package com.PombalGustavo.ScheduleMedicalRecord.dto.patient;

import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.Patient;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public record PatientResponseDTO(
        UUID patientId,
        String name,
        String cpf,
        Date birthdate,
        String phone,
        Instant creationTimestamp,
        UUID clinicId,
        String clinicName
        ) {
    public PatientResponseDTO(Patient patient) {
        this(
                patient.getPatientId(),
                patient.getName(),
                patient.getCpf(),
                patient.getBirthdate(),
                patient.getPhone(),
                patient.getCreationTimestamp(),
                patient.getClinic().getId(),
                patient.getClinic().getClinicName()
        );
    }
}