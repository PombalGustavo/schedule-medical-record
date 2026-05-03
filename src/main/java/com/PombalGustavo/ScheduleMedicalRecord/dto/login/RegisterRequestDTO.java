package com.PombalGustavo.ScheduleMedicalRecord.dto.login;

import java.util.Set;

public record RegisterRequestDTO(
        String username,
        String email,
        String password,
        Long clinicId,
        Set<String> roles
) {}
