package com.revature.project3backend.services;

import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService {

    private UserRoleRepo roleRepo;

    @Autowired
    public RoleService(UserRoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }

    public UserRole getRoleByName(String roleName) {
        return this.roleRepo.findUserRoleByName(roleName);
    }
}
