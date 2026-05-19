package com.PombalGustavo.ScheduleMedicalRecord.controllers;

import com.PombalGustavo.ScheduleMedicalRecord.dto.clinic.ClinicAndUserResponseDTO;
import com.PombalGustavo.ScheduleMedicalRecord.dto.clinic.ClinicDTO;
import com.PombalGustavo.ScheduleMedicalRecord.dto.clinic.ClinicRegisterDTO;
import com.PombalGustavo.ScheduleMedicalRecord.dto.clinic.ClinicResponseDTO;
import com.PombalGustavo.ScheduleMedicalRecord.models.Clinic;
import com.PombalGustavo.ScheduleMedicalRecord.models.Role;
import com.PombalGustavo.ScheduleMedicalRecord.models.User;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.ClinicRepository;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.RoleRepository;
import com.PombalGustavo.ScheduleMedicalRecord.repositorys.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/clinic")
public class ClinicController {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @PostMapping("/register")
    public ResponseEntity<ClinicAndUserResponseDTO> createClinicWithAdmin(@RequestBody ClinicRegisterDTO dto){

        Clinic clinic = new Clinic();
        clinic.setClinicName(dto.name());
        Clinic savedClinic = clinicRepository.save(clinic);

        Role role = roleRepository.findByName(Role.Values.DOCTOR.name())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setClinic(savedClinic);
        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ClinicAndUserResponseDTO(clinic, user));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ClinicResponseDTO> update(@PathVariable UUID id, @RequestBody ClinicDTO dto){
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
        clinic.setClinicName(dto.name());
        clinicRepository.save(clinic);

        return ResponseEntity.status(HttpStatus.OK).body(new ClinicResponseDTO(clinic));

    }

}
