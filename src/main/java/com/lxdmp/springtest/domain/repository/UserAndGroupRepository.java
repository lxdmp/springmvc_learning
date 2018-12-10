package com.lxdmp.springtest.domain.repository;

import com.lxdmp.springtest.domain.User;

public interface UserAndGroupRepository
{
	void userJoinGroup(String userName, String userGroupName); // 用户加入某用户组
	void userLeaveGroup(String userName, String userGroupName); // 用户离开某用户组
}

