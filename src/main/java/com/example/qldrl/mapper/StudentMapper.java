package com.example.qldrl.mapper;

import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.Student;
import com.example.qldrl.entities.User;

public class StudentMapper {
    public static StudentDTO mapToStudentDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setSId(student.getSId());
        dto.setFullName(student.getFullName());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setAddress(student.getAddress());
        dto.setClazz(student.getClazz().getId());
        dto.setDepartment(student.getDepartment().getId());
        dto.setUser(student.getUser().getUserName());
        return dto;
    }

    public static Student mapToStudent(StudentDTO studentDTO, Class clazz, Department department, User user) {
        return new Student(
                studentDTO.getId(),
                studentDTO.getSId(),
                studentDTO.getFullName(),
                studentDTO.getPhoneNumber(),
                studentDTO.getAddress(),
                clazz,
                department,
                user
        );
    }
}
