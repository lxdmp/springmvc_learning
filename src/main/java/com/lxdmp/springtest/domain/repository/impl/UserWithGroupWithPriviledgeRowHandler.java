package com.lxdmp.springtest.domain.repository.impl;

import java.util.List;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowCallbackHandler;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;

public class UserWithGroupWithPriviledgeRowHandler implements RowCallbackHandler
{
	private List<User> users = new LinkedList<User>();

	public List<User> getUsers()
	{
		return this.users;
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException
	{
		int userId = rs.getInt("id1");
		String userName = rs.getString("name1");
		String userPasswd = rs.getString("password");
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
}

