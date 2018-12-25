package com.lxdmp.springtest.entity.repository;

import java.util.List;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.dto.UserDto;

public interface UserRepository
{
	Integer addUser(UserDto user); // 增加用户
	void delUser(Integer userId); // 删除用户
	void updateUserPassword(String userName, String newPassword); // 修改用户密码
	List<User> queryAllUsers(); // 查询所有用户
	User queryUserByName(String userName); // 查询用户
	Integer queryUserIdByName(String userName); // 查询用户id
}

