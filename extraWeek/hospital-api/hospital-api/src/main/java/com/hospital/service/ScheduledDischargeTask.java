package com.hospital.service;

import com.hospital.entity.Reservation;
import com.hospital.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledDischargeTask {

    DischargeService dischargeService;
    ReservationRepository reservationRepository;
    private final Random random = new Random();

    @Scheduled(cron = "0 * * * * *")
    public void  simulateRandomDischarge() {
        List<Reservation> activeReservation = reservationRepository
                .findByStatus(Reservation.ReservationStatus.ACTIVE);

        if(activeReservation.isEmpty()) {
            log.debug("No active reservation found for discharge simulation");
            return;
        }

        if(random.nextDouble() < 0.2) {
            Reservation randomReservation = activeReservation
                    .get(random.nextInt(activeReservation.size()));
            Long patientId = randomReservation.getId();
            log.info("Simulating discharge for patient: {} from reservation: {}",
                    patientId, randomReservation.getId());

            try {
                dischargeService.dischargePatient(patientId);
                log.info("Patient with id : {} got successfully discharged", patientId);
            }
            catch (Exception e) {
                log.error("Error discharging patient with id {}: {}",patientId, e.getMessage());
            }
        }
    }
}
