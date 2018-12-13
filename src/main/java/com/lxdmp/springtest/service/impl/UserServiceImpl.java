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
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserRepository;
import com.lxdmp.springtest.domain.repository.UserAndGroupRepository;
import com.lxdmp.springtest.domain.repository.UserGroupRepository;
import com.lxdmp.springtest.service.UserService;
import org.apache.log4j.Logger;

@Transactional
@Service
public class UserServiceImpl implements UserService
{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	@Qualifier("mysqlUserRepo")
	private UserRepository userRepository;

	@Autowired
	@Qualifier("mysqlUserAndGroupRepo")
	private UserAndGroupRepository userAndGroupRepository;

	@Autowired
	@Qualifier("mysqlUserGroupRepo")
	private UserGroupRepository userGroupRepository;

	// 增加用户
	@Override
	public boolean addUser(UserDto userDto)
	{
		int duplicateUserId = userRepository.queryUserIdByName(userDto.getUserName());
		if(duplicateUserId>=0) // 已有同名的用户
			return false;
		userRepository.addUser(userDto);
		return true;
	}
	
	// 删除用户
	@Override
	public boolean delUser(String userName)
	{
		int existedUserId = userRepository.queryUserIdByName(userName);
		if(existedUserId<0) // 没有该用户
			return false;
		userRepository.delUser(existedUserId);
		return true;
	}

	// 修改用户密码
	@Override
	public boolean updateUserPassword(String userName, String oldPassword, String newPassword)
	{
		User existedUser = userRepository.queryUserByName(userName);
		if(existedUser==null) // 没有该用户
			return false;
		if(!existedUser.getUserPasswd().equals(oldPassword)) // 现有密码不匹配
			return false;
		userRepository.updateUserPassword(userName, newPassword);
		return true;
	}

	// 查询所有用户
	@Override
	public List<User> queryAllUsers()
	{
		return userRepository.queryAllUsers();
	}

	// 查询用户
	@Override
	public User queryUserByName(String userName)
	{
		User user = userRepository.queryUserByName(userName);
		if(user==null)
			return null;
		return user;
	}

	// 查询用户未加入的用户组
	@Override
	public List<UserGroup> queryUserNotJoinedGroups(String userName)
	{
		List<UserGroup> result = new LinkedList<UserGroup>();
		User user = userRepository.queryUserByName(userName);
		if(user!=null)
		{
			List<UserGroup> allGroups = userGroupRepository.queryAllUserGroups();
			Set<UserGroup> resultSet = new HashSet<UserGroup>();
			resultSet.addAll(allGroups);
			resultSet.removeAll(user.getUserGroups());
			result.addAll(resultSet);
		}
		return result;
	}

	// 用户加入某用户组
	@Override
	public boolean userJoinGroup(String userName, String userGroupName)
	{
		int userId = userRepository.queryUserIdByName(userName);
		if(userId<0) // 没有该用户
			return false;

		int userGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(userGroupId<0) // 没有该用户组
			return false;

		try{
			userAndGroupRepository.userJoinGroup(userId, userGroupId);
		}catch(DuplicateKeyException e){
			logger.warn(String.format("user %s already with group %s", userName, userGroupName));
			return false;
		}
		return true;
	}

	// 用户离开某用户组
	@Override
	public boolean userLeaveGroup(String userName, String userGroupName)
	{
		int userId = userRepository.queryUserIdByName(userName);
		if(userId<0) // 没有该用户
			return false;

		int userGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(userGroupId<0) // 没有该用户组
			return false;

		userAndGroupRepository.userLeaveGroup(userId, userGroupId);
		return true;
	}
}

