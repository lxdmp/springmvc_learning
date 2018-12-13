package com.lxdmp.springtest.domain.repository.impl;

import java.util.List;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowCallbackHandler;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import org.apache.log4j.Logger;

public class UserWithGroupWithPriviledgeRowHandler implements RowCallbackHandler
{
	private static final Logger logger = Logger.getLogger(UserWithGroupWithPriviledgeRowHandler.class);

	// 联立用户/用户组/权限三张表查询,基于用户返回结果.
	final private String SQL = "select User.id as id1," + 
			"User.name as name1," + 
			"User.password as password," + 
			"UserGroup.id as id2," + 
			"UserGroup.name as name2," + 
			"UserPriviledge.id as id3," + 
			"UserPriviledge.name as name3" + 
			" from User " + 
			"left join UserWithGroup on User.id=UserWithGroup.userId " + 
			"left join UserGroup on UserWithGroup.groupId=UserGroup.id " + 
			"left join GroupWithPriviledge on UserGroup.id=GroupWithPriviledge.groupId " + 
			"left join UserPriviledge on GroupWithPriviledge.priviledgeId=UserPriviledge.id " + 
			"where %s " + 
			"order by id1 asc, id2 asc, id3 asc";
	private List<User> users = new LinkedList<User>();

	public List<User> getUsers()
	{
		return this.users;
	}

	public String queryAll()
	{
		String condition = "True";
		return String.format(this.SQL, condition);
	}

	public String queryWithUserName(String userName)
	{
		String condition = String.format("User.name = :%s", userName);
		return String.format(this.SQL, condition);
	}

	public String queryWithGroupName(String userGroupName)
	{
		String condition = String.format("UserGroup.name = :%s", userGroupName);
		return String.format(this.SQL, condition);
	}

	public String queryWithPriviledgeName(String userPriviledgeName)
	{
		String condition = String.format("UserPriviledge.name = :%s", userPriviledgeName);
		return String.format(this.SQL, condition);
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException
	{
		int userId = rs.getInt("id1");
		String userName = rs.getString("name1");
		String userPasswd = rs.getString("password");
		if(!checkValidity(userId, userName))
			return;
		if(users.isEmpty() || users.get(users.size()-1).getUserId()!=userId)
		{
			User user = new User();
			user.setUserId(userId);
			user.setUserName(userName);
			user.setUserPasswd(userPasswd);
			users.add(user);
		}
		User user = users.get(users.size()-1);

		int userGroupId = rs.getInt("id2");
		String userGroupName = rs.getString("name2");
		if(!checkValidity(userGroupId, userGroupName))
			return;
		if( user.getUserGroups().isEmpty() || 
			user.getUserGroups().get(user.getUserGroups().size()-1).getGroupId()!=userGroupId)
		{
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupId(userGroupId);
			userGroup.setGroupName(userGroupName);

			List<UserGroup> userGroups = user.getUserGroups();
			userGroups.add(userGroup);
			user.setUserGroups(userGroups);
		}
		UserGroup userGroup = user.getUserGroups().get(user.getUserGroups().size()-1);

		int priviledgeId = rs.getInt("id3");
		String priviledgeName = rs.getString("name3");
		if(!checkValidity(priviledgeId, priviledgeName))
			return;
		if( userGroup.getGroupPriviledges().isEmpty() || 
			userGroup.getGroupPriviledges().get(userGroup.getGroupPriviledges().size()-1).getPriviledgeId()!=priviledgeId)
		{
			UserPriviledge userPriviledge = new UserPriviledge();
			userPriviledge.setPriviledgeId(priviledgeId);
			userPriviledge.setPriviledgeName(priviledgeName);

			List<UserPriviledge> groupPriviledges = userGroup.getGroupPriviledges();
			groupPriviledges.add(userPriviledge);
			userGroup.setGroupPriviledges(groupPriviledges);
		}
	}

	private boolean checkValidity(int id, String name)
	{
		// 确认SQL字段不为NULL.
		return (id>0 && name!=null);
	}
}

