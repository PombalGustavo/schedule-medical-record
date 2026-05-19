package com.PombalGustavo.ScheduleMedicalRecord.repositorys;

import com.PombalGustavo.ScheduleMedicalRecord.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findAllByClinicId(UUID clinicId);
    boolean existsByCpfAndClinicId(String cpf, UUID clinicId);
    List<Patient> findByNameContainingIgnoreCaseAndClinicId(String name, UUID clinicId);
    Patient findByCpfAndClinicId(String cpf, UUID clinicId);
}

