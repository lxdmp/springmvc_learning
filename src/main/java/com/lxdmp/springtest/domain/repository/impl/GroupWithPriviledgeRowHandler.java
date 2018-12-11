package com.lxdmp.springtest.domain.repository.impl;

import java.util.List;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowCallbackHandler;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;

public class GroupWithPriviledgeRowHandler implements RowCallbackHandler
{
	private List<UserGroup> userGroups = new LinkedList<UserGroup>();

	public List<UserGroup> getUserGroups()
	{
		return this.userGroups;
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException
	{
		int userGroupId = rs.getInt("id1");
		String userGroupName = rs.getString("name1");
		if( userGroups().isEmpty() || 
			userGroups().get(userGroups().size()-1).getGroupId()!=userGroupId)
		{
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupId(userGroupId);
			userGroup.setGroupName(userGroupName);

			userGroups.add(userGroup);
		}
		UserGroup userGroup = userGroups().get(userGroups().size()-1);

		int priviledgeId = rs.getInt("id2");
		String priviledgeName = rs.getString("name2");
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

