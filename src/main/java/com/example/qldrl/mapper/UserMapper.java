package com.example.qldrl.mapper;

import com.example.qldrl.dto.UserDTO;
import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.User;
import com.example.qldrl.functions.CustomPasswordEncoder;

public class UserMapper {
    public static UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getUserName(),
                user.getPassWord(),
                user.getRole()
        );
    }

    public static User mapToUser(UserDTO userDTO) {
        CustomPasswordEncoder x = new CustomPasswordEncoder();
        String pwdencode = x.encode(userDTO.getPassWord());
        return new User(
                userDTO.getUserName(),
                pwdencode,
                userDTO.getRole()
        );
    }
}
