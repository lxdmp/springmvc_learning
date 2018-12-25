package com.lxdmp.springtest.service.impl;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.entity.UserPriviledge;
import com.lxdmp.springtest.entity.repository.UserRepository;
import com.lxdmp.springtest.entity.repository.UserGroupRepository;
import com.lxdmp.springtest.entity.repository.GroupAndPriviledgeRepository;
import com.lxdmp.springtest.entity.repository.UserPriviledgeRepository;
import com.lxdmp.springtest.service.UserGroupService;
import org.apache.log4j.Logger;

@Transactional
@Service
public class UserGroupServiceImpl implements UserGroupService
{
	private static final Logger logger = Logger.getLogger(UserGroupServiceImpl.class);
	
	@Autowired
	@Qualifier("mysqlUserRepo")
	private UserRepository userRepository;

	@Autowired
	@Qualifier("mysqlUserGroupRepo")
	private UserGroupRepository userGroupRepository;

	@Autowired
	@Qualifier("mysqlGroupAndPriviledgeRepo")
	private GroupAndPriviledgeRepository groupAndPriviledgeRepository;

	@Autowired
	@Qualifier("mysqlUserPriviledgeRepo")
	private UserPriviledgeRepository userPriviledgeRepository;

	// 增加用户组
	@Override
	public boolean addUserGroup(UserGroupDto userGroupDto)
	{
		Integer duplicateUserGroupId = userGroupRepository.queryUserGroupIdByName(userGroupDto.getGroupName());
		if(duplicateUserGroupId>=0) // 已有同名的用户组
			return false;
		Integer newGroupId = userGroupRepository.addUserGroup(userGroupDto);
		return true;
	}

	// 删除用户组
	@Override
	public boolean delUserGroup(String userGroupName)
	{
		Integer existedUserGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(existedUserGroupId<0) // 没有该用户组
			return false;
		userGroupRepository.delUserGroup(existedUserGroupId);
		return true;
	}

	// 修改用户组名称
	@Override
	public boolean updateUserGroup(String userGroupName, String newUserGroupName)
	{
		Integer existedUserGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(existedUserGroupId<0) // 没有该用户组
			return false;
		userGroupRepository.updateUserGroup(existedUserGroupId, newUserGroupName);
		return true;
	}

	// 查询所有用户组
	@Override
	public List<UserGroup> queryAllUserGroups()
	{
		return userGroupRepository.queryAllUserGroups();
	}

	// 查询用户组
	@Override
	public UserGroup queryUserGroupByName(String userGroupName)
	{
		UserGroup userGroup = userGroupRepository.queryUserGroupByName(userGroupName);
		if(userGroup==null)
			return null;
		return userGroup;
	}

	// 查询用户组未被赋予的权限
	@Override
	public List<UserPriviledge> queryGroupNotAddedPriviledges(String userGroupName)
	{
		List<UserPriviledge> result = new LinkedList<UserPriviledge>();
		UserGroup userGroup = userGroupRepository.queryUserGroupByName(userGroupName);
		if(userGroup!=null)
		{
			List<UserPriviledge> allPriviledges = userPriviledgeRepository.queryAllUserPriviledges();
			Set<UserPriviledge> resultSet = new HashSet<UserPriviledge>();
			resultSet.addAll(allPriviledges);
			resultSet.removeAll(userGroup.getGroupPriviledges());
			result.addAll(resultSet);
		}
		return result;
	}

	// 该用户组中的用户
	@Override
	public List<User> usersInThisGroup(String userGroupName)
	{
		List<User> result = new LinkedList<User>();
		List<User> users = userGroupRepository.queryUsersByName(userGroupName);
		for(User user : users)
		{
			User userInDetail = userRepository.queryUserByName(user.getUserName());
			if(userInDetail!=null)
				result.add(userInDetail);
		}
		return result;
	}

	// 用户组赋予某权限
	@Override
	public boolean userGroupAddPriviledge(String userGroupName, String userPriviledgeName)
	{
		Integer userGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(userGroupId<0) // 没有该用户组
			return false;

		Integer userPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(userPriviledgeId<0) // 没有该用户权限
			return false;

		try{
			groupAndPriviledgeRepository.addPriviledgeToGroup(userGroupId, userPriviledgeId);
		}catch(DuplicateKeyException e){
			logger.warn(String.format("group %s already with priviledge %s", 
				userGroupName, userPriviledgeName
			));
			return false;
		}
		return true;
	}

	// 用户组取消某权限
	@Override
	public boolean userGroupDelPriviledge(String userGroupName, String userPriviledgeName)
	{
		Integer userGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(userGroupId<0) // 没有该用户组
			return false;

		Integer userPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(userPriviledgeId<0) // 没有该用户权限
			return false;

		groupAndPriviledgeRepository.delPriviledgeFromGroup(userGroupId, userPriviledgeId);
		return true;	
	}
}

