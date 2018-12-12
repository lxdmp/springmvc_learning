package com.lxdmp.springtest.domain.repository;

import java.util.List;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.dto.UserGroupDto;

public interface UserGroupRepository
{
	int addUserGroup(UserGroupDto userGroupDto); // 增加用户组
	void delUserGroup(int userGroupId); // 删除用户组
	void updateUserGroup(int userGroupId, String userGroupName); // 修改用户组名
	UserGroup queryUserGroupByName(String userGroupName); // 查询用户组
	int queryUserGroupIdByName(String userGroupName); // 查询用户组id
	List<User> queryUsersByName(String userGroupName); // 查询加入用户组的用户
}

