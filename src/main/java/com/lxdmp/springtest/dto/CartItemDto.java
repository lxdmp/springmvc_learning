package com.lxdmp.springtest.dto;

import java.io.Serializable;

public class CartItemDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String productId;
	private String cartId;
	private int quantity;

	public String getProductId(){return productId;}
	public void setProductId(String productId){this.productId = productId;}

	public String getCartId(){return cartId;}
	public void setCartId(String cartId){this.cartId = cartId;}

	public int getQuantity(){return quantity;}
	public void setQuantity(int quantity){this.quantity = quantity;}
}

