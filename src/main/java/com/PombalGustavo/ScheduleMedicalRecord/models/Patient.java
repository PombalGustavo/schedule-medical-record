package com.PombalGustavo.ScheduleMedicalRecord.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "patientId")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    @Column(name = "patient_name", nullable = false)
    private String name;

    @Column(name = "patient_cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "patient_birthdate", nullable = false)
    private Date birthdate;

    @Column(name = "patient_phone", nullable = false)
    private String phone;

    @CreationTimestamp
    @Column(name = "patient_creation_timestamp", nullable = false)
    private Instant creationTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

}
