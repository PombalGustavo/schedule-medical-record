package com.PombalGustavo.ScheduleMedicalRecord.controllers;

import com.PombalGustavo.ScheduleMedicalRecord.dto.patient.PatientRequestDTO;
import com.PombalGustavo.ScheduleMedicalRecord.dto.patient.PatientResponseDTO;
import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.Patient;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.ClinicRepository;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<PatientResponseDTO> savePatient(@Valid @RequestBody PatientRequestDTO dto,
                                                          @AuthenticationPrincipal Jwt jwt) {
        Long clinicId = jwt.getClaim("clinicId");

        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        Patient patient = new Patient();
        patient.setName(dto.name());
        patient.setCpf(dto.cpf());
        patient.setBirthdate(dto.birthdate());
        patient.setPhone(dto.phone());
        patient.setClinic(clinic);

        Patient savedPatient = patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PatientResponseDTO(savedPatient));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id,
                                                            @Valid @RequestBody PatientRequestDTO dto,
                                                            @AuthenticationPrincipal Jwt jwt) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));


        patient.setName(dto.name());
        patient.setCpf(dto.cpf());
        patient.setBirthdate(dto.birthdate());
        patient.setPhone(dto.phone());

        Patient updatedPatient = patientRepository.save(patient);
        return ResponseEntity.ok(new PatientResponseDTO(updatedPatient));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found with ID: " + id);
        }

        patientRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Patient deleted successfully");
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients(@AuthenticationPrincipal Jwt jwt) {
        Long clinicId = jwt.getClaim("clinicId");

        List<PatientResponseDTO> dtos = patientRepository.findAllByClinicId(clinicId)
                .stream()
                .map(PatientResponseDTO::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}