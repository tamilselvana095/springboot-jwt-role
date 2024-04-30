package com.youtube.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.User;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer>{

	public List<OrderDetail> findByUser(User user);
	
	public List<OrderDetail> findByOrderStatus(String status);
}
