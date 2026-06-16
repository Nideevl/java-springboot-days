package com.hospital.service;

import com.hospital.entity.Equipment;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public Equipment getById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: "+id));
    }

    public Equipment getByName(String name) {
        return equipmentRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with name: "+name));
    }

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    public List<Equipment> getByTypeAndActive(String type) {
        return equipmentRepository.findByTypeAndIsActiveTrue(type);
    }

    public Equipment create(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Transactional
    public Equipment update(Equipment updated) {
        Equipment existing = getById(updated.getId());
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setAvailableQuantity(updated.getAvailableQuantity());
        existing.setTotalQuantity(updated.getTotalQuantity());
        existing.setIsActive(updated.getIsActive());
        existing.setLocation(updated.getLocation());
        return existing;
    }

    public Boolean isAvailable(Long equipmentId) {
        Equipment equipment = getById(equipmentId);
        return equipment.getIsActive() && equipment.getAvailableQuantity() > 0;
    }

    @Transactional
    public void reserveEquipment(Long equipmentId) {
        Equipment equipment = getById(equipmentId);
        if(equipment.getAvailableQuantity() <= 0) {
            throw new IllegalArgumentException("Equipment not available for reservation");
        }

        equipment.setAvailableQuantity(equipment.getAvailableQuantity()-1);
    }

    @Transactional
    public void releaseEquipment(Long equipmentId) {
        Equipment equipment = getById(equipmentId);
        if(equipment.getAvailableQuantity() < equipment.getTotalQuantity()) {
            equipment.setAvailableQuantity(equipment.getAvailableQuantity()+1);
        }
    }

    public void delete(Long equipmentId) {
        Equipment equipment = getById(equipmentId);
        equipmentRepository.delete(equipment);
    }
}
