package com.youtube.jwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youtube.jwt.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

}
