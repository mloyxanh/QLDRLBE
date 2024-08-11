package com.example.qldrl.services;

import com.example.qldrl.dto.AdvisorDTO;
import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.entities.*;
import com.example.qldrl.entities.Class;
import com.example.qldrl.mapper.AdvisorMapper;
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
public class AdvisorService {
    private final AdvisorRepository advisorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    private final ClassRepository classRepository;
    @Autowired
    public AdvisorService(AdvisorRepository advisorRepository, ClassRepository classRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.advisorRepository = advisorRepository;
        this.classRepository = classRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }
    public List<AdvisorDTO> getAllAdvisors() {
        return advisorRepository.findAll().stream()
                .map(AdvisorMapper::mapToAdvisorDTO)
                .collect(Collectors.toList());
    }

    public AdvisorDTO createAdvisor(AdvisorDTO advisorDTO) {
        Department department = departmentRepository.findById(advisorDTO.getDepartment())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        User user = userRepository.findById(advisorDTO.getUser())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Class clazz = classRepository.findById(advisorDTO.getClazz())
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));
        Advisor adv = AdvisorMapper.mapToAdvisor(advisorDTO, clazz, department, user);
        Advisor savedAdvisor = advisorRepository.save(adv);
        return AdvisorMapper.mapToAdvisorDTO(savedAdvisor);
    }

    public AdvisorDTO updateAdvisor(String id, AdvisorDTO advisorDTO) {
        Optional<Advisor> optionalAdvisor = advisorRepository.findById(id);
        if (optionalAdvisor.isPresent()) {
            Advisor advisor = optionalAdvisor.get();
            Department department = departmentRepository.findById(advisorDTO.getDepartment())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
            User user = userRepository.findById(advisorDTO.getUser())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            Class clazz = classRepository.findById(advisorDTO.getClazz())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));
            advisor.setFullName(advisorDTO.getFullName());
            advisor.setPhoneNumber(advisorDTO.getPhoneNumber());
            advisor.setAddress(advisorDTO.getAddress());
            advisor.setClazz(clazz);
            advisor.setDepartment(department);
            advisor.setUser(user);

            Advisor updatedAdvisor = advisorRepository.save(advisor);
            return AdvisorMapper.mapToAdvisorDTO(updatedAdvisor);
        } else {
            throw new RuntimeException("Advisor Not Found");
        }
    }

    public void deleteAdvisor(String id) {
        if (advisorRepository.existsById(id)) {
            advisorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    public Optional<AdvisorDTO> getAdvisorById(String id) {
        return advisorRepository.findById(id).map(AdvisorMapper::mapToAdvisorDTO);
    }

    public Optional<AdvisorDTO> getAdvisorByUser(String user) {
        return advisorRepository.findByUser_UserName(user).map(AdvisorMapper::mapToAdvisorDTO);
    }

    public Optional<AdvisorDTO> getAdvisorByClass(String clazz) {
        return advisorRepository.findByClazz_Id(clazz).map(AdvisorMapper::mapToAdvisorDTO);
    }

    public List<AdvisorDTO> getStudentsByDepartment(String department) {
        return advisorRepository.findByDepartment_Id(department).stream()
                .map(AdvisorMapper::mapToAdvisorDTO)
                .collect(Collectors.toList());
    }

    public void importAdvisorsFromExcel(MultipartFile file) throws IOException {
        List<AdvisorDTO> advisorDTOList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Bỏ qua header
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                AdvisorDTO advisorDTO = new AdvisorDTO();
                advisorDTO.setId(getCellValueAsString(currentRow.getCell(0)));
                advisorDTO.setFullName(getCellValueAsString(currentRow.getCell(1)));
                advisorDTO.setDepartment(getCellValueAsString(currentRow.getCell(2)));
                advisorDTO.setAddress(getCellValueAsString(currentRow.getCell(3)));
                advisorDTO.setClazz(getCellValueAsString(currentRow.getCell(4)));
                advisorDTO.setPhoneNumber(getCellValueAsString(currentRow.getCell(5)));
                advisorDTO.setUser(getCellValueAsString(currentRow.getCell(6)));
                System.out.println(advisorDTO.getId());
                advisorDTOList.add(advisorDTO);
            }
        }

        for (AdvisorDTO advisorDTO : advisorDTOList) {
            createAdvisor(advisorDTO);
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
