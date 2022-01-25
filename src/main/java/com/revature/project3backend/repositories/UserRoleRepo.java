package com.revature.project3backend.repositories;

import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

    /**
     * Retrieves the role from the database given a role name
     */
    @Query(value = "SELECT * FROM user_roles WHERE role LIKE :role", nativeQuery = true)
    UserRole findUserRoleByName(@Param("role") String roleName);
}
