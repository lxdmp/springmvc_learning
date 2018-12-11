package com.lxdmp.springtest.service.impl;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserRepository;
import com.lxdmp.springtest.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	@Qualifier("mysqlUserRepo")
	private UserRepository userRepository;

	// 增加用户
	public boolean addUser(UserDto userDto)
	{
		User duplicateUser = userRepository.queryUserByName(userDto.getUserName());
		if(duplicateUser!=null) // 已有同名的用户
			return false;
		userRepository.addUser(userDto);
		return true;
	}
	
	// 删除用户
	public boolean delUser(String userName)
	{
		User existedUser = userRepository.queryUserByName(userName);
		if(existedUser==null) // 没有该用户
			return false;
		userRepository.delUser(existedUser.getUserId());
		return true;
	}

	// 修改用户密码
	public boolean updateUserPassword(String userName, String oldPassword, String newPassword)
	{
		User existedUser = userRepository.queryUserByName(userName);
		if(existedUser==null) // 没有该用户
			return false;
		if(!existedUser.getUserPasswd().equals(oldPassword)) // 现有密码不匹配
			return false;
		userRepository.updateUserPassword(userName, newPassword);
		return true;
	}

	// 查询用户
	public User queryUserByName(String userName)
	{
		return userRepository.queryUserByName(userName);
	}

	// 用户加入某用户组
	public void userJoinGroup(String userName, String userGroupName)
	{
	}

	// 用户离开某用户组
	public void userLeaveGroup(String userName, String userGroupName)
	{
	}
}

