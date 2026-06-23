package com.hospital.service;

import com.hospital.entity.Patient;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: "+id));
    }

    public Optional<Patient> getByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public List<Patient> getByStatus(Patient.PatientStatus status) {
        return patientRepository.findByStatus(status);
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient update(Patient updated) {
        Patient existing = getById(updated.getId());
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        return existing;
    }

    public void delete(Long id) {
        Patient exiting = getById(id);
        patientRepository.delete(exiting);
    }

}
