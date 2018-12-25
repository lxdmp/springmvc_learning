package com.lxdmp.springtest.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import com.lxdmp.springtest.entity.Product;

@XmlRootElement(name="products")
public class ProductList implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<Product> productList;

	public ProductList(){}
	public ProductList(List<Product> productList){this.productList = productList;}

	@XmlElement(name="product")
	public List<Product> getProductList()
	{
		return this.productList;
	}

	public void setProductList(List<Product> productList)
	{
		this.productList = productList;
	}
}

