package com.youtube.jwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youtube.jwt.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {

}
