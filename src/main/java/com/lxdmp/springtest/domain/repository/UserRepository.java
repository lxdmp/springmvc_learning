package com.lxdmp.springtest.domain.repository;

import java.util.List;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.dto.UserDto;

public interface UserRepository
{
	int addUser(UserDto user); // 增加用户
	void delUser(int userId); // 删除用户
	void updateUserPassword(String userName, String newPassword); // 修改用户密码
	List<User> queryAllUsers(); // 查询所有用户
	User queryUserByName(String userName); // 查询用户
	int queryUserIdByName(String userName); // 查询用户id
}

