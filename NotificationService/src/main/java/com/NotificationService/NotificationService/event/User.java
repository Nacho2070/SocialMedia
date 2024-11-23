package com.NotificationService.NotificationService.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record User(
        String username,
        String email,
        String password
) {}