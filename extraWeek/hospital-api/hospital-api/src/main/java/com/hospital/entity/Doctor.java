package com.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "doctors")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor name is required")
    @Column(nullable = false)
    private String name;

    @Email(message = "Email must be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Specialty is required")
    @Column(nullable = false)
    private String specialty;

    @NotBlank(message = "License number is required")
    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WaitlistEntry> waitlistEntries = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
