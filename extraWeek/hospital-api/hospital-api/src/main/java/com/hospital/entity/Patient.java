package com.hospital.entity;

import com.hospital.entity.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @NotBlank(message="Patient name is required")
 @Column(nullable = false)
 private String name;

 @Email(message="Email must be valid")
 @NotBlank(message="Patient Email is required")
 @Column(unique = true, nullable = false)
 private String email;

 @NotBlank(message = "Phone number is required")
 @Column(nullable = false)
 private String phone;

 @Column(nullable = false)
 private String medicalRecordNumber;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 private PatientStatus status = PatientStatus.ACTIVE;

 @Column(name = "created_at", nullable = false, updatable = false)
 private LocalDateTime createdAt;

 @Column(name = "updated_at")
 private LocalDateTime updatedAt;

 @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
 private Set<Reservation> reservations = new HashSet<>();

 @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
 private Set<Appointment> appointments = new HashSet<>();

 @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
 private Set<WaitlistEntry> waitlistEntries = new HashSet<>();

 @PrePersist
 protected void onCreated() {
  createdAt = LocalDateTime.now();
  updatedAt = LocalDateTime.now();
 }

 @PreUpdate
 protected void onUpdate() {
  updatedAt = LocalDateTime.now();
 }

 public enum PatientStatus {
     ACTIVE, DISCHARGED, INACTIVE
 }
}

