package com.example.qldrl.mapper;

import com.example.qldrl.dto.SemesterDTO;
import com.example.qldrl.entities.Semester;

public class SemesterMapper {

    // Chuyển đổi từ Semester entity sang SemesterDTO
    public static SemesterDTO mapToSemesterDTO(Semester semester) {
        return new SemesterDTO(
                semester.getId(),
                semester.getName(),
                semester.getBegin(),
                semester.getEnd(),
                semester.getAcademicYear()
        );
    }

    // Chuyển đổi từ SemesterDTO sang Semester entity
    public static Semester mapToSemester(SemesterDTO semesterDTO) {
        return new Semester(
                semesterDTO.getId(),
                semesterDTO.getName(),
                semesterDTO.getBegin(),
                semesterDTO.getEnd(),
                semesterDTO.getAcademicYear()
        );
    }
}
