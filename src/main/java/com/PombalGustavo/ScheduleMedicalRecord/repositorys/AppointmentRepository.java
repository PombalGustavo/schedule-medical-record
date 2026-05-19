package com.PombalGustavo.ScheduleMedicalRecord.repositorys;

import com.PombalGustavo.ScheduleMedicalRecord.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAllByPatientPatientIdAndClinicId(UUID patientId, UUID clinicId);
    List<Appointment> findAllByUserUserId(UUID userId);

}
