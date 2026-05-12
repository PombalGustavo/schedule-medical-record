package com.PombalGustavo.ScheduleMedicalRecord.dto.appointment;

import com.PombalGustavo.ScheduleMedicalRecord.models.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record AppointmentRequestDTO(
        @NotNull @Future Instant dateAndTime,
        @NotBlank String description,
        @NotNull AppointmentStatus status,
        @NotNull UUID patientId
) {}