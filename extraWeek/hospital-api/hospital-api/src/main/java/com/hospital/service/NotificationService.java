package com.hospital.service;

import com.hospital.entity.Equipment;
import com.hospital.entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void notifyReservationCreated(Patient patient, Equipment equipment) {
        log.info("NOTIFICATION: Reservation created for patient '{}' for equipment '{}'", patient.getName(), equipment.getName());
    }

    public void notifyReservationCompleted(Patient patient, Equipment equipment) {
        log.info("NOTIFICATION: Reservation completed for patient '{}' for equipment '{}'", patient.getName(), equipment.getName());
    }

    public void notifyAddedToWaitlist(Patient patient, String resourceName, int position) {
        log.info("NOTIFICATION: Patient '{}' added to waitlist for '{}' at position {}", patient.getName(), resourceName, position);
    }

    public void notifyAppointmentApproved(Patient patient, String doctorName) {
        log.info("NOTIFICATION: Appointment APPROVED for patient '{}' with Dr '{}'", patient.getName(), doctorName);
    }

    public void notifyAppointmentRejected(Patient patient, String doctorName) {
        log.info("NOTIFICATION: Appointment REJECTED for patient '{}' with Dr '{}'", patient.getName(), doctorName);
    }

    public void notifyAppointmentCompleted(Patient patient, String doctorName) {
        log.info("NOTIFICATION: Appointment COMPLETED for patient '{}' with Dr '{}'", patient.getName(), doctorName);
    }

    public void notifyAppointmentCancelled(Patient patient, String doctorName) {
        log.info("NOTIFICATION: Appointment CANCELLED for patient '{}' with Dr '{}'", patient.getName(), doctorName);
    }

    public void notifyWaitlistPromoted(Patient patient, String resourceName) {
        log.info("NOTIFICATION: Waitlist entry PROMOTED for patient '{}' for '{}'", patient.getName(), resourceName);
    }

    public void notifyPatientDischarged(Patient patient) {
        log.info("NOTIFICATION: Patient '{}' has been DISCHARGED", patient.getName());
    }
}