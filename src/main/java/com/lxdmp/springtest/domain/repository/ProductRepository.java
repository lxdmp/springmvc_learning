package com.lxdmp.springtest.domain.repository;

import java.util.List;
import com.lxdmp.springtest.domain.Product;

public interface ProductRepository
{
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	Product getProductById(String productId);
	void updateStock(String productId, long noOfUnits);
	void addProduct(Product product);
}

