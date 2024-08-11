package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Class {
    @Id
    @Column(name = "MaLop", columnDefinition = "varchar(12)")
    private String id;
    @Column(name = "TenLop", columnDefinition = "nvarchar(255)")
    private String name;
    @ManyToOne
    @JoinColumn(name = "MaKhoa")
    private Department department;
    @Column(name = "NienKhoa", columnDefinition = "varchar(12)")
    private String schoolYear;
    @OneToOne
    @JoinColumn(name = "MaUser", unique = true)
    private User user;
    public String getId() {
        return id;
    }

}
