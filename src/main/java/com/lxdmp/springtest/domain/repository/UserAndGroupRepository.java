package com.lxdmp.springtest.domain.repository;

import com.lxdmp.springtest.domain.User;

public interface UserAndGroupRepository
{
	void userJoinGroup(Integer userId, Integer userGroupId); // 用户加入某用户组
	void userLeaveGroup(Integer userId, Integer userGroupId); // 用户离开某用户组
}

