package com.techmaster.blogappbackend.mapper;


import com.techmaster.blogappbackend.dto.UserDto;
import com.techmaster.blogappbackend.entity.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles()
        );
        return userDto;
    }
}
