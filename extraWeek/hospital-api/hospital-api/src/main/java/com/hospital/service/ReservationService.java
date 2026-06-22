package com.hospital.service;

import com.hospital.entity.Equipment;
import com.hospital.entity.Patient;
import com.hospital.entity.Reservation;
import com.hospital.entity.WaitlistEntry;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.ReservationRepository;
import com.hospital.repository.WaitlistEntryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final WaitlistEntryRepository waitlistEntryRepository;
    private final PatientService patientService;
    private final EquipmentService equipmentService;
    private final NotificationService notificationService;

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: "+id));
    }


    public List<Reservation> getByPatient(Patient patient) {
        return reservationRepository.findByPatient(patient);
    }

    public List<Reservation> getByPatientAndStatus(Patient patient, Reservation.ReservationStatus status) {
        return reservationRepository.findByPatientAndStatus(patient, status);
    }

    public List<Reservation> getByEquipmentIdAndStatus(Long equipmentId, Reservation.ReservationStatus status) {
        return reservationRepository.findByEquipmentIdAndStatus(equipmentId, status);
    }

    public int getNextWaitListPosition(Long equipmentId) {
        List<WaitlistEntry> entries = waitlistEntryRepository.findByEquipmentIdAndStatusOrderByPositionAsc(equipmentId, WaitlistEntry.WaitlistStatus.WAITING);
        return entries.size()+1;
    }

    public Reservation create(Long patientId, Long equipmentId, LocalDateTime reservedFrom, LocalDateTime reservedUntil) {
        Patient patient = patientService.getById(patientId);
        Equipment equipment = equipmentService.getById(equipmentId);

        if(equipment.getAvailableQuantity() > 0) {
            Reservation reservation = new Reservation();
            reservation.setPatient(patient);
            reservation.setEquipment(equipment);
            reservation.setStatus(Reservation.ReservationStatus.PENDING);
            reservation.setReservedFrom(reservedFrom);
            reservation.setReservedUntil(reservedUntil);

            notificationService.notifyReservationCreated(patient, equipment);
            return reservation;
        }
        else {
            WaitlistEntry waitlistEntry = new WaitlistEntry();
            waitlistEntry.setPatient(patient);
            waitlistEntry.setEquipment(equipment);
            waitlistEntry.setType(WaitlistEntry.WaitlistType.EQUIPMENT);
            waitlistEntry.setStatus(WaitlistEntry.WaitlistStatus.WAITING);
            waitlistEntry.setPosition(getNextWaitListPosition(equipmentId));
            waitlistEntry.setDesiredFrom(reservedFrom);
            waitlistEntry.setDesiredUntil(reservedUntil);

            notificationService.notifyReservationCreated(patient, equipment);
            throw new IllegalArgumentException("Equipment is not available, Added to waitlist at position : "+waitlistEntry.getPosition());
        }
    }

    public Reservation approveReservation(Long reservationId) {
        Reservation  reservation = getById(reservationId);
        if(reservation.getStatus() != Reservation.ReservationStatus.PENDING) {
            throw new IllegalArgumentException("Only reservation with pending status can be approved");
        }
        reservation.setStatus(Reservation.ReservationStatus.APPROVED);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation completeReservation(Long reservationId) {
        Reservation reservation = getById(reservationId);
        if(reservation.getStatus() != Reservation.ReservationStatus.ACTIVE) {
            throw new IllegalArgumentException("Only reservation with active status can be completed");
        }
        reservation.setStatus(Reservation.ReservationStatus.COMPLETED);
        equipmentService.releaseEquipment(reservation.getEquipment().getId());
        notificationService.notifyReservationCompleted(reservation.getPatient(), reservation.getEquipment());
        return reservationRepository.save(reservation);
    }


    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = getById(reservationId);
        if (reservation.getStatus() == Reservation.ReservationStatus.COMPLETED || reservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot cancel completed or already cancelled reservations");
        }
        if(reservation.getStatus() == Reservation.ReservationStatus.APPROVED ||
            reservation.getStatus() == Reservation.ReservationStatus.ACTIVE) {
            equipmentService.releaseEquipment(reservationId);
        }
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

    }

}
