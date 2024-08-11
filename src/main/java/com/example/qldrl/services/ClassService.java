package com.example.qldrl.services;

import com.example.qldrl.dto.AdvisorDTO;
import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.dto.DepartmentDTO;
import com.example.qldrl.entities.Advisor;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.User;
import com.example.qldrl.mapper.AdvisorMapper;
import com.example.qldrl.mapper.ClassMapper;
import com.example.qldrl.mapper.StudentMapper;
import com.example.qldrl.repositories.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {
    private final ClassRepository classRepository;
    private final DepartmentRepository departmentRepository;
    private final AdvisorRepository advisorRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClassService(ClassRepository classRepository, DepartmentRepository departmentRepository, AdvisorRepository advisorRepository, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.advisorRepository = advisorRepository;
    }

    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll().stream()
                .map(ClassMapper::mapToClassDTO)
                .collect(Collectors.toList());
    }

    public ClassDTO createClass(ClassDTO classDTO) {
        Department department = departmentRepository.findById(classDTO.getDepartment())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        User user = userRepository.findById(classDTO.getUser())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Class clazz = ClassMapper.mapToClass(classDTO, department, user);
        Class savedClass = classRepository.save(clazz);
        return ClassMapper.mapToClassDTO(savedClass);
    }

    public ClassDTO updateClass(String id, ClassDTO classDTO) {
        Department department = departmentRepository.findById(classDTO.getDepartment())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        User user = userRepository.findById(classDTO.getUser())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));
        Optional<Class> optionalClass = classRepository.findById(id);
        if (optionalClass.isPresent()) {
            Class clazz = optionalClass.get();
            clazz.setName(classDTO.getName());
            clazz.setSchoolYear(classDTO.getSchoolYear());
            clazz.setDepartment(department);
            clazz.setUser(user);
            Class updatedClass = classRepository.save(clazz);
            return ClassMapper.mapToClassDTO(updatedClass);
        } else {
            throw new RuntimeException("Class not found");
        }
    }

    public void deleteClass(String id) {
        if (classRepository.existsById(id)) {
            classRepository.deleteById(id);
        } else {
            throw new RuntimeException("Class not found");
        }
    }
    public Optional<ClassDTO> getClassById(String id) {
        return classRepository.findById(id).map(ClassMapper::mapToClassDTO);
    }
    public Optional<ClassDTO> getClassByUser(String user) {
        return classRepository.findByUser_UserName(user).map(ClassMapper::mapToClassDTO);
    }

    public List<ClassDTO> getClassByDepartment(String department) {
        return classRepository.findByDepartment_Id(department).stream()
                .map(ClassMapper::mapToClassDTO)
                .collect(Collectors.toList());
    }

    public void importClassesFromExcel(MultipartFile file) throws IOException {
        List<ClassDTO> classDTOList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Bỏ qua header
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                ClassDTO classDTO = new ClassDTO();
                classDTO.setId(getCellValueAsString(currentRow.getCell(0)));
                classDTO.setName(getCellValueAsString(currentRow.getCell(1)));
                classDTO.setDepartment(getCellValueAsString(currentRow.getCell(2)));
                classDTO.setSchoolYear(getCellValueAsString(currentRow.getCell(3)));
                classDTO.setUser(getCellValueAsString(currentRow.getCell(4)));
                classDTOList.add(classDTO);
            }
        }

        for (ClassDTO classDTO : classDTOList) {
            createClass(classDTO);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Sử dụng BigDecimal để giữ chính xác hơn
                return new BigDecimal(cell.getNumericCellValue()).toString();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}