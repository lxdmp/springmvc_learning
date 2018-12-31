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
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.entity.UserPriviledge;
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.dto.UserPriviledgeDto;
import com.lxdmp.springtest.service.UserService;
import com.lxdmp.springtest.service.UserGroupService;
import com.lxdmp.springtest.service.UserPriviledgeService;
import com.lxdmp.springtest.utils.result.ajax.ReadResult;
import com.lxdmp.springtest.utils.result.ajax.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api/priviledges")
public class PriviledgeRestController
{
	private static final Logger logger = LoggerFactory.getLogger(PriviledgeRestController.class);

	private final String success = "success";
	private final String failed = "failed";

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService groupService;

	@Autowired
	private UserPriviledgeService priviledgeService;

	// 创建用户
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public WriteResult create(@RequestBody UserDto userDto)
	{
		boolean userAdded = userService.addUser(userDto);
		if(userAdded)
			return new WriteResult(this.success);
		else
			return new WriteResult(this.failed);
	}

	// 查询用户
	@RequestMapping(value = "/user/{userName}", method=RequestMethod.GET)
	public ReadResult<User> read(@PathVariable(value = "userName") String userName)
	{
		User user = userService.queryUserByName(userName);
		if(user!=null)
			return new ReadResult<User>(this.success, user);
		else
			return new ReadResult<User>(
				this.failed, 
				String.format("No user named with %s", userName)
			);
	}

	// 更新用户信息
	/*
	@RequestMapping(value = "/user/{userName}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult update(@PathVariable(value = "userName") String cartId, @RequestBody UserDto userDto)
	{
		cartDto.setId(cartId);
		cartService.update(cartId, cartDto);
		return new WriteResult(this.success);
	}
	*/

	// 删除用户
	@RequestMapping(value = "/user/{userName}", method=RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public WriteResult delete(@PathVariable(value = "userName") String userName)
	{
		if(userService.delUser(userName))
			return new WriteResult(this.success);
		else
			return new WriteResult(this.failed);
	}

	/*
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
	*/
}

