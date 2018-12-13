package com.lxdmp.springtest.service.impl;

import java.util.List;
import java.util.LinkedList;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.dto.UserPriviledgeDto;
import com.lxdmp.springtest.domain.repository.UserRepository;
import com.lxdmp.springtest.domain.repository.UserGroupRepository;
import com.lxdmp.springtest.domain.repository.GroupAndPriviledgeRepository;
import com.lxdmp.springtest.domain.repository.UserPriviledgeRepository;
import com.lxdmp.springtest.service.UserPriviledgeService;
import com.lxdmp.springtest.service.UserGroupService;

@Transactional
@Service
public class UserPriviledgeServiceImpl implements UserPriviledgeService
{
	@Autowired
	@Qualifier("mysqlUserPriviledgeRepo")
	private UserPriviledgeRepository userPriviledgeRepository;

	@Autowired
	@Qualifier("mysqlGroupAndPriviledgeRepo")
	private GroupAndPriviledgeRepository groupAndPriviledgeRepository;

	@Autowired
	@Qualifier("mysqlUserGroupRepo")
	private UserGroupRepository userGroupRepository;

	@Autowired
	@Qualifier("mysqlUserRepo")
	private UserRepository userRepository;
	
	// 增加用户权限
	public boolean addUserPriviledge(UserPriviledgeDto userPriviledgeDto)
	{	
		int duplicateUserPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(
			userPriviledgeDto.getPriviledgeName()
		);
		if(duplicateUserPriviledgeId>=0) // 已有同名的用户权限
			return false;
		int newPriviledgeId = userPriviledgeRepository.addUserPriviledge(userPriviledgeDto);
		return true;
	}

	// 删除用户权限
	public boolean delUserPriviledge(String userPriviledgeName)
	{
		int existedUserPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(existedUserPriviledgeId<0) // 没有该用户权限
			return false;
		userPriviledgeRepository.delUserPriviledge(existedUserPriviledgeId);
		return true;
	}

	// 修改用户权限名称
	public boolean updateUserPriviledge(String userPriviledgeName, String newUserPriviledgeName)
	{
		int existedUserPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(existedUserPriviledgeId<0) // 没有该用户权限
			return false;
		userPriviledgeRepository.updateUserPriviledge(existedUserPriviledgeId, newUserPriviledgeName);
		return true;
	}

	// 查询用户权限
	public UserPriviledge queryUserPriviledgeByName(String userPriviledgeName)
	{
		UserPriviledge userPriviledge = userPriviledgeRepository.queryUserPriviledgeByName(userPriviledgeName);
		if(userPriviledge==null)
			return null;
		return userPriviledge;
	}

	// 具有该权限的用户组
	public List<UserGroup> userGroupsWithPriviledge(String userPriviledgeName)
	{
		List<UserGroup> result = new LinkedList<UserGroup>();
		List<UserGroup> userGroups = userPriviledgeRepository.queryUserGroupsByName(userPriviledgeName);
		for(UserGroup userGroup : userGroups)
		{
			UserGroup groupInDetail = userGroupRepository.queryUserGroupByName(userGroup.getGroupName());
			if(groupInDetail!=null)
				result.add(groupInDetail);
		}
		return result;
	}

	// 具有该权限的用户
	public List<User> usersWithPriviledge(String userPriviledgeName)
	{
		List<User> result = new LinkedList<User>();
		List<User> users = userPriviledgeRepository.queryUsersByName(userPriviledgeName);
		for(User user : users)
		{
			User userInDetail = userRepository.queryUserByName(user.getUserName());
			if(userInDetail!=null)
				result.add(userInDetail);
		}
		return result;
	}
}

