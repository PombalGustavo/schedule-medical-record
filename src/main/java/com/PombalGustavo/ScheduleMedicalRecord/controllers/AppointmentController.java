package com.PombalGustavo.ScheduleMedicalRecord.controllers;

import com.PombalGustavo.ScheduleMedicalRecord.dto.appointment.AppointmentRequestDTO;
import com.PombalGustavo.ScheduleMedicalRecord.dto.appointment.AppointmentResponseDTO;
import com.PombalGustavo.ScheduleMedicalRecord.models.Appointment;
import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.User;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.AppointmentRepository;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.ClinicRepository;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.PatientRepository;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> addAppointment(@Valid @RequestBody AppointmentRequestDTO dto,
                                                                 @AuthenticationPrincipal Jwt jwt) throws BadRequestException {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentStartDateTime = LocalDateTime.of(dto.date(), dto.startTime());

        if (appointmentStartDateTime.isBefore(now)) {
            throw new BadRequestException("O agendamento não pode ser criado em uma data/hora que já passou.");
        }

        if (dto.startTime().isAfter(dto.endTime()) || dto.startTime().equals(dto.endTime())) {
            throw new BadRequestException("O horário de início deve ser menor que o horário de término.");
        }


        Appointment appointment = new Appointment();
        appointment.setDate(dto.date());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setDescription(dto.description());
        appointment.setStatus(dto.status());
        appointment.setPatient(patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found")));

        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        UUID userId = UUID.fromString(jwt.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        appointment.setUser(user);
        appointment.setClinic(clinic);

        Appointment response = appointmentRepository.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponseDTO(response));
    }

    @Transactional(readOnly = true)
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt) {

        UUID clinicId = UUID.fromString(jwt.getClaim("clinicId"));

        List<AppointmentResponseDTO> dtos = appointmentRepository
                .findAllByPatientPatientIdAndClinicId(id, clinicId)
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();

        if (dtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(dtos);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable UUID id, @Valid @RequestBody AppointmentRequestDTO dto) throws BadRequestException {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        if (dto.startTime().isAfter(dto.endTime())) {
            throw new BadRequestException("Start time cannot be after end time");
        }

        appointment.setDate(dto.date());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setDescription(dto.description());
        appointment.setStatus(dto.status());

        Appointment response = appointmentRepository.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponseDTO(response));
    }

    @Transactional
    @GetMapping("/my-schedule")
    public ResponseEntity<List<AppointmentResponseDTO>> getSchedule(@AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<AppointmentResponseDTO> schedule = appointmentRepository.
                findAllByUserUserId(userId)
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();

        if (schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(schedule);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable UUID id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        return ResponseEntity.ok(new AppointmentResponseDTO(appointment));
    }

}
