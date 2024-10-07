package com.example.ServiceImpl;
import com.example.Config.BsicUtil;
import com.example.DTO.*;
import com.example.Model.Role;
import com.example.Model.User;
import com.example.Repository.RoleRepository;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends BsicUtil implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Result createUser(UserDTO userDTO) {
        boolean b = userRepository.existsByEmail(userDTO.getEmail());
        if (b) {
            return prepareResponseObject("006", "User Exists By Given Email", null);
        }
        List<Role> singleRole = roleRepository.findByRoleNameIn(userDTO.getRoles());
        if (isNullOrEmpty(singleRole)) {
            return prepareResponseObject("007", "No Role Was Present With That RoleNAme", null);
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setAbout(userDTO.getAbout());
        user.setRoles(singleRole);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        if (isNullOrEmpty(user)) {
            return prepareResponseObject("008", "User is Empty", null);
        }
        User user1 = userRepository.save(user);
        log.warn("After calling the save {}", user1);
        if (isNullOrEmpty(user1)) {
            return prepareResponseObject("009", "User1 is Not present", null);
        }
        return prepareResponseObject("010", "User created Successfully", user1);
    }

    @Override
    public Result getAllUser() {
        List<User> allUser = userRepository.findAll();
        log.warn("calling to findAll {}", allUser);
        if (isNullOrEmpty(allUser)) {
            return prepareResponseObject("011", "No user are Available", null);
        }
        return prepareResponseObject("012", "All User Fetched Successfully", allUser);
    }

    @Override
    public Result getSingleUser(int id) {
        return null;
    }

    @Override
    public Result UpdatePassword(UpdatePasswordDTO updatePasswordDTO) {

        if (isNullOrEmpty(updatePasswordDTO.getOldPassword())) {
            return prepareResponseObject("049", "OldPAssword Should Not be empty", null);
        }
        if (isNullOrEmpty(updatePasswordDTO.getNewPassword())) {
            return prepareResponseObject("050", "NewPassword Should Not be Empty", null);
        }
        if (isNullOrEmpty(updatePasswordDTO.getConformPassword())) {
            return prepareResponseObject("051", "conform password should not be empty", null);
        }
        if (updatePasswordDTO.getOldPassword().equals(updatePasswordDTO.getNewPassword())) {
            return prepareResponseObject("047", "Oldpassword and newPAssword Should Not BE same", null);
        }
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConformPassword())) {
            return prepareResponseObject("048", "Newpassword and conformPassword should be same", null);
        }
        User byEmail = userRepository.findByEmail(updatePasswordDTO.getEmail());
        byEmail.setPassword(passwordEncoder.encode(updatePasswordDTO.getConformPassword()));
        return prepareResponseObject("049", "Password Updated SuccessFully", userRepository.save(byEmail));


    }

    @Override
    public Result getAllsUser(PageableDTO pageableDTO) {
        Pageable pageable = null;
        pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getPageSize());
        Page<User> all = userRepository.findAll(pageable);
        PageableResponseVO pageableResponseVO = new PageableResponseVO(all.getContent(), all, pageableDTO);
        return prepareResponseObject("055", "User Fetched Successfully", pageableResponseVO);
    }


    @Override
    public Result updateProduct(int id, String productName, String productDescription, String price, String quantity, String file) {
        return null;
    }

    private Result isValid(UpdatePasswordDTO updatePasswordDTO) {
        if (isNullOrEmpty(updatePasswordDTO.getOldPassword())) {
            return prepareResponseObject("049", "OldPAssword Should Not be empty", null);
        }
        if (isNullOrEmpty(updatePasswordDTO.getNewPassword())) {
            return prepareResponseObject("050", "NewPassword Should Not be Empty", null);
        }
        if (isNullOrEmpty(updatePasswordDTO.getConformPassword())) {
            return prepareResponseObject("051", "conform password should not be empty", null);
        }
        return prepareResponseObject("052", "", null);
    }
}
