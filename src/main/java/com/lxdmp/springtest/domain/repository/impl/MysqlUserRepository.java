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
	public int addUser(UserDto userDto)
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
	public void delUser(int userId)
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

	// 查询用户
	@Override
	public User queryUserByName(String userName)
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
			"where User.name = :userName " + 
			"order by id1 asc, id2 asc, id3 asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);

		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		jdbcTemplate.query(SQL, params, handler);
		List<User> users = handler.getUsers();
		return (users.isEmpty()?null:users.get(0));
	}

	// 查询用户id
	public int queryUserIdByName(String userName)
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

