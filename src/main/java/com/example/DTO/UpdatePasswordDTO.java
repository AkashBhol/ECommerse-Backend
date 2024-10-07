package com.example.DTO;

import lombok.Data;

@Data
public class UpdatePasswordDTO {

    private String email;

    private String oldPassword;

    private String newPassword;

    private String conformPassword;
}
