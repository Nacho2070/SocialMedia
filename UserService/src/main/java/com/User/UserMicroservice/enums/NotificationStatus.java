package com.User.UserMicroservice.enums;

public enum NotificationStatus {
    ACTIVE,
    INACTIVE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
