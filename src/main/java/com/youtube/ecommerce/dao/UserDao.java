package com.youtube.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youtube.ecommerce.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

}
