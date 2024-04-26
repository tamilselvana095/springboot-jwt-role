package com.youtube.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	public List<Product> getAllProducts(int pageNumber){
		
		Pageable pageable = PageRequest.of(pageNumber, 12);
		return productDao.findAll(pageable);
	}
	
	public Product getProductDetailsById(Integer productId) {
		return productDao.findById(productId).get();
	}
	
	public void deleteProductDetails(Integer productId) {
		productDao.deleteById(productId);
	}
	
	public List<Product> getProductDetails(boolean isSingleProductCheckout,Integer productId) {
		if(isSingleProductCheckout) {
			// we are going to buy single product
			
			List<Product> list=new ArrayList<>();
			Product product = productDao.findById(productId).get();
			list.add(product);
			//System.out.println(list);
			
			return list;
		}else {
			//we are going to checkout entire cart
		}
		
		return new ArrayList<>();
	}
}
