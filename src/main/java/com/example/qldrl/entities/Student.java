package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @Column(name = "MaSV", columnDefinition = "varchar(12)")
    private String id;

    @Column(name = "MaSinhVien", columnDefinition = "nvarchar(255)")
    private String sId;

    @Column(name = "TenSV", columnDefinition = "nvarchar(255)")
    private String fullName;

    @Column(name = "SoDienThoai", columnDefinition = "varchar(20)")
    private String phoneNumber;

    @Column(name = "DiaChi", columnDefinition = "nvarchar(255)")
    private String address;

    @ManyToOne
    @JoinColumn(name = "MaLop")
    private Class clazz;

    @ManyToOne
    @JoinColumn(name = "MaKhoa")
    private Department department;

    @OneToOne
    @JoinColumn(name = "MaUser", unique = true)
    private User user;
}
