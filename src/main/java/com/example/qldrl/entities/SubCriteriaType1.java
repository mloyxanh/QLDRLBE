package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCriteriaType1 {
    @Id
    @Column(name = "MaLoaiPhu1", columnDefinition = "varchar(12)")
    private String id;
    @Column(name = "NoiDung", columnDefinition = "varchar(520)")
    private String content;
    @Column(name = "DiemToiDa", columnDefinition = "integer")
    private Integer score;
    @ManyToOne
    @JoinColumn(name = "MaLoai1")
    private CriteriaType1 criteriaType1;
}
