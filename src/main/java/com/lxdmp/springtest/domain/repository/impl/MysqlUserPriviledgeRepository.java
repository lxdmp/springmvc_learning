package com.lxdmp.springtest.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.dto.UserPriviledgeDto;
import com.lxdmp.springtest.domain.repository.UserPriviledgeRepository;
import com.lxdmp.springtest.domain.repository.impl.UserWithGroupWithPriviledgeRowHandler;

@Repository("mysqlUserPriviledgeRepo")
public class MysqlUserPriviledgeRepository extends BaseRepository implements UserPriviledgeRepository
{
	// 增加用户权限
	public int addUserPriviledge(UserPriviledgeDto userPriviledgeDto)
	{
		String SQL = "insert into UserPriviledge (" + 
			"name" +
			") values (" + 
			":name)";
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("name", userPriviledgeDto.getPriviledgeName());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL, params, keyHolder, new String[]{"id"});
		return keyHolder.getKey().intValue();
	}

	// 删除用户权限
	public void delUserPriviledge(int userPriviledgeId)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("priviledgeId", userPriviledgeId);

		{
			String SQL = "delete from GroupWithPriviledge where priviledgeId = :priviledgeId";
			jdbcTemplate.update(SQL, params);
		}

		{
			String SQL = "delete from UserPriviledge where id = :priviledgeId";
			jdbcTemplate.update(SQL, params);
		}
	}

	// 修改用户权限名
	public void updateUserPriviledge(int userPriviledgeId, String userPriviledgeName)
	{
		final String SQL = "update UserPriviledge set name = :priviledgeName WHERE id = :priviledgeId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("priviledgeId", userPriviledgeId);
		params.put("priviledgeName", userPriviledgeName);
		jdbcTemplate.update(SQL, params);
	}

	// 查询用户权限
	public UserPriviledge queryUserPriviledgeByName(String userPriviledgeName)
	{
		final String SQL = "select id,name from UserPriviledge where name = :priviledgeName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("priviledgeName", userPriviledgeName);
		try{
			return jdbcTemplate.queryForObject(SQL, params, new RowMapper<UserPriviledge>(){
				@Override
				public UserPriviledge mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					UserPriviledge userPriviledge = new UserPriviledge();
					userPriviledge.setPriviledgeId(rs.getInt("id"));
					userPriviledge.setPriviledgeName(rs.getString("name"));
					return userPriviledge;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 查询用户权限id
	public int queryUserPriviledgeIdByName(String userPriviledgeName)
	{
		final String SQL = "select id from UserPriviledge where name = :priviledgeName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("priviledgeName", userPriviledgeName);
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

	// 查询具有该权限的用户
	public List<User> queryUsersByName(String userPriviledgeName)
	{
		final String key = "priviledgeName";
		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		String SQL = handler.queryWithPriviledgeName(key);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(key, userPriviledgeName);
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<User> users = handler.getUsers();
		return users;
	}

	// 查询具有该权限的用户组
	public List<UserGroup> queryUserGroupsByName(String userPriviledgeName)
	{
		final String key = "priviledgeName";
		GroupWithPriviledgeRowHandler handler = new GroupWithPriviledgeRowHandler();
		String SQL = handler.queryWithPriviledgeName(key);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(key, userPriviledgeName);
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<UserGroup> userGroups = handler.getUserGroups();
		return userGroups;
	}
}

