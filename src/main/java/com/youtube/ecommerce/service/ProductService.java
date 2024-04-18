package com.youtube.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.entity.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	public Product addNewProduct(Product product) {
		return productDao.save(product);
	}
}
