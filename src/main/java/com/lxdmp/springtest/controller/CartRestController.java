package com.lxdmp.springtest.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.lxdmp.springtest.domain.Cart;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.service.CartService;

@RestController
@RequestMapping(value = "/api/cart")
public class CartRestController
{
	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/id", method=RequestMethod.GET)
	public String myCartId(HttpSession session)
	{
		return session.getId();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody CartDto cartDto, HttpSession session)
	{
		cartDto.setId(session.getId());
		cartService.create(cartDto);
	}

	@RequestMapping(value = "/{cartId}", method=RequestMethod.GET)
	public Cart read(@PathVariable(value = "cartId") String cartId)
	{
		return cartService.read(cartId);
	}

	@RequestMapping(value = "/{cartId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable(value = "cartId") String cartId, @RequestBody CartDto cartDto)
	{
		cartDto.setId(cartId);
		cartService.update(cartId, cartDto);
	}

	@RequestMapping(value = "/{cartId}", method=RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable(value = "cartId") String cartId)
	{
		cartService.delete(cartId);
	}

	@RequestMapping(value = "/add/{productId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void addItem(@PathVariable String productId, HttpSession session)
	{
		cartService.modifyItem(session.getId(), productId, 1);
	}
	
	@RequestMapping(value = "/sub/{productId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void subItem(@PathVariable String productId, HttpSession session)
	{
		cartService.modifyItem(session.getId(), productId, -1);
	}

	@RequestMapping(value = "/remove/{productId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeItem(@PathVariable String productId, HttpSession session)
	{
		cartService.removeItem(session.getId(), productId);
	}
}

