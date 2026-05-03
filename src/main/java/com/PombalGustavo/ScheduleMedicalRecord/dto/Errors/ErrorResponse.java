package com.PombalGustavo.ScheduleMedicalRecord.dto.Errors;

public record ErrorResponse(
        int status,
        String message
){}