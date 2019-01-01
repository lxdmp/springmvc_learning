package com.lxdmp.springtest.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.entity.Cart;
import com.lxdmp.springtest.entity.CartItem;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.dto.CartItemDto;
import com.lxdmp.springtest.service.CartService;
import org.springframework.transaction.annotation.Transactional;
import com.lxdmp.springtest.entity.repository.CartRepository;
import com.lxdmp.springtest.dao.CartDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@Service
public class CartServiceImpl implements CartService
{
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	/*
	@Autowired
	//@Qualifier("hsqlCartRepo")
	@Qualifier("mysqlCartRepo")
	private CartRepository cartRepository;
	*/

	@Autowired
	private CartDao cartRepository;

	// 创建购物车
	@Override
	public void create(CartDto cartDto)
	{
		cartRepository.create(cartDto);
		for(CartItemDto cartItemDto : cartDto.getCartItems())
		{
			this.updateItem(cartDto.getId(), cartItemDto.getProductId(), cartItemDto.getQuantity());
		}
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
		cartRepository.deleteItems(cartId);
		cartRepository.delete(cartId);
	}

	// 更新购物车
	@Override
	public void update(CartDto cartDto)
	{
		List<CartItemDto> cartItems = cartDto.getCartItems();
		for(CartItemDto cartItemDto : cartItems)
			this.updateItem(cartDto.getId(), cartItemDto.getProductId(), cartItemDto.getQuantity());
	}

	// 购物车中加入某种货物
	@Override
	public void updateItem(String cartId, String productId, int updatedNum)
	{
		Cart cart = cartRepository.read(cartId);
		if(cart==null)
		{
			CartDto cartDto = new CartDto(cartId);
			cartRepository.create(cartDto);
			cartRepository.updateItem(cartId, productId, updatedNum);
			return;
		}

		CartItem cartItem = cart.getItemByProductId(productId);
		if(cartItem==null)
		{
			cartRepository.updateItem(cartId, productId, updatedNum);
		}else{
			if(cartItem.getQuantity()!=updatedNum)
			{
				cartRepository.modifyItem(cartId, productId, updatedNum);
			}
		}
	}

	// 购物车中调整某种货物
	@Override
	public void modifyItem(String cartId, String productId, int modifiedNum)
	{
		Cart cart = cartRepository.read(cartId);
		if(cart==null)
		{
			if(modifiedNum>0)
			{
				CartDto cartDto = new CartDto(cartId);
				cartRepository.create(cartDto);
				cartRepository.updateItem(cartId, productId, modifiedNum);
			}
			return;
		}

		CartItem cartItem = cart.getItemByProductId(productId);
		if(cartItem==null)
		{
			if(modifiedNum>0)
			{
				cartRepository.updateItem(cartId, productId, modifiedNum);
			}
		}else{
			if(modifiedNum!=0)
			{
				if(cartItem.getQuantity()+modifiedNum>0){
					cartRepository.modifyItem(cartId, productId, cartItem.getQuantity()+modifiedNum);
				}else{
					cartRepository.deleteItem(cartId, productId);
				}
			}
		}
	}

	// 购物车中删除某种货物
	@Override
	public void removeItem(String cartId, String productId)
	{
		cartRepository.removeItem(cartId, productId);
	}
}

