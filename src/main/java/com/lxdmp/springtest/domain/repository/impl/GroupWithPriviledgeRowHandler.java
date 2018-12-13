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
	// 联立用户组/权限两张表查询,基于用户组返回结果.
	private final String SQL = "select UserGroup.id as id1," + 
			"UserGroup.name as name1," + 
			"UserPriviledge.id as id2," + 
			"UserPriviledge.name as name2" + 
			" from UserGroup " + 
			"left join GroupWithPriviledge on UserGroup.id=GroupWithPriviledge.groupId " + 
			"left join UserPriviledge on GroupWithPriviledge.priviledgeId=UserPriviledge.id " + 
			"where %s " + 
			"order by id1 asc, id2 asc";
	private List<UserGroup> userGroups = new LinkedList<UserGroup>();

	public List<UserGroup> getUserGroups()
	{
		return this.userGroups;
	}

	public String queryAll()
	{
		String condition = "True";
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
		int userGroupId = rs.getInt("id1");
		String userGroupName = rs.getString("name1");
		if(!checkValidity(userGroupId, userGroupName))
			return;
		if( userGroups.isEmpty() || 
			userGroups.get(userGroups.size()-1).getGroupId()!=userGroupId)
		{
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupId(userGroupId);
			userGroup.setGroupName(userGroupName);

			userGroups.add(userGroup);
		}
		UserGroup userGroup = userGroups.get(userGroups.size()-1);

		int priviledgeId = rs.getInt("id2");
		String priviledgeName = rs.getString("name2");
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

