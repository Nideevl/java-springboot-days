    package com.hospital.repository;

    import com.hospital.entity.Patient;
    import com.hospital.entity.Reservation;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface ReservationRepository extends JpaRepository<Reservation, Long> {
        List<Reservation> findByPatient(Patient patient);
        List<Reservation> findByPatientAndStatus(Patient patient, Reservation.ReservationStatus status);
        List<Reservation> findByStatus(Reservation.ReservationStatus status);
        List<Reservation> findByEquipmentIdAndStatus(Long equipmentId, Reservation.ReservationStatus status);
    }
