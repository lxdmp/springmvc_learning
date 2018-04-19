package com.lxdmp.springtest.service;

import java.util.List;
import com.lxdmp.springtest.domain.Product;

public interface ProductService
{
	List<Product> getAllProducts();
	void updateAllStock();
}

