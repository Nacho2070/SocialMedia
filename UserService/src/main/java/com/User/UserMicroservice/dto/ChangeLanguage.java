package com.User.UserMicroservice.dto;

import com.User.UserMicroservice.enums.Language;

public record ChangeLanguage(
        Long userId,
        Language language
) {
}
