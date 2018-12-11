package com.lxdmp.springtest.service;

import java.util.List;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.dto.UserGroupDto;

public interface UserGroupService
{
	boolean addUserGroup(UserGroupDto userGroupDto); // 增加用户组
	boolean delUserGroup(String userGroupName); // 删除用户组
	boolean updateUserGroup(String userGroupName, String newUserGroupName); // 修改用户组名称
	UserGroup queryUserGroupByName(String userGroupName); // 查询用户组
	List<User> usersInThisGroup(String userGroupName); // 该用户组中的用户
	boolean userGroupAddPriviledge(String userGroupName, String userPriviledgeName); // 用户组赋予某权限
	boolean userGroupDelPriviledge(String userGroupName, String userPriviledgeName); // 用户组取消某权限
}

