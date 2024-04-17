package com.youtube.ecommerce.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.youtube.ecommerce.dao.RoleDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.Role;
import com.youtube.ecommerce.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerNewUser(User user) {
		Role role=roleDao.findById("User").get();
		
		Set<Role>roles=new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		
		return userDao.save(user);
	}
	
	public void initRolesAndUser() {
		    Role adminRole = new Role();
	        adminRole.setRoleName("Admin");
	        adminRole.setRoleDescription("Admin role");
	        roleDao.save(adminRole);

	        Role userRole = new Role();
	        userRole.setRoleName("User");
	        userRole.setRoleDescription("Default role for newly created record");
	        roleDao.save(userRole);

	        User adminUser = new User();
	        adminUser.setUserName("admin123");
	        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
	        adminUser.setUserFirstName("admin");
	        adminUser.setUserLastName("admin");
	        Set<Role> adminRoles = new HashSet<>();
	        adminRoles.add(adminRole);
	        adminUser.setRole(adminRoles);
	        userDao.save(adminUser);
	        
	        User user = new User();
	        user.setUserName("raj123");
	        user.setUserPassword(getEncodedPassword("raj@123"));
	        user.setUserFirstName("raj");
	        user.setUserLastName("sharma");
	        Set<Role> userRoles = new HashSet<>();
	        userRoles.add(userRole);
	        user.setRole(userRoles);
	        userDao.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
