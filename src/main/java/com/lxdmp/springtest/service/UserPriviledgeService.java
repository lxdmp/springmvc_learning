package com.lxdmp.springtest.service;

import java.util.List;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.entity.UserPriviledge;
import com.lxdmp.springtest.dto.UserPriviledgeDto;

public interface UserPriviledgeService
{
	boolean addUserPriviledge(UserPriviledgeDto userPriviledgeDto); // 增加用户权限
	boolean delUserPriviledge(String userPriviledgeName); // 删除用户权限
	boolean updateUserPriviledge(String userPriviledgeName, String newUserPriviledgeName); // 修改用户权限名称
	List<UserPriviledge> queryAllUserPriviledges(); // 查询所有用户权限
	UserPriviledge queryUserPriviledgeByName(String userPriviledgeName); // 查询用户权限
	List<UserGroup> userGroupsWithPriviledge(String userPriviledgeName); // 具有该权限的用户组
	List<User> usersWithPriviledge(String userPriviledgeName); // 具有该权限的用户
}

