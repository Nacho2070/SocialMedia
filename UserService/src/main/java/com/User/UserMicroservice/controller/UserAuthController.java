package com.User.UserMicroservice.controller;

import com.User.UserMicroservice.dto.ChangePassword;
import com.User.UserMicroservice.dto.UserDto;

import com.User.UserMicroservice.dto.UserIdDto;
import com.User.UserMicroservice.persistence.entity.User;
import com.User.UserMicroservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authUser")
@AllArgsConstructor
public class UserAuthController{

    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Object> logUpUser(@RequestBody UserDto userDto){
        if(userService.logUpUser(userDto)){
            return new ResponseEntity<>(null,HttpStatus.CREATED);
        };
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody UserDto userDto) {
        try {
            userService.logUpUser(userDto);
            return ResponseEntity.ok("User successfully created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {

            if (userService.userByEmail(userDto.email()) != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
            return ResponseEntity.ok("User updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword passwordDTO) {
           if(userService.changePassword(passwordDTO) != null ){
               return ResponseEntity.ok("Password changed successfully!");
           };
        return ResponseEntity.ok("");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserIdDto> getUser(@PathVariable Long id) {
         return ResponseEntity.ok(userService.getUserById(id));
    }
}
