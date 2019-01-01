package com.lxdmp.springtest.service.impl;

import java.util.List;
import java.util.LinkedList;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.entity.UserPriviledge;
import com.lxdmp.springtest.dto.UserPriviledgeDto;
//import com.lxdmp.springtest.entity.repository.UserRepository;
//import com.lxdmp.springtest.entity.repository.UserGroupRepository;
//import com.lxdmp.springtest.entity.repository.GroupAndPriviledgeRepository;
//import com.lxdmp.springtest.entity.repository.UserPriviledgeRepository;
import com.lxdmp.springtest.dao.UserDao;
import com.lxdmp.springtest.dao.UserGroupDao;
import com.lxdmp.springtest.dao.GroupAndPriviledgeDao;
import com.lxdmp.springtest.dao.UserPriviledgeDao;
import com.lxdmp.springtest.service.UserPriviledgeService;
import com.lxdmp.springtest.service.UserGroupService;

@Transactional
@Service
public class UserPriviledgeServiceImpl implements UserPriviledgeService
{
	/*
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
	*/

	@Autowired
	private UserPriviledgeDao userPriviledgeRepository;

	@Autowired
	private GroupAndPriviledgeDao groupAndPriviledgeRepository;

	@Autowired
	private UserGroupDao userGroupRepository;

	@Autowired
	private UserDao userRepository;

	// 增加用户权限
	@Override
	public boolean addUserPriviledge(UserPriviledgeDto userPriviledgeDto)
	{	
		Integer duplicateUserPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(
			userPriviledgeDto.getPriviledgeName()
		);
		if(duplicateUserPriviledgeId>=0) // 已有同名的用户权限
			return false;
		Integer newPriviledgeId = userPriviledgeRepository.addUserPriviledge(userPriviledgeDto);
		return true;
	}

	// 删除用户权限
	@Override
	public boolean delUserPriviledge(String userPriviledgeName)
	{
		Integer existedUserPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(existedUserPriviledgeId<0) // 没有该用户权限
			return false;
		userPriviledgeRepository.delUserPriviledge(existedUserPriviledgeId);
		return true;
	}

	// 修改用户权限名称
	@Override
	public boolean updateUserPriviledge(String userPriviledgeName, String newUserPriviledgeName)
	{
		Integer existedUserPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(existedUserPriviledgeId<0) // 没有该用户权限
			return false;
		userPriviledgeRepository.updateUserPriviledge(existedUserPriviledgeId, newUserPriviledgeName);
		return true;
	}

	// 查询所有用户权限
	@Override
	public List<UserPriviledge> queryAllUserPriviledges()
	{
		return userPriviledgeRepository.queryAllUserPriviledges();
	}

	// 查询用户权限
	@Override
	public UserPriviledge queryUserPriviledgeByName(String userPriviledgeName)
	{
		UserPriviledge userPriviledge = userPriviledgeRepository.queryUserPriviledgeByName(userPriviledgeName);
		if(userPriviledge==null)
			return null;
		return userPriviledge;
	}

	// 具有该权限的用户组
	@Override
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
	@Override
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

