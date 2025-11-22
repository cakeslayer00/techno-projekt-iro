package com.technopark.iro.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "partners")
@AllArgsConstructor
@NoArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partners_seq_gen")
    @SequenceGenerator(name = "partners_seq_gen", sequenceName = "partners_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "university", nullable = false)
    private String university;

    @Column(name = "qs_ranking", nullable = false)
    private Integer qsRanking;

    @Column(name = "faculties", nullable = false)
    private String faculties;

    @Column(name = "date_of_sign", nullable = false)
    private LocalDate dateOfSign;

    @Column(name = "quota", nullable = false)
    private String quota;

    @Column(name = "status", nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "logo", nullable = false)
    private String logoUrl;

}
