package com.PombalGustavo.ScheduleMedicalRecord.models.enums;

public enum AppointmentStatus {
    SCHEDULED,   // Agendado
    CONFIRMED,   // Confirmado
    IN_PROGRESS, // Em atendimento
    COMPLETED,   // Concluído
    CANCELED,    // Cancelado
    NO_SHOW      // Paciente não compareceu
}