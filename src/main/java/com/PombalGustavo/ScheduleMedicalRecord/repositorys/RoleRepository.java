package com.PombalGustavo.ScheduleMedicalRecord.repositorys;

import com.PombalGustavo.ScheduleMedicalRecord.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
