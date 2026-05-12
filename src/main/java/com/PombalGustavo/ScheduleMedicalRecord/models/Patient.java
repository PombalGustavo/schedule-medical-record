package com.PombalGustavo.ScheduleMedicalRecord.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.processing.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID patientId;

    @Column(name = "patient_name", nullable = false)
    private String name;

    @Column(name = "patient_cpf", nullable = false, unique = true)
    @CPF
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clinic clinic;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

}
