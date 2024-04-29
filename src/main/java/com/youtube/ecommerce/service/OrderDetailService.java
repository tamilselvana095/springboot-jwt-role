package com.youtube.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.OrderDetailDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.Cart;
import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.OrderInput;
import com.youtube.ecommerce.entity.OrderProductQuantity;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;

@Service
public class OrderDetailService {
	
	private static final String ORDER_PLACED="placed";
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	
	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<OrderProductQuantity> productQuantityList=orderInput.getOrderProductQuantityList();
		
		for(OrderProductQuantity o: productQuantityList) {
			
			Product product=productDao.findById(o.getProductId()).get();
			
			String currentUser=JwtRequestFilter.CURRENT_USER;
			User user=userDao.findById(currentUser).get();
			
			OrderDetail orderDetail=new OrderDetail(
					orderInput.getFullName(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					ORDER_PLACED,
					product.getProductDiscountedPrice() * o.getQuantity(),
					product,
					user
					
					);
			
			//empty the cart
			
			if(!isSingleProductCheckout) {
				
				List<Cart> carts = cartDao.findByUser(user);
				carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
			}
			
			orderDetailDao.save(orderDetail);
		}
	}

}
