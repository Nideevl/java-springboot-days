package com.hospital.service;

import com.hospital.entity.Patient;
import com.hospital.entity.Reservation;
import com.hospital.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DischargeService {
    private final PatientService patientService;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final NotificationService notificationService;

    public void dischargePatient(Long patientId) {
        Patient patient = patientService.getById(patientId);

        patient.setStatus(Patient.PatientStatus.DISCHARGED);

        List<Reservation> reservations = reservationRepository.findByPatient(patient);
        for(Reservation reservation : reservations) {
                reservationService.cancelReservation(reservation.getId());
            }
        notificationService.notifyPatientDischarged(patient);
        }
}
