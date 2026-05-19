package com.PombalGustavo.ScheduleMedicalRecord.models;

import com.PombalGustavo.ScheduleMedicalRecord.infra.Crypto.CryptoService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_medicalRecord")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID medicalRecordId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String encryptedContentText;

    @CreatedDate
    @Column(name = "medical_record_created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "medical_record_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmnet_id", nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient
    private String rawContentText;

    @PrePersist
    public void prePersist() {
        this.encryptedContentText = CryptoService.encrypt(rawContentText);
    }

    @PostLoad
    public void postLoad() {
        this.rawContentText = CryptoService.decrypt(encryptedContentText);
    }

}
