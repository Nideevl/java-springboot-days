package com.hospital.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegisterRequest {
    private String email;
    private String password;
    private String specialty;
    private String licenseNumber;
}