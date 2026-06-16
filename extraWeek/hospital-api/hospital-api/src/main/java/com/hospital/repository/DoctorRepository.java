package com.hospital.repository;

import com.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByName(String name);

    List<Doctor> findBySpecialty(String specialty);

    List<Doctor> findBySpecialtyAndIsAvailableTrue(String specialty);
}