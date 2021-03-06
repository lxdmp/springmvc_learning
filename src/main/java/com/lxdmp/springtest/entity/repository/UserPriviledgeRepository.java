package com.lxdmp.springtest.entity.repository;

import java.util.List;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.entity.UserPriviledge;

public interface UserPriviledgeRepository
{
	void addUserPriviledge(UserPriviledge userPriviledge); // 增加用户权限

	void delUserPriviledge(Integer userPriviledgeId); // 删除用户权限

	void updateUserPriviledge(Integer userPriviledgeId, String userPriviledgeName); // 修改用户权限名

	List<UserPriviledge> queryAllUserPriviledges(); // 查询所有用户权限
	UserPriviledge queryUserPriviledgeByName(String userPriviledgeName); // 查询用户权限
	Integer queryUserPriviledgeIdByName(String userPriviledgeName); // 查询用户权限id
	List<User> queryUsersByName(String userPriviledgeName); // 查询具有该权限的用户
	List<UserGroup> queryUserGroupsByName(String userPriviledgeName); // 查询具有该权限的用户组
}

