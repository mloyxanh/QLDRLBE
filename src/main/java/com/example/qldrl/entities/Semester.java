package com.example.qldrl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHK", columnDefinition = "integer")
    private Integer id;

    @Column(name = "TenHK", columnDefinition = "varchar(25)")
    private String name;

    @Column(name = "NgayBD")
    private LocalDate begin;

    @Column(name = "NgayKT")
    private LocalDate end;

    @Column(name = "NienKhoa", columnDefinition = "varchar(13)")
    private String academicYear;
}
