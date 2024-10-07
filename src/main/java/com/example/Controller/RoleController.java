package com.example.Controller;
import com.example.DTO.Result;
import com.example.Model.Role;
import com.example.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role/create")
    public Result createRole(@RequestBody Role role){
        return roleService.createRole(role);
    }

    @GetMapping("/role/get")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result getAllRole(){
        return roleService.getAllRole();
    }
}
