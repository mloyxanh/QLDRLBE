package com.example.qldrl.dto;

import com.example.qldrl.entities.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String id;
    private String sId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String clazz;
    private String department;
    private String user;
}
