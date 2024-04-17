package com.youtube.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youtube.ecommerce.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {

}
