package com.PombalGustavo.ScheduleMedicalRecord.dto.clinic;

import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.User;

import java.time.Instant;
import java.util.UUID;

public record ClinicAndUserResponseDTO(
        UUID clinicID,
        String clinicName,
        Instant createdAt,
        UUID userID,
        String username
) {
    public ClinicAndUserResponseDTO(Clinic clinic, User user) {
        this(
                clinic.getId(),
                clinic.getClinicName(),
                clinic.getCreatedAt(),
                user.getUserId(),
                user.getUsername()
        );
    }
}
