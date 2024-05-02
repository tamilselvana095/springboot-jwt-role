package com.youtube.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
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
import com.youtube.ecommerce.entity.TransactionDetails;
import com.youtube.ecommerce.entity.User;

@Service
public class OrderDetailService {

	private static final String ORDER_PLACED = "placed";
	private static final String KEY = "rzp_test_8vq9yyCNE8Avnr";
	private static final String KEY_SECRET = "j59eHxtCInhehPfH9p7hpzIb";
	private static final String CURRENCY = "INR";

	@Autowired
	private OrderDetailDao orderDetailDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CartDao cartDao;

	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

		for (OrderProductQuantity o : productQuantityList) {

			Product product = productDao.findById(o.getProductId()).get();

			String currentUser = JwtRequestFilter.CURRENT_USER;
			User user = userDao.findById(currentUser).get();

			OrderDetail orderDetail = new OrderDetail(orderInput.getFullName(), orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(), ORDER_PLACED,
					product.getProductDiscountedPrice() * o.getQuantity(), product, user,
					orderInput.getTransactionId()

			);

			// empty the cart

			if (!isSingleProductCheckout) {

				List<Cart> carts = cartDao.findByUser(user);
				carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
			}

			orderDetailDao.save(orderDetail);
		}
	}

	public List<OrderDetail> getOrderDetails() {
		String username = JwtRequestFilter.CURRENT_USER;

		User user = userDao.findById(username).get();

		return orderDetailDao.findByUser(user);
	}

	public List<OrderDetail> getAllOrderDetails(String status) {

		List<OrderDetail> orderDetails = new ArrayList<>();

		if (status.equals("All")) {

			orderDetailDao.findAll().forEach(x -> orderDetails.add(x));
		} else {

			orderDetailDao.findByOrderStatus(status).forEach(x -> orderDetails.add(x));
		}

		return orderDetails;
	}

	public void markOrderAsDelivered(Integer orderId) {
		OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

		if (orderDetail != null) {
			orderDetail.setOrderStatus("Delivered");
			orderDetailDao.save(orderDetail);
		}
	}

	public TransactionDetails createTransaction(Double amount) {
		// amount
		// currency
		// key
		// secret key

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", (amount * 100));
			jsonObject.put("currency", CURRENCY);

			RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

			Order order = razorpayClient.orders.create(jsonObject);

//			System.out.println(order);

			TransactionDetails transactionDetails = prepareTransactionDetails(order);

			return transactionDetails;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return null;

	}

	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		String currency = order.get("currency");
		Integer amount = order.get("amount");

		TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount,KEY);
		return transactionDetails;
	}

}
