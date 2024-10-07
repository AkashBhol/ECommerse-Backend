package com.example.Repository;

import com.example.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public boolean existsByRoleName(String rollName);

    List<Role> findByRoleNameIn(List<String> names);
}
