package com.lxdmp.springtest.dao;

import java.util.List;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.utils.Paginator;

public interface ProductDao
{
	List<Product> getAllProducts();
	int getAllProductsNum();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByPage(Paginator<Product> paginator);
	Product getProductById(String productId);

	void updateStock(String productId, long noOfUnits);
	void addProduct(Product product);
}
