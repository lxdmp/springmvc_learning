package com.lxdmp.springtest.service;

import com.lxdmp.springtest.domain.Cart;
import com.lxdmp.springtest.domain.CartItem;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.dto.CartItemDto;

public interface CartService
{
	void create(CartDto cartDto); // 创建购物车
	Cart read(String cartId); // 获取购物车
	void delete(String cartId); // 删除购物车

	void update(String cartId, CartDto cartDto); // 更新购物车
	void updateItem(String cartId, String productId, int updatedNum); // 购物车中设置某种货物
	void modifyItem(String cartId, String productId, int modifiedNum); // 购物车中调整某种货物
	void removeItem(String cartId, String productId); // 购物车中删除某种货物
}

