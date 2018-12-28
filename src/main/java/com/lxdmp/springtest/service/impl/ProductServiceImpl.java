package com.lxdmp.springtest.service.impl;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.entity.repository.ProductRepository;
import com.lxdmp.springtest.service.ProductService;
import com.lxdmp.springtest.utils.Paginator;
import com.lxdmp.springtest.dao.ProductDao;

@Transactional
@Service
public class ProductServiceImpl implements ProductService
{
	/*
	@Autowired
	//@Qualifier("hsqlProductRepo")
	@Qualifier("mysqlProductRepo")
	private ProductRepository productRepository;
	*/
	@Autowired
	private ProductDao productRepository;

	@Override
	public List<Product> getAllProducts()
	{
		return productRepository.getAllProducts();
	}

	@Override
	public int getAllProductsNum()
	{
		return productRepository.getAllProductsNum();
	}

	@Override
	public List<Product> getProductsByCategory(String category)
	{
		return productRepository.getProductsByCategory(category);
	}

	@Override
	public List<Product> getProductsByPage(Integer pageNo, Integer pageSize)
	{
		return productRepository.getProductsByPage(pageNo, pageSize);
	}

	@Override
	public Product getProductById(String productId)
	{
		return productRepository.getProductById(productId);
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

	@Override
	public void addProduct(Product product)
	{
		productRepository.addProduct(product);
	}
}

