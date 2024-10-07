package com.example.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String email;

    private String password;

    private String name;

    private String about;

    private List<String> roles;

}
