package com.usermanagement.mapper;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.User;

import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}