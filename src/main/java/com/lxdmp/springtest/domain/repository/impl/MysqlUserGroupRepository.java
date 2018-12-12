package com.lxdmp.springtest.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserGroupRepository;
import com.lxdmp.springtest.domain.repository.impl.GroupWithPriviledgeRowHandler;

@Repository("mysqlUserGroupRepo")
public class MysqlUserGroupRepository extends BaseRepository implements UserGroupRepository
{
	// 增加用户组
	@Override
	public int addUserGroup(UserGroupDto userGroupDto)
	{
		String SQL = "insert into UserGroup (" + 
			"name" +
			") values (" + 
			":name)";
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("name", userGroupDto.getGroupName());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL, params, keyHolder, new String[]{"id"});
		return keyHolder.getKey().intValue();
	}

	// 删除用户组
	@Override
	public void delUserGroup(int userGroupId)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", userGroupId);

		{
			String SQL = "delete from UserWithGroup where groupId = :groupId";
			jdbcTemplate.update(SQL, params);
		}

		{
			String SQL = "delete from UserGroup where id = :groupId";
			jdbcTemplate.update(SQL, params);
		}
	}

	// 修改用户组名
	@Override
	public void updateUserGroup(int userGroupId, String userGroupName)
	{	
		final String SQL = "update UserGroup set name = :groupName WHERE id = :groupId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", userGroupId);
		params.put("groupName", userGroupName);
		jdbcTemplate.update(SQL, params);
	}

	// 查询用户组
	@Override
	public UserGroup queryUserGroupByName(String userGroupName)
	{
		final String SQL = "select UserGroup.id as id1," + 
			"UserGroup.name as name1," + 
			"UserPriviledge.id as id2," + 
			"UserPriviledge.name as name2" + 
			" from UserGroup " + 
			"inner join GroupWithPriviledge on UserGroup.id=GroupWithPriviledge.groupId " + 
			"inner join UserPriviledge on GroupWithPriviledge.priviledgeId=UserPriviledge.id " + 
			"where UserGroup.name = :groupName " + 
			"order by id1 asc, id2 asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupName", userGroupName);

		GroupWithPriviledgeRowHandler handler = new GroupWithPriviledgeRowHandler();
		jdbcTemplate.query(SQL, params, handler);
		List<UserGroup> userGroups = handler.getUserGroups();
		return (userGroups.isEmpty()?null:userGroups.get(0));
	}

	// 查询加入用户组的用户
	@Override
	public List<User> queryUsersByName(String userGroupName)
	{
		final String SQL = "select User.id as id1," + 
			"User.name as name1," + 
			"User.password as password," + 
			"UserGroup.id as id2," + 
			"UserGroup.name as name2," + 
			"UserPriviledge.id as id3," + 
			"UserPriviledge.name as name3" + 
			" from User " + 
			"inner join UserWithGroup on User.id=UserWithGroup.userId " + 
			"inner join UserGroup on UserWithGroup.groupId=UserGroup.id " + 
			"inner join GroupWithPriviledge on UserGroup.id=GroupWithPriviledge.groupId " + 
			"inner join UserPriviledge on GroupWithPriviledge.priviledgeId=UserPriviledge.id " + 
			"where UserGroup.name = :userGroupNamae " + 
			"order by id1 asc, id2 asc, id3 asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userGroupName", userGroupName);

		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		jdbcTemplate.query(SQL, params, handler);
		List<User> users = handler.getUsers();
		return users;
	}

	// 查询用户组id
	public int queryUserGroupIdByName(String userGroupName)
	{
		final String SQL = "select id from UserGroup where name = :groupName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupName", userGroupName);
		try{
			return jdbcTemplate.queryForObject(SQL, params, new RowMapper<Integer>(){
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return new Integer(rs.getInt("id"));
				}
			}).intValue();
		}catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}
}

