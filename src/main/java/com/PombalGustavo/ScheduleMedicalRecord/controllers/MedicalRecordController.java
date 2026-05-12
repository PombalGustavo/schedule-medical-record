package com.PombalGustavo.ScheduleMedicalRecord.controllers;

import com.PombalGustavo.ScheduleMedicalRecord.dto.medicalRecord.MedicalRecordRequestDTO;
import com.PombalGustavo.ScheduleMedicalRecord.dto.medicalRecord.MedicalRecordResponseDTO;
import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.MedicalRecord;
import com.PombalGustavo.ScheduleMedicalRecord.models.User;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/medical-record")
public class MedicalRecordController {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<MedicalRecordResponseDTO> saveMedicalRecord(@Valid @RequestBody MedicalRecordRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {

        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        UUID userId = UUID.fromString(jwt.getSubject());
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));


        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRawContentText(dto.contentText());
        medicalRecord.setPatient(patientRepository.findById(dto.patientId()).orElseThrow(() -> new EntityNotFoundException("Patient not found")));
        medicalRecord.setAppointment(appointmentRepository.findById(dto.appointmentId()).orElseThrow(() -> new EntityNotFoundException("Appointment not found")));
        medicalRecord.setCreatedAt(Instant.now());
        medicalRecord.setUpdatedAt(LocalDateTime.now());
        medicalRecord.setClinic(clinic);
        medicalRecord.setUser(user);

        MedicalRecord response = medicalRecordRepository.save(medicalRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MedicalRecordResponseDTO(response));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> updateMedicalRecord(@PathVariable UUID id, @Valid @RequestBody MedicalRecordRequestDTO dto) {

        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medical Record not found"));
        medicalRecord.setRawContentText(dto.contentText());
        medicalRecord.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(new MedicalRecordResponseDTO(medicalRecordRepository.save(medicalRecord)));

    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecord(@PathVariable UUID id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical Record not found"));
        return ResponseEntity.ok(new MedicalRecordResponseDTO(record));
    }
}
