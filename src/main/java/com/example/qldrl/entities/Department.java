package com.example.qldrl.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "MaKhoa", columnDefinition = "varchar(12)")
    private String id;
    @Column(name = "TenKhoa", columnDefinition = "nvarchar(255)")
    private String Name;
}

