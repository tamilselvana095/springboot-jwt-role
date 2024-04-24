package com.youtube.ecommerce.controller;



import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.ecommerce.entity.ImageModel;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.service.ProductService;



@RestController 
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
								 @RequestPart("imageFile") MultipartFile[] file) {
		
		
		try {
			Set<ImageModel> images=uploadImage(file);
			product.setProductImages(images);
			return productService.addNewProduct(product);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
		Set<ImageModel> imageModels=new HashSet<>();
		
		for(MultipartFile file:multipartFiles) {
			ImageModel imageModel=new ImageModel(
					file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes()
					);
			
			imageModels.add(imageModel);
		}
		
		return imageModels;
	}
	
	
	@GetMapping({"/getAllProducts"})
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	

	@GetMapping({"/getProductDetailsById/{productId}"})
	public Product getProductDetailsById(@PathVariable("productId")Integer productId) {
		return productService.getProductDetailsById(productId);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping({"/deleteProductDetails/{productId}"})
	public void deleteProductDetails(@PathVariable ("productId") Integer productId){
		 productService.deleteProductDetails(productId);
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
	public List<Product> getProductDetails(@PathVariable(name="isSingleProductCheckout") boolean isSingleProductCheckout,
								  @PathVariable(name="productId") Integer productId) {
		System.out.println("product details request");
		
		return productService.getProductDetails(isSingleProductCheckout, productId);
	}
	
	
}
