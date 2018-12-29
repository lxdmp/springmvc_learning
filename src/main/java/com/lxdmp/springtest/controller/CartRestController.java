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
import com.lxdmp.springtest.entity.Cart;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.service.CartService;
import com.lxdmp.springtest.utils.result.ajax.ReadResult;
import com.lxdmp.springtest.utils.result.ajax.WriteResult;
import org.apache.log4j.Logger;

@RestController
@RequestMapping(value = "/api/cart")
public class CartRestController
{
	private static final Logger logger = Logger.getLogger(CartRestController.class);

	private final String success = "success";
	private final String failed = "failed";

	@Autowired
	private CartService cartService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public WriteResult create(@RequestBody CartDto cartDto, HttpSession session)
	{
		cartDto.setId(session.getId());
		cartService.create(cartDto);
		return new WriteResult(this.success);
	}

	@RequestMapping(value = "/{cartId}", method=RequestMethod.GET)
	public ReadResult<Cart> read(@PathVariable(value = "cartId") String cartId)
	{
		Cart cart = cartService.read(cartId);
		if(cart!=null)
			return new ReadResult<Cart>(this.success, cart);
		else
			return new ReadResult<Cart>(
				this.failed, 
				String.format("No cart with id %s", cartId)
			);
	}

	@RequestMapping(value = "/{cartId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult update(@PathVariable(value = "cartId") String cartId, @RequestBody CartDto cartDto)
	{
		cartDto.setId(cartId);
		cartService.update(cartDto);
		return new WriteResult(this.success);
	}

	@RequestMapping(value = "/{cartId}", method=RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult delete(@PathVariable(value = "cartId") String cartId)
	{
		cartService.delete(cartId);
		return new WriteResult(this.success);
	}

	@RequestMapping(value = "/add/{productId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult addItem(@PathVariable String productId, HttpSession session)
	{
		cartService.modifyItem(session.getId(), productId, 1);
		return new WriteResult(this.success);
	}
	
	@RequestMapping(value = "/sub/{productId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult subItem(@PathVariable String productId, HttpSession session)
	{
		cartService.modifyItem(session.getId(), productId, -1);
		return new WriteResult(this.success);
	}

	@RequestMapping(value = "/remove/{productId}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult removeItem(@PathVariable String productId, HttpSession session)
	{
		cartService.removeItem(session.getId(), productId);
		return new WriteResult(this.success);
	}
}

