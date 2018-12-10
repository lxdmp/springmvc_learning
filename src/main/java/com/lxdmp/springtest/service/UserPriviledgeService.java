package com.lxdmp.springtest.service;

import java.util.Set;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;

public interface UserPriviledgeService
{
	void addUserPriviledge(UserPriviledge userPriviledge); // 增加用户权限
	void delUserPriviledge(String userPriviledgeName); // 删除用户权限
	void updateUserPriviledge(String userPriviledgeName, String newUserPriviledgeName); // 修改用户权限名称
	UserPriviledge queryUserPriviledgeByName(String userPriviledgeName); // 查询用户权限
	Set<UserGroup> userGroupsWithPriviledge(String userPriviledgeName); // 具有该权限的用户组
	Set<User> usersWithPriviledge(String userPriviledgeName); // 具有该权限的用户
}

