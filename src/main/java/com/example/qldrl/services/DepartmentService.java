package com.example.qldrl.services;

import com.example.qldrl.dto.DepartmentDTO;
import com.example.qldrl.entities.Department;
import com.example.qldrl.mapper.ClassMapper;
import com.example.qldrl.mapper.DepartmentMapper;
import com.example.qldrl.repositories.DepartmentRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::mapToDepartmentDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = DepartmentMapper.mapToDepartment(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDTO(savedDepartment);
    }

    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setName(departmentDTO.getName());
            Department updatedDepartment = departmentRepository.save(department);
            return DepartmentMapper.mapToDepartmentDTO(updatedDepartment);
        } else {
            throw new RuntimeException("Department not found");
        }
    }

    public void deleteDepartment(String id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Department not found");
        }
    }

    public void importDepartmentsFromExcel(MultipartFile file) throws IOException {
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Bỏ qua header
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                DepartmentDTO departmentDTO = new DepartmentDTO();

                departmentDTO.setId(currentRow.getCell(0).getStringCellValue());
                departmentDTO.setName(currentRow.getCell(1).getStringCellValue());

                departmentDTOList.add(departmentDTO);
            }
        }

        for (DepartmentDTO departmentDTO : departmentDTOList) {
            createDepartment(departmentDTO);
        }
    }

    public Optional<DepartmentDTO> getDeptById(String id) {
        return departmentRepository.findById(id).map(DepartmentMapper::mapToDepartmentDTO);
    }
}