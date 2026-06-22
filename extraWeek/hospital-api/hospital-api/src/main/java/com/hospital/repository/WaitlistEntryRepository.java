package com.hospital.repository;

import com.hospital.entity.WaitlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitlistEntryRepository extends JpaRepository<WaitlistEntry, Long> {
    List<WaitlistEntry> findByEquipmentIdAndStatusOrderByPositionAsc(Long equipmentId, WaitlistEntry.WaitlistStatus status);
    List<WaitlistEntry> findByDoctorIdAndStatusOrderByPositionAsc(Long doctorId, WaitlistEntry.WaitlistStatus status);
    List<WaitlistEntry> findByPatientIdAndStatusOrderByPositionAsc(Long patientId, WaitlistEntry.WaitlistStatus status);
    List<WaitlistEntry> findByPatientIdAndStatus(Long patientId, WaitlistEntry.WaitlistStatus status);
}
