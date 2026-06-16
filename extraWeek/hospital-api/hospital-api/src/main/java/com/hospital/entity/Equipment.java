package com.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "equipments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Equipment name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Equipment type is required")
    @Column(nullable = false)
    private String type;

    @Positive(message = "Total quantity must be positive")
    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer availableQuantity;

    private String location;

    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "equipment" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "equipment" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WaitlistEntry> waitlistEntries = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if(availableQuantity == null) {
            availableQuantity = totalQuantity;
        }
    }

    @PreUpdate
    protected void OnUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
