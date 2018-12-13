package com.lxdmp.springtest.service;

import java.util.List;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.dto.UserDto;

public interface UserService
{
	boolean addUser(UserDto userDto); // 增加用户
	boolean delUser(String userName); // 删除用户
	boolean updateUserPassword(String userName, String oldPassword, String newPassword); // 修改用户密码
	List<User> queryAllUsers(); // 查询所有用户
	User queryUserByName(String userName); // 查询用户
	boolean userJoinGroup(String userName, String userGroupName); // 用户加入某用户组
	boolean userLeaveGroup(String userName, String userGroupName); // 用户离开某用户组
}

