package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.WaitlistEntry;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.WaitlistEntryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private WaitlistEntryRepository waitlistEntryRepository;
    private PatientService patientService;
    private DoctorService doctorService;
    private NotificationService notificationService;

    public Appointment getById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no appointment present with AppointmentId : " + appointmentId));
    }

    public List<Appointment> getByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }

    public List<Appointment> getByPatientAndStatus(Patient patient, Appointment.AppointmentStatus status) {
        return appointmentRepository.findByPatientAndStatus(patient, status);
    }

    public List<Appointment> getByDoctorIdAndStatus(Long doctorId, Appointment.AppointmentStatus status) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, status);
    }

    public int getNextWaitListPosition(Long doctorId) {
        List<WaitlistEntry> entries = waitlistEntryRepository.findByDoctorIdAndStatusOrderByPositionAsc(doctorId, WaitlistEntry.WaitlistStatus.WAITING);
        return entries.size() + 1;
    }

    @Transactional
    public Appointment create(Long patientId, Long doctorId, LocalDateTime appointmentFrom, LocalDateTime appointmentUntil, String reason) {
        Patient patient = patientService.getById(patientId);
        Doctor doctor = doctorService.getById(doctorId);

        if (doctor.getIsAvailable()) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setStatus(Appointment.AppointmentStatus.APPROVED);
            appointment.setAppointmentFrom(appointmentFrom);
            appointment.setGetAppointmentUntil(appointmentUntil);
            appointment.setReason(reason);

            Appointment saved = appointmentRepository.save(appointment);
            notificationService.notifyAppointmentApproved(patient, doctor.getName());
            return saved;
        } else {
            WaitlistEntry waitlist = new WaitlistEntry();
            waitlist.setDesiredFrom(appointmentFrom);
            waitlist.setDesiredUntil(appointmentUntil);
            waitlist.setDoctor(doctor);
            waitlist.setPatient(patient);
            waitlist.setPosition(getNextWaitListPosition(doctorId));
            waitlist.setType(WaitlistEntry.WaitlistType.APPOINTMENT);
            waitlist.setStatus(WaitlistEntry.WaitlistStatus.WAITING);

            waitlistEntryRepository.save(waitlist);
            notificationService.notifyAddedToWaitlist(patient, doctor.getName(), getNextWaitListPosition(doctorId));
            throw new IllegalArgumentException("Doctor not available. Added to waitlist at position: " + waitlist.getPosition());
        }

    }

    @Transactional
    public Appointment aproveAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        if (appointment.getStatus() != Appointment.AppointmentStatus.PENDING) {
            throw new IllegalArgumentException("Only PENDING appointments can be approved");
        }
        appointment.setStatus(Appointment.AppointmentStatus.APPROVED);
        return appointment;
    }

    @Transactional
    public Appointment rejectAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        if (appointment.getStatus() != Appointment.AppointmentStatus.PENDING) {
            throw new IllegalArgumentException("Only PENDING appointments can be rejected");
        }
        appointment.setStatus(Appointment.AppointmentStatus.REJECTED);
        notificationService.notifyAppointmentRejected(appointment.getPatient(), appointment.getDoctor().getName());
        return appointment;
    }

    public Appointment completeAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        if (appointment.getStatus() != Appointment.AppointmentStatus.ACTIVE) {
            throw new IllegalArgumentException("Only ACTIVE appointments can be completed");
        }
        appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
        notificationService.notifyAppointmentCompleted(appointment.getPatient(), appointment.getDoctor().getName());
        return appointment;
    }

    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        if (appointment.getStatus() == Appointment.AppointmentStatus.COMPLETED || appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot cancel completed or already cancelled appointments");
        }
        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        notificationService.notifyAppointmentCancelled(appointment.getPatient(), appointment.getDoctor().getName());
        return appointment;
    }

}
