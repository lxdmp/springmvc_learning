package com.lxdmp.springtest.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.domain.Product;
import com.lxdmp.springtest.domain.repository.ProductRepository;
import com.lxdmp.springtest.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts()
	{
		return productRepository.getAllProducts();
	}

	@Override
	public void updateAllStock()
	{
		List<Product> allProducts = productRepository.getAllProducts();
		for(Product product : allProducts)
		{
			if(product.getUnitsInStock()<500)
				productRepository.updateStock(product.getProductId(), product.getUnitsInStock()+1000);
		}
	}
}

