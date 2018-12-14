package com.lxdmp.springtest.domain.repository;

import com.lxdmp.springtest.domain.User;

public interface GroupAndPriviledgeRepository
{
	void addPriviledgeToGroup(Integer userGroupId, Integer priviledgeId); // 用户组给予某权限
	void delPriviledgeFromGroup(Integer userGroupId, Integer priviledgeId); // 用户组去除某权限
}

