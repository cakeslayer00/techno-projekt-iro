package com.technopark.iro.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "partners")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partners_seq_gen")
    @SequenceGenerator(name = "partners_seq_gen", sequenceName = "partners_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "university", nullable = false, length = 255)
    private String university;

    @Column(name = "qs_ranking")
    private Integer qsRanking;

    @Column(name = "faculties", nullable = false, columnDefinition = "TEXT")
    private String faculties;

    @Column(name = "date_of_sign", nullable = false)
    private LocalDate dateOfSign;

    @Column(name = "quota", nullable = false, length = 50)
    private String quota;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "logo", nullable = false, length = 500)
    private String logoUrl;

}