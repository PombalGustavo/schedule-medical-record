package com.PombalGustavo.ScheduleMedicalRecord.dto.login;

public record LoginResponseDTO(
        String accessToken,
        Long expiresIn,
        String username,
        String clinicName
){}
