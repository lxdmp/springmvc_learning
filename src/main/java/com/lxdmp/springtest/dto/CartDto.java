package com.lxdmp.springtest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartDto implements Serializable
{
	/*
	 * 需指定Cart的id与其中各项货物的货物id与数量.
	 * 其中,前台提交中显示指定的只是货物的货物id与数量,Cart的id设定为session的id.
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private List<CartItemDto> cartItems;

	public CartDto() {}

	public CartDto(String id)
	{
		this.id = id;
		cartItems = new ArrayList<>();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<CartItemDto> getCartItems()
	{
		return cartItems;
	}

	public void setCartItems(List<CartItemDto> cartItems)
	{
		this.cartItems = cartItems;
	}

	public void addCartItem(CartItemDto cartItemDto)
	{
		this.cartItems.add(cartItemDto);
	}
}

