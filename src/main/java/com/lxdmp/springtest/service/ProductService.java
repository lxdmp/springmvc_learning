package com.lxdmp.springtest.service;

import java.util.List;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.utils.Paginator;

public interface ProductService
{
	List<Product> getAllProducts();
	int getAllProductsNum();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByPage(Integer pageNo, Integer pageSize);
	Product getProductById(String productId);

	void updateAllStock();
	void addProduct(Product product);
}

