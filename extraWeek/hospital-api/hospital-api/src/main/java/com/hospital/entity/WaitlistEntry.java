package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "waitlist_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaitlistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitlistType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitlistStatus status = WaitlistStatus.WAITING;

    @Column(nullable = false)
    private Integer position;

    private LocalDateTime desiredFrom;

    private LocalDateTime desiredUntil;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum WaitlistType {
        EQUIPMENT, APPOINTMENT
    }

    public enum WaitlistStatus {
        WAITING, PROMOTED, EXPIRED, CANCELLED
    }
}