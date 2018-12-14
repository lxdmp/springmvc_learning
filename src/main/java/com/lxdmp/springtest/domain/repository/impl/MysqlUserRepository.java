package com.lxdmp.springtest.domain.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.lxdmp.springtest.domain.User;
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserRepository;
import com.lxdmp.springtest.domain.repository.impl.UserWithGroupWithPriviledgeRowHandler;

@Repository("mysqlUserRepo")
public class MysqlUserRepository extends BaseRepository implements UserRepository
{
	// 增加用户
	@Override
	public Integer addUser(UserDto userDto)
	{
		String SQL = "insert into User (" + 
			"name, password" +
			") values (" + 
			":name, :password)";
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("name", userDto.getUserName())
			.addValue("password", userDto.getUserPasswd());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL, params, keyHolder, new String[]{"id"});
		return keyHolder.getKey().intValue();
	}

	// 删除用户
	@Override
	public void delUser(Integer userId)
	{	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);

		{
			String SQL = "delete from UserWithGroup where userId=:userId";
			jdbcTemplate.update(SQL, params);
		}

		{
			String SQL = "delete from User where id = :userId";
			jdbcTemplate.update(SQL, params);
		}
	}

	// 修改用户密码
	@Override
	public void updateUserPassword(String userName, String newPassword)
	{
		final String SQL = "update User set password = :userPw WHERE name = :userName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userPw", newPassword);
		params.put("userName", userName);
		jdbcTemplate.update(SQL, params);
	}

	// 查询所有用户
	@Override
	public List<User> queryAllUsers()
	{
		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		String SQL = handler.queryAll();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<User> users = handler.getUsers();
		return users;
	}

	// 查询用户
	@Override
	public User queryUserByName(String userName)
	{
		final String key = "userName";
		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		String SQL = handler.queryWithUserName(key);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(key, userName);
		try{
			jdbcTemplate.query(SQL, params, handler);
		}catch(DataAccessException e){
		}
		List<User> users = handler.getUsers();
		return (users.isEmpty()?null:users.get(0));
	}

	// 查询用户id
	public Integer queryUserIdByName(String userName)
	{
		final String SQL = "select id from User where name = :userName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
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

