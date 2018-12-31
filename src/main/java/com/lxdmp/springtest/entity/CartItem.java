package com.lxdmp.springtest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"handler"})
public class CartItem implements Serializable
{
	private static final long serialVersionUID = 2L;
	private Integer id;
	private Product product;
	private Integer quantity;
	private BigDecimal totalPrice;
	
	public CartItem()
	{
	}

	public CartItem(Integer id)
	{
		super();
		this.setId(id);
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
		this.updateTotalPrice();
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public BigDecimal getTotalPrice()
	{
		this.updateTotalPrice();
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal price)
	{
		this.totalPrice = price;
	}

	public void updateTotalPrice()
	{
		this.setTotalPrice(this.product.getUnitPrice().multiply(new BigDecimal(this.quantity)));
	}

	@Override
	public int hashCode()
	{
		return id%1024;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return id==other.id;
	}
}

