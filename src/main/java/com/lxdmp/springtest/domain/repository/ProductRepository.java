package com.lxdmp.springtest.domain.repository;

import java.util.List;
import com.lxdmp.springtest.domain.Product;

public interface ProductRepository
{
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	void updateStock(String productId, long noOfUnits);
}

