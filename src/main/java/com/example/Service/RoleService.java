package com.example.Service;

import com.example.DTO.Result;
import com.example.Model.Role;

import java.util.List;

public interface RoleService {
    public Result createRole(Role role);

    public Result getAllRole();
}
