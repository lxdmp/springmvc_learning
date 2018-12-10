package com.lxdmp.springtest.service;

import java.util.Set;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;

public interface UserGroupService
{
	void addUserGroup(UserGroup userGroup); // 增加用户组
	void delUserGroup(String userGroupName); // 删除用户组
	void updateUserGroup(String userGroupName, String newUserGroupName); // 修改用户组名称
	UserGroup queryUserGroupByName(String userGroupName); // 查询用户组
	Set<User> usersInThisGroup(String userGroupName); // 该用户组中的用户
	void userGroupAddPriviledge(String userGroupName, String userPriviledgeName); // 用户组赋予某权限
	void userGroupDelPriviledge(String userGroupName, String userPriviledgeName); // 用户组取消某权限
}

