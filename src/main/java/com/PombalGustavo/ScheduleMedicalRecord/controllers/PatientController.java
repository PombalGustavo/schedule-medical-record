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
import java.util.UUID;

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
        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        if (patientRepository.existsByCpfAndClinicId(dto.cpf(), clinicId)) {
            throw new RuntimeException("CPF já cadastrado nessa clínica");
        }

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
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
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
    public ResponseEntity<String> deletePatient(@PathVariable UUID id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        patientRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Patient deleted successfully");
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients(@AuthenticationPrincipal Jwt jwt) {

        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));

        List<PatientResponseDTO> dtos = patientRepository.findAllByClinicId(clinicId)
                .stream()
                .map(PatientResponseDTO::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatient(@PathVariable UUID id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        return ResponseEntity.ok(new PatientResponseDTO(patient));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<PatientResponseDTO>> searchByName(@RequestParam String name,
                                                                 @AuthenticationPrincipal Jwt jwt) {
        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));

        List<PatientResponseDTO> patients = patientRepository
                .findByNameContainingIgnoreCaseAndClinicId(name, clinicId)
                .stream()
                .map(PatientResponseDTO::new)
                .toList();

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search/cpf")
    public ResponseEntity<PatientResponseDTO> searchByCpf(@RequestParam String cpf,
                                         @AuthenticationPrincipal Jwt jwt) {
        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));
        Patient patient = patientRepository.findByCpfAndClinicId(cpf, clinicId);

        return ResponseEntity.ok(new PatientResponseDTO(patient));

    }
}
