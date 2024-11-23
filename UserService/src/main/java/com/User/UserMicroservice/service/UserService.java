package com.User.UserMicroservice.service;

import com.User.UserMicroservice.dto.ChangePassword;
import com.User.UserMicroservice.dto.UserDto;
import com.User.UserMicroservice.dto.UserIdDto;
import com.User.UserMicroservice.persistence.entity.User;
import com.User.UserMicroservice.persistence.UserRepository;
import com.User.UserMicroservice.utils.JsonUtils;
import com.User.UserMicroservice.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;
    private KafkaTemplate<String, String> kafkaTemplate;


    public boolean logUpUser(UserDto userDto){

        User user = mapper.DtoToUser(userDto);

        log.info("User mapped {}",user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String userJson = JsonUtils.toJson(userDto);
        kafkaTemplate.send("userRegistered", userJson);
        return true;
    };

    public User userByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    public UserDto retrieveUser(String userMail) {
        User user = userByEmail(userMail);
        return mapper.userToDto(user);
    }
    public UserIdDto getUserById(Long id) {
        User user = userRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        String userId = String.valueOf(user.getId());
        log.info("User mapped {}",userId);
       return new UserIdDto(userId,user.getUsername(),user.getEmail(),user.getPassword());
    }
/*
    public User updateUser(Long id, UserDto userDto) {
        User existingUser = getUserById(id);
        mapper.updateUserFromDto(userDto, existingUser);
        return userRepository.save(existingUser);
    }
*/
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public String changePassword(ChangePassword changePassword) {

       User user = userRepository.findByEmail(changePassword.mail());

       if (!user.getPassword().matches(changePassword.oldPassword())){
           throw new RuntimeException("Old password does not match");
       }
       user.setPassword(passwordEncoder.encode(changePassword.newPassword()));
       userRepository.save(user);

       return "Password changed!";
    }

    public String addPost(String postId) {
        log.info("post ID {}",postId);
        return "";
    }

}
