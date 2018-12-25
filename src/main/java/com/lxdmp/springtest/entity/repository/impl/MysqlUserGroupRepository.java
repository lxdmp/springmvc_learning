package com.lxdmp.springtest.entity.repository.impl;

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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.entity.UserPriviledge;
import com.lxdmp.springtest.entity.repository.UserGroupRepository;
import com.lxdmp.springtest.entity.repository.impl.GroupWithPriviledgeRowHandler;

@Repository("mysqlUserGroupRepo")
public class MysqlUserGroupRepository extends BaseRepository implements UserGroupRepository
{
	// 增加用户组
	@Override
	public Integer addUserGroup(UserGroupDto userGroupDto)
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
	public void delUserGroup(Integer userGroupId)
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
	public void updateUserGroup(Integer userGroupId, String userGroupName)
	{	
		final String SQL = "update UserGroup set name = :groupName WHERE id = :groupId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", userGroupId);
		params.put("groupName", userGroupName);
		jdbcTemplate.update(SQL, params);
	}

	// 查询所有用户组
	@Override
	public List<UserGroup> queryAllUserGroups()
	{
		GroupWithPriviledgeRowHandler handler = new GroupWithPriviledgeRowHandler();
		String SQL = handler.queryAll();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<UserGroup> userGroups = handler.getUserGroups();
		return userGroups;
	}

	// 查询用户组
	@Override
	public UserGroup queryUserGroupByName(String userGroupName)
	{
		final String key = "groupName";	
		GroupWithPriviledgeRowHandler handler = new GroupWithPriviledgeRowHandler();
		String SQL = handler.queryWithGroupName(key);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(key, userGroupName);
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<UserGroup> userGroups = handler.getUserGroups();
		return (userGroups.isEmpty()?null:userGroups.get(0));
	}

	// 查询加入用户组的用户
	@Override
	public List<User> queryUsersByName(String userGroupName)
	{
		final String key = "userGroupName";
		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		String SQL = handler.queryWithGroupName(key);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(key, userGroupName);
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<User> users = handler.getUsers();
		return users;
	}

	// 查询用户组id
	public Integer queryUserGroupIdByName(String userGroupName)
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

