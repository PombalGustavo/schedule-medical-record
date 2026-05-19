package com.PombalGustavo.ScheduleMedicalRecord.dto.appointment;

import com.PombalGustavo.ScheduleMedicalRecord.models.Appointment;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentResponseDTO(
        UUID appointmentId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String description,
        String status,
        UUID patientId,
        String patientName,
        UUID medicalRecordId,
        String medicalRecordContent
) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(
                appointment.getAppointmentId(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getDescription(),
                appointment.getStatus().name(),
                appointment.getPatient().getPatientId(),
                appointment.getPatient().getName(),
                appointment.getMedicalRecord() != null?
                    appointment.getMedicalRecord().getMedicalRecordId() : null,
                appointment.getMedicalRecord() != null ?
                        appointment.getMedicalRecord().getRawContentText() : null
        );
    }
}