package com.lxdmp.springtest.domain.repository;

import com.lxdmp.springtest.domain.User;

public interface GroupAndPriviledgeRepository
{
	void addPriviledgeToGroup(int userGroupId, int priviledgeId); // 用户组给予某权限
	void delPriviledgeFromGroup(int userGroupId, int priviledgeId); // 用户组去除某权限
}

