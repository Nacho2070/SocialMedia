package com.User.UserMicroservice.persistence;

import com.User.UserMicroservice.persistence.entity.User;

public interface UserRepository extends Repository<User, Long> {
    User findByEmail(String userMail);

    User findByPassword(String s);
}
