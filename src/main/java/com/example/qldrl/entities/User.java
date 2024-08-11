package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Column(name = "TenDangNhap", columnDefinition = "varchar(12)")
    private String userName;
    @Column(name = "MatKhau", columnDefinition = "varchar(255)")
    private String passWord;
    @Column(name = "VaiTro", columnDefinition = "nvarchar(12)")
    private String role;

}