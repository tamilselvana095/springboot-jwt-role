package com.youtube.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.Cart;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;

@Service
public class CartService {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	public Cart addToCart(Integer productId) {
		Product product = productDao.findById(productId).get();
		
		String userName=JwtRequestFilter.CURRENT_USER;
		
		User user=null;
		
		if(userName !=null) {
			
			user= userDao.findById(userName).get();
		}
		
		List<Cart> cartList= cartDao.findByUser(user);
		
		List<Cart> filteredList= cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
		
		if(filteredList.size() >0) {
			return null;
		}
		
		if(product != null && user != null) {
		
			Cart cart = new Cart(product,user);
			return cartDao.save(cart);
		}
		
		return null;
	}
	
	public List<Cart> getCartDetails() {
		
		String userName=JwtRequestFilter.CURRENT_USER;
		User user=userDao.findById(userName).get();
		return cartDao.findByUser(user);
		
	}
	
	public void deleteCartItem(Integer cartId) {
		cartDao.deleteById(cartId);
	}

}
