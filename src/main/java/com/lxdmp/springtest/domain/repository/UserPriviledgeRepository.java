package com.lxdmp.springtest.domain.repository;

import java.util.List;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.dto.UserPriviledgeDto;

public interface UserPriviledgeRepository
{
	Integer addUserPriviledge(UserPriviledgeDto userPriviledgeDto); // 增加用户权限
	void delUserPriviledge(Integer userPriviledgeId); // 删除用户权限
	void updateUserPriviledge(Integer userPriviledgeId, String userPriviledgeName); // 修改用户权限名
	List<UserPriviledge> queryAllUserPriviledges(); // 查询所有用户权限
	UserPriviledge queryUserPriviledgeByName(String userPriviledgeName); // 查询用户权限
	Integer queryUserPriviledgeIdByName(String userPriviledgeName); // 查询用户权限id
	List<User> queryUsersByName(String userPriviledgeName); // 查询具有该权限的用户
	List<UserGroup> queryUserGroupsByName(String userPriviledgeName); // 查询具有该权限的用户组
}

