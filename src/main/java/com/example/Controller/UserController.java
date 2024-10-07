package com.example.Controller;

import com.example.DTO.PageableDTO;
import com.example.DTO.Result;
import com.example.DTO.UpdatePasswordDTO;
import com.example.DTO.UserDTO;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/create")
    public Result createUser(@RequestBody UserDTO userDTO){
        Result user = userService.createUser(userDTO);
        return user;
    }

    @GetMapping("/user/get")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result getAllUser(){
        Result allUser = userService.getAllUser();
        return allUser;
    }

    @PutMapping("/user/updatePasswoed")
    public Result UpdatePassword(@RequestBody UpdatePasswordDTO UpdatePassword){
        Result result = userService.UpdatePassword(UpdatePassword);
        return result;
    }

    @GetMapping("/user/get/all")
    public Result getAllsUser(PageableDTO pageableDTO){
        Result allsUser = userService.getAllsUser(pageableDTO);
        return  allsUser;
    }
}
