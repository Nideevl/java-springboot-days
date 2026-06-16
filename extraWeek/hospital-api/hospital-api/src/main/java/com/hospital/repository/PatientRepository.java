package com.hospital.repository;

import com.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    List<Patient> findByStatus(Patient.PatientStatus status);

    List<Patient> findByStatusAndNameStartingWith(Patient.PatientStatus status, String name);
//    You could have done this too
//    @Query("SELECT p FROM patient p WHERE p.status = :status AND p.name LIKE :name%")
//    List<Patient> searchByNameAndStatus(@Param("status") Patient.PatientStatus status, @Param("name") String name);

}
