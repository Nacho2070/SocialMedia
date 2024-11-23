package com.User.UserMicroservice.dto;

public record ChangePassword (
        String mail,
        String oldPassword,
        String newPassword
) {}

