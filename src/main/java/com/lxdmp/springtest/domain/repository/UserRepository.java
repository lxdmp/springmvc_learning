package com.lxdmp.springtest.domain.repository;

import com.lxdmp.springtest.domain.User;

public interface UserRepository
{
	void addUser(User user); // 增加用户
	void delUser(String userName); // 删除用户
	boolean updateUserPassword(String userName, String oldPassword, String newPassword); // 修改用户密码
	User queryUserByName(String userName); // 查询用户
}

