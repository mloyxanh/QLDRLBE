package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evaluation {

    @Id
    @Column(name = "MaPhieu", columnDefinition = "varchar(12)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "MaSV", unique = true)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "MaCV")
    private Advisor advisor;

    @ManyToOne
    @JoinColumn(name = "MaLop")
    private Class clazz;

    @Column(name = "NgayTao")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "MaHK", referencedColumnName = "MaHK")
    private Semester semester;

    @Column(name = "KhoaDanhGia")
    private Boolean reviewedByAdvisor;
    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationDetail> evaluationDetails;

    public void removeEvaluationDetail(EvaluationDetail evaluationDetail) {
        this.evaluationDetails.remove(evaluationDetail);
        evaluationDetail.setEvaluation(null);
    }

    public void addEvaluationDetail(EvaluationDetail evaluationDetail) {
        this.evaluationDetails.add(evaluationDetail);
        evaluationDetail.setEvaluation(this);
    }

}
