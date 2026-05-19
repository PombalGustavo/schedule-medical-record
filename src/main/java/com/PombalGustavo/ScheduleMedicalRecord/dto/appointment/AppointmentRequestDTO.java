package com.PombalGustavo.ScheduleMedicalRecord.dto.appointment;

import com.PombalGustavo.ScheduleMedicalRecord.models.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentRequestDTO(
        @NotNull @Future LocalDate date,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotBlank String description,
        @NotNull AppointmentStatus status,
        @NotNull UUID patientId
) {}