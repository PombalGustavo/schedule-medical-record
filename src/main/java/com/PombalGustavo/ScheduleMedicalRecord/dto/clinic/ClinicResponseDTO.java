package com.PombalGustavo.ScheduleMedicalRecord.dto.clinic;

import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import java.time.Instant;
import java.util.UUID;

public record ClinicResponseDTO(
        UUID clinicId,
        String clinicName
) {
    public ClinicResponseDTO(Clinic clinic) {
        this(
                clinic.getId(),
                clinic.getClinicName()
        );
    }
}