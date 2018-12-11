package com.lxdmp.springtest.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserGroupRepository;

@Repository("mysqlUserGroupRepo")
public class MysqlUserGroupRepository extends BaseRepository implements UserGroupRepository
{
	// 增加用户组
	@Override
	public void addUserGroup(UserGroupDto userGroupDto)
	{
		String SQL = "insert into UserGroup (" + 
			"name" +
			") values (" + 
			":name)";
		Map<String, Object> params = new HashMap<>();
		params.put("name", userGroupDto.getGroupName());
		jdbcTemplate.update(SQL, params);
	}

	// 删除用户组
	@Override
	public void delUserGroup(int userGroupId)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", userGroupId);

		String SQL_DELETE_USER_WITH_GROUP = "delete from UserWithGroup where groupId = :groupId";
		jdbcTemplate.update(SQL_DELETE_USER_WITH_GROUP, params);

		String SQL_DELETE_USER = "delete from UserGroup where id = :groupId";
		jdbcTemplate.update(SQL_DELETE_USER, params);
	}

	// 查询用户组
	@Override
	public UserGroup queryUserGroupByName(String userGroupName)
	{
		List<UserGroup> userGroups = new LinkedList<UserGroup>();
		final String SQL = "select UserGroup.id as id1," + 
			"UserGroup.name as name1," + 
			"UserPriviledge.id as id2," + 
			"UserPriviledge.name as name2" + 
			" from UserGroup " + 
			"inner join GroupWithPriviledge on UserGroup.id=GroupWithPriviledge.groupId " + 
			"inner join UserPriviledge on GroupWithPriviledge.priviledgeId=UserPriviledge.id " + 
			"on UserGroup.name = :groupName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupName", userGroupName);
		jdbcTemplate.query(SQL, params, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException
			{
				
			}
		});
		return (userGroups.isEmpty()?null:userGroups.get(0));
	}

	// 查询加入用户组的用户
	@Override
	public List<User> queryUsersByName(String userGroupName)
	{
	}
}

