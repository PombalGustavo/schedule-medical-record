package com.PombalGustavo.ScheduleMedicalRecord.dto.appointment;

import com.PombalGustavo.ScheduleMedicalRecord.models.Appointment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDTO(
        UUID appointmentId,
        Instant dateAndTime,
        String description,
        String status,
        String patientName,
        UUID medicalRecordId,
        String medicalRecordContent
) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(
                appointment.getAppointmentId(),
                appointment.getDateAndTime(),
                appointment.getDescription(),
                appointment.getStatus().name(),
                appointment.getPatient().getName(),
                appointment.getMedicalRecord() != null?
                    appointment.getMedicalRecord().getMedicalRecordId() : null,
                appointment.getMedicalRecord() != null ?
                        appointment.getMedicalRecord().getRawContentText() : null
        );
    }
}