package com.example.qldrl.services;

import com.example.qldrl.dto.DepartmentDTO;
import com.example.qldrl.dto.UserDTO;
import com.example.qldrl.entities.User;
import com.example.qldrl.entities.Department;
import com.example.qldrl.functions.CustomPasswordEncoder;
import com.example.qldrl.mapper.StudentMapper;
import com.example.qldrl.mapper.UserMapper;
import com.example.qldrl.repositories.DepartmentRepository;
import com.example.qldrl.repositories.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean changePassword(String rawPass, String encodePass) {
        return CustomPasswordEncoder.matches(rawPass, encodePass);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public Optional<User> getUsersByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDTO(savedUser);
    }

    public UserDTO updateUser(String username, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        CustomPasswordEncoder x = new CustomPasswordEncoder();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassWord(x.encode(userDTO.getPassWord()));
            User updatedUser1 = userRepository.save(user);
            return UserMapper.mapToUserDTO(updatedUser1);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public UserDTO updatePass(String username, String newPass) {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassWord(newPass);
            User updatedUser1 = userRepository.save(user);
            return UserMapper.mapToUserDTO(updatedUser1);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<User> authenticate(String userName, String passWord) {
        return userRepository.findByUserNameAndPassWord(userName, CustomPasswordEncoder.encode(passWord));
    }

    public Optional<UserDTO> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).map(UserMapper::mapToUserDTO);
    }

    public void importUsersFromExcel(MultipartFile file) throws IOException {
        List<UserDTO> userDTOList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Bỏ qua header
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                UserDTO userDTO = new UserDTO();

                // Đọc dữ liệu từ các ô và xử lý kiểu dữ liệu
                userDTO.setUserName(getCellValueAsString(currentRow.getCell(0)));
                userDTO.setPassWord(getCellValueAsString(currentRow.getCell(1)));
                userDTO.setRole(getCellValueAsString(currentRow.getCell(2)));

                userDTOList.add(userDTO);
            }
        }

        for (UserDTO userDTO : userDTOList) {
            createUser(userDTO);
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
