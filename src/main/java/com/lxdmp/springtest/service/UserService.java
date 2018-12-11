package com.lxdmp.springtest.service;

import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.dto.UserDto;

public interface UserService
{
	boolean addUser(UserDto userDto); // 增加用户
	boolean delUser(String userName); // 删除用户
	boolean updateUserPassword(String userName, String oldPassword, String newPassword); // 修改用户密码
	User queryUserByName(String userName); // 查询用户
	void userJoinGroup(String userName, String userGroupName); // 用户加入某用户组
	void userLeaveGroup(String userName, String userGroupName); // 用户离开某用户组
}

