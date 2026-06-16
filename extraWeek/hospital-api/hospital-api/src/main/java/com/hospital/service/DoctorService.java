package com.hospital.service;

import com.hospital.entity.Doctor;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: "+id));
    }

    public Doctor getByName(String name) {
        return doctorRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with name: "+name));
    }

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public List<Doctor> getAvailableBySpecialty(String specialty) {
        return doctorRepository.findBySpecialtyAndIsAvailableTrue(specialty);
    }

    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor update(Long id,Doctor updated) {
        Doctor existing = getById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setIsAvailable(updated.getIsAvailable());
        return existing;
    }

    public void delete(Long id) {
        Doctor doctor = getById(id);
        doctorRepository.delete(doctor);
    }
}
