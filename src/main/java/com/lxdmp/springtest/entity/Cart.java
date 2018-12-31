package com.lxdmp.springtest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Iterator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"handler"})
public class Cart implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private List<CartItem> cartItems;
	private BigDecimal grandTotal;

	public Cart(String id)
	{
		this.setId(id);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public BigDecimal getGrandTotal()
	{
		updateGrandTotal();
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal)
	{
		this.grandTotal = grandTotal;
	}

	public List<CartItem> getCartItems()
	{
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems)
	{
		this.cartItems = cartItems;
	}

	public CartItem getItemByProductId(String productId)
	{
		if(getCartItems()!=null) // 使用getter,mybatis的懒加载导致字段可能没有被载入.
		{
			Iterator<CartItem> iterator = getCartItems().iterator();
			while(iterator.hasNext())
			{
				CartItem cartItem = iterator.next();
				if(cartItem.getProduct().getProductId().equals(productId))
					return cartItem;
			}
		}
		return null;
	}

	public void updateGrandTotal()
	{
		BigDecimal sum = new BigDecimal(0.0);
		Iterator<CartItem> iterator = this.cartItems.iterator();
		while(iterator.hasNext())
		{
			CartItem cartItem = iterator.next();
			sum = sum.add(cartItem.getTotalPrice());
		}
		this.setGrandTotal(sum);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 :
				id.hashCode());
		return result;
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
		Cart other = (Cart) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		return true;
	}
}

