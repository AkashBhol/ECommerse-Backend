package com.example.Service;

import com.example.DTO.PageableDTO;
import com.example.DTO.Result;
import com.example.DTO.UpdatePasswordDTO;
import com.example.DTO.UserDTO;


public interface UserService {

    public Result createUser( UserDTO userDTO);

    public Result getAllUser();

    public Result getSingleUser(int id);

    public Result UpdatePassword(UpdatePasswordDTO updatePasswordDTO);

    public Result getAllsUser(PageableDTO pageableDTO);
}
