package com.hospital.repository;

import com.hospital.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByName(String name);
    List<Equipment> findByTypeAndIsActiveTrue(String Type);
}
