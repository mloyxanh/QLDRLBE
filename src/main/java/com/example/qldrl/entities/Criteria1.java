package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Criteria1 {
    @Id
    @Column(name = "MaTieuChi1", columnDefinition = "integer")
    private Integer id;
    @Column(name = "NoiDung", columnDefinition = "varchar(520)")
    private String content;
    @Column(name = "DiemToiDa", columnDefinition = "integer")
    private Integer score;
    @ManyToOne
    @JoinColumn(name = "MaLoai")
    private SubCriteriaType1 subCriteriaType1;
}
