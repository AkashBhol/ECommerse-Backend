package com.example.ServiceImpl;

import com.example.Config.BsicUtil;
import com.example.DTO.Result;
import com.example.Model.Role;
import com.example.Repository.RoleRepository;
import com.example.Service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends BsicUtil implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);


    @Override
    @Transactional
    public Result createRole(Role role) {
        if (isNullOrEmpty(role)) {
            return prepareResponseObject("001", "Role is Empty", null);
        }
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            return prepareResponseObject("002", "Role exists with that given Name", null);
        }
        Role role1 = roleRepository.save(role);
        log.warn("After calling the save Method {}", role1);
        return prepareResponseObject("003", "Role created  successFully", role1);
    }

    @Override
    public Result getAllRole() {
        List<Role> allrepository = roleRepository.findAll();
        if(isNullOrEmpty(allrepository)){
            return prepareResponseObject("004","Role is Empty",null);
        }
        log.warn("calling to the findAll {}",allrepository);
        return prepareResponseObject("005","Role fetched successFully",allrepository);
    }

    @Override
    public Result updateProduct(int id, String productName, String productDescription, String price, String quantity, String file) {
        return null;
    }
}
