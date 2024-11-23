package com.User.UserMicroservice.utils;

import com.User.UserMicroservice.dto.UserDto;
import com.User.UserMicroservice.enums.STATUS;
import com.User.UserMicroservice.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public User DtoToUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        User user = new User();
                user.setUsername(userDto.username());
                user.setEmail(userDto.email());
                user.setPassword(userDto.password());
                return user;
    }

    public void updateUserFromDto(UserDto userDto, User existingUser) {

        if (userDto.username() != null) {
            existingUser.setUsername(userDto.username());
        }
        if (userDto.email() != null) {
            existingUser.setEmail(userDto.email());
        }
        if (userDto.password() != null) {
            existingUser.setPassword(userDto.password());
        }
    }

    public UserDto userToDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                null
        );
    }
}
