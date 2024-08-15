package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationDetail {

    @Id
    @Column(name = "MaCTCham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MaPhieu")
    private Evaluation evaluation;

    @OneToOne
    @JoinColumn(name = "MaTieuChiPhu")
    private SubCriteriaType1 subCriteriaType;

    @Column(name = "DiemSo", columnDefinition = "float")
    private Float score;

}
