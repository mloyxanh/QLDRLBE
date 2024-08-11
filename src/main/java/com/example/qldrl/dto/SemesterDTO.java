package com.example.qldrl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterDTO {
    private Integer id;
    private String name;
    private LocalDate begin;
    private LocalDate end;
    private String academicYear;
}
