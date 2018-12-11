package com.lxdmp.springtest.domain.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
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
	public void addUser(UserDto userDto)
	{
		String SQL = "insert into User (" + 
			"name, password" +
			") values (" + 
			":name, :password)";
		Map<String, Object> params = new HashMap<>();
		params.put("name", userDto.getUserName());
		params.put("password", userDto.getUserPasswd());
		jdbcTemplate.update(SQL, params);
	}

	// 删除用户
	@Override
	public void delUser(int userId)
	{	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);

		String SQL_DELETE_USER_WITH_GROUP = "delete from UserWithGroup where userId=:userId";
		jdbcTemplate.update(SQL_DELETE_USER_WITH_GROUP, params);

		String SQL_DELETE_USER = "delete from User where id = :userId";
		jdbcTemplate.update(SQL_DELETE_USER, params);
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
}

