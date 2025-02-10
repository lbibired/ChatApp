package com.chat_app.chat.repository;

import com.chat_app.chat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

    Users findByEmail(String email);
}
