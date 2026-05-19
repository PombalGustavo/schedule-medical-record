package com.PombalGustavo.ScheduleMedicalRecord.dto.patient;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record PatientRequestDTO(
        @NotBlank String name,
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @NotNull LocalDate birthdate,
        @NotBlank String phone

) {}
