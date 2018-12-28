package com.lxdmp.springtest.entity.repository;

import java.util.List;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.utils.Paginator;

public interface ProductRepository
{
	List<Product> getAllProducts();
	int getAllProductsNum();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByPage(Integer pageNo, Integer pageSize);
	Product getProductById(String productId);

	void updateStock(String productId, long noOfUnits);
	void addProduct(Product product);
}

