package com.PombalGustavo.ScheduleMedicalRecord.repositorys;

import com.PombalGustavo.ScheduleMedicalRecord.models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
}
