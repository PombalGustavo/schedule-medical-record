package com.PombalGustavo.ScheduleMedicalRecord.dto.medicalRecord;

import java.util.UUID;

public record MedicalRecordRequestDTO(
        String contentText,
        UUID patientId,
        UUID appointmentId
) {
}