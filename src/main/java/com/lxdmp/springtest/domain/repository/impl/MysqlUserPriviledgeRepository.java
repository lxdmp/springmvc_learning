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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.DataAccessException;
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
	public void addUserPriviledge(UserPriviledgeDto userPriviledgeDto)
	{
		String SQL = "insert into UserPriviledge (" + 
			"name" +
			") values (" + 
			":name)";
		Map<String, Object> params = new HashMap<>();
		params.put("name", userPriviledgeDto.getPriviledgeName());
		jdbcTemplate.update(SQL, params);
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

	// 查询具有该权限的用户
	public List<User> queryUsersByName(String userPriviledgeName)
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
			"where UserPriviledge.name = :priviledgeName " + 
			"order by id1 asc, id2 asc, id3 asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("priviledgeName", userPriviledgeName);

		UserWithGroupWithPriviledgeRowHandler handler = new UserWithGroupWithPriviledgeRowHandler();
		jdbcTemplate.query(SQL, params, handler);
		List<User> users = handler.getUsers();
		return users;
	}

	// 查询具有该权限的用户组
	public List<UserGroup> queryUserGroupsByName(String userPriviledgeName)
	{
		final String SQL = "select UserGroup.id as id1," + 
			"UserGroup.name as name1," + 
			"UserPriviledge.id as id2," + 
			"UserPriviledge.name as name2" + 
			" from UserGroup " + 
			"inner join GroupWithPriviledge on UserGroup.id=GroupWithPriviledge.groupId " + 
			"inner join UserPriviledge on GroupWithPriviledge.priviledgeId=UserPriviledge.id " + 
			"where UserPriviledge.name = :priviledgeName " + 
			"order by id1 asc, id2 asc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("priviledgeName", userPriviledgeName);

		GroupWithPriviledgeRowHandler handler = new GroupWithPriviledgeRowHandler();
		jdbcTemplate.query(SQL, params, handler);
		List<UserGroup> userGroups = handler.getUserGroups();
		return userGroups;
	}
}

