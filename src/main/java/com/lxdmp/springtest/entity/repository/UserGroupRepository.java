package com.lxdmp.springtest.entity.repository;

import java.util.List;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;

public interface UserGroupRepository
{
	void addUserGroup(UserGroup userGroup); // 增加用户组

	void delUserGroup(Integer userGroupId); // 删除用户组

	void updateUserGroup(Integer userGroupId, String userGroupName); // 修改用户组名

	List<UserGroup> queryAllUserGroups(); // 查询所有用户组
	UserGroup queryUserGroupByName(String userGroupName); // 查询用户组
	Integer queryUserGroupIdByName(String userGroupName); // 查询用户组id
	List<User> queryUsersByName(String userGroupName); // 查询加入用户组的用户
}

