package com.hospital.service;

import com.hospital.entity.*;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.ReservationRepository;
import com.hospital.repository.WaitlistEntryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitlistService {
    private final WaitlistEntryRepository waitlistEntryRepository;
    private final EquipmentService equipmentService;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;
    private final DoctorService doctorService;
    private final AppointmentRepository appointmentRepository;

    public WaitlistEntry getById(Long id) {
        return waitlistEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There was Waitlist with id : "+id));
    }

    public List<WaitlistEntry> getByPatientId(Long patientId) {
        return waitlistEntryRepository.findByPatientIdAndStatus(patientId, WaitlistEntry.WaitlistStatus.WAITING);
    }

    public void promoteFromEquipmentWaitlist(Long equipmentId) {
        List<WaitlistEntry> waitingEntries = waitlistEntryRepository
                .findByEquipmentIdAndStatusOrderByPositionAsc(equipmentId, WaitlistEntry.WaitlistStatus.WAITING);

        if(waitingEntries.isEmpty()) {
            return;
        }

        WaitlistEntry toPromote = waitingEntries.getFirst();
        Equipment equipment = equipmentService.getById(equipmentId);

        Reservation reservation = new Reservation();
        reservation.setEquipment(equipment);
        reservation.setPatient(toPromote.getPatient());
        reservation.setReservedFrom(toPromote.getDesiredFrom());
        reservation.setReservedUntil(toPromote.getDesiredUntil());
        reservation.setStatus(Reservation.ReservationStatus.PENDING);

        reservationRepository.save(reservation);
        toPromote.setStatus(WaitlistEntry.WaitlistStatus.PROMOTED);

        reorderWaitListByEquipmentId(equipmentId);

        notificationService.notifyWaitlistPromoted(toPromote.getPatient(), equipment.getName());
    }

    @Transactional
    public void promoteFromDoctorWaitlist(Long doctorId) {

        List<WaitlistEntry> waitingEntries = waitlistEntryRepository.findByDoctorIdAndStatusOrderByPositionAsc(doctorId, WaitlistEntry.WaitlistStatus.WAITING);

        if (waitingEntries.isEmpty()) {
            return;
        }

        WaitlistEntry toPromote = waitingEntries.getFirst();
        Doctor doctor = doctorService.getById(doctorId);

        Appointment appointment = new Appointment();
        appointment.setPatient(toPromote.getPatient());
        appointment.setDoctor(doctor);
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        appointment.setAppointmentFrom(toPromote.getDesiredFrom());
        appointment.setAppointmentUntil(toPromote.getDesiredUntil());

        appointmentRepository.save(appointment);

        toPromote.setStatus(WaitlistEntry.WaitlistStatus.PROMOTED);
        waitlistEntryRepository.save(toPromote);

        reorderWaitListByDoctorId(doctorId);

        notificationService.notifyWaitlistPromoted(toPromote.getPatient(), doctor.getName());
    }

    private void reorderWaitListByEquipmentId(Long equipmentId) {
        List<WaitlistEntry> entries = waitlistEntryRepository.findByEquipmentIdAndStatusOrderByPositionAsc(equipmentId, WaitlistEntry.WaitlistStatus.WAITING);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setPosition(i + 1);
        }
    }

    private void reorderWaitListByDoctorId(Long doctorId) {
        List<WaitlistEntry> entries = waitlistEntryRepository.findByDoctorIdAndStatusOrderByPositionAsc(doctorId, WaitlistEntry.WaitlistStatus.WAITING);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setPosition(i + 1);
        }
    }
}
