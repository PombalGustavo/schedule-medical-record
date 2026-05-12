package com.PombalGustavo.ScheduleMedicalRecord.dto.medicalRecord;

import com.PombalGustavo.ScheduleMedicalRecord.models.MedicalRecord;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record MedicalRecordResponseDTO(
        UUID medicalRecordId,
        String contentText,
        Instant createdAt,
        LocalDateTime updatedAt,
        UUID patientId,
        String patientName,
        UUID appointmentId,
        String appointmentDescription,
        UUID userId,
        String username,
        UUID clinicId,
        String clinicName
) {
    public MedicalRecordResponseDTO(MedicalRecord record) {
        this(
                record.getMedicalRecordId(),
                record.getRawContentText(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getPatient().getPatientId(),
                record.getPatient().getName(),
                record.getAppointment().getAppointmentId(),
                record.getAppointment().getDescription(),
                record.getUser().getUserId(),
                record.getUser().getUsername(),
                record.getClinic().getId(),
                record.getClinic().getClinicName()
        );
    }
}