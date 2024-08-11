package com.example.qldrl.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriteriaType1 {
    @Id
    @Column(name = "MaLoai1", columnDefinition = "varchar(12)")
    private String id;
    @Column(name = "NoiDungTieuChi", columnDefinition = "varchar(840)")
    private String mainContent;
    @Column(name = "DiemToiDa", columnDefinition = "integer")
    private Integer score;
}
