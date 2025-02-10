package com.chat_app.chat.model;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String name;
    private String email;
    private String password;
}
