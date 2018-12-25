package com.lxdmp.springtest.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.entity.Cart;
import com.lxdmp.springtest.entity.CartItem;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.dto.CartItemDto;
import com.lxdmp.springtest.entity.repository.CartRepository;
import com.lxdmp.springtest.service.CartService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartServiceImpl implements CartService
{
	@Autowired
	//@Qualifier("hsqlCartRepo")
	@Qualifier("mysqlCartRepo")
	private CartRepository cartRepository;

	// 创建购物车
	@Override
	public void create(CartDto cartDto)
	{
		cartRepository.create(cartDto);
	}

	// 获取购物车
	@Override
	public Cart read(String cartId)
	{
		return cartRepository.read(cartId);
	}

	// 删除购物车
	@Override
	public void delete(String cartId)
	{
		cartRepository.delete(cartId);
	}

	// 更新购物车
	@Override
	public void update(String cartId, CartDto cartDto)
	{
		cartRepository.update(cartId, cartDto);
	}

	// 购物车中设置某种货物
	@Override
	public void updateItem(String cartId, String productId, int updatedNum)
	{
		cartRepository.updateItem(cartId, productId, updatedNum);
	}

	// 购物车中调整某种货物
	@Override
	public void modifyItem(String cartId, String productId, int modifiedNum)
	{
		cartRepository.modifyItem(cartId, productId, modifiedNum);
	}

	// 购物车中删除某种货物
	@Override
	public void removeItem(String cartId, String productId)
	{
		cartRepository.removeItem(cartId, productId);
	}
}

