package com.PombalGustavo.ScheduleMedicalRecord.dto.patient;

import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.Patient;
import java.time.Instant;
import java.util.Date;

public record PatientResponseDTO(
        long patientId,
        String name,
        String cpf,
        Date birthdate,
        String phone,
        Instant creationTimestamp,
        Clinic clinic
) {
    public PatientResponseDTO(Patient patient) {
        this(
                patient.getPatientId(),
                patient.getName(),
                patient.getCpf(),
                patient.getBirthdate(),
                patient.getPhone(),
                patient.getCreationTimestamp(),
                patient.getClinic()
        );
    }
}