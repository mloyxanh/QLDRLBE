package com.example.qldrl.mapper;

import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.entities.Advisor;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.User;

public class ClassMapper {
    public static ClassDTO mapToClassDTO(Class clazz) {
        return new ClassDTO(
                clazz.getId(),
                clazz.getName(),
                clazz.getDepartment() != null ? clazz.getDepartment().getId() : null,
                clazz.getSchoolYear(),
                clazz.getUser() != null ? clazz.getUser().getUserName() : null
        );
    }

    public static Class mapToClass(ClassDTO classDTO, Department department, User user) {
        return new Class(
                classDTO.getId(),
                classDTO.getName(),
                department,
                classDTO.getSchoolYear(),
                user
        );
    }
}
