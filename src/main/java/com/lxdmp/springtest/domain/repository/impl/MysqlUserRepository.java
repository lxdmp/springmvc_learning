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
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserRepository;

@Repository("mysqlUserRepo")
public class MysqlUserRepository extends BaseRepository implements UserRepository
{
	@Override
	public void addUser(User user)
	{
		// 增加用户
		String SQL = "insert into User (" + 
			"name, password" +
			") values (" + 
			":name, :password)";
		Map<String, Object> params = new HashMap<>();
		params.put("name", user.getUserName());
		params.put("password", user.getUserPasswd());
		jdbcTemplate.update(SQL, params);
	}

	@Override
	public void delUser(String userName)
	{	
		// 删除用户
		User user = this.queryUserByName(userName);
		if(user==null)
			return;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());

		String SQL_DELETE_USER_WITH_GROUP = "delete from UserWithGroup where userId=:userId";
		jdbcTemplate.update(SQL_DELETE_USER_WITH_GROUP, params);

		String SQL_DELETE_USER = "delete from User where id = :userId";
		jdbcTemplate.update(SQL_DELETE_USER, params);
	}

	@Override
	public boolean updateUserPassword(String userName, String oldPassword, String newPassword)
	{
		// 修改用户密码
		User user = this.queryUserByName(userName);
		if(user==null)
			return false;
		if(!user.getUserPasswd().equals(oldPassword))
			return false;

		final String SQL = "update User set password = :userPw WHERE id = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userPw", newPassword);
		params.put("userId", user.getUserId());
		jdbcTemplate.update(SQL, params);
		return true;
	}

	@Override
	public User queryUserByName(String userName)
	{
		// 查询用户
		List<User> users = new LinkedList<User>();
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
			"on User.name = :userName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		jdbcTemplate.query(SQL, params, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException
			{
				int userId = rs.getInt("id1");
				String userName = rs.getString("name1");
				String userPasswd = rs.getString("password");
				if(users.isEmpty() || users.get(users.size()-1).getUserId()!=userId)
				{
					User user = new User();
					user.setUserId(userId);
					user.setUserName(userName);
					user.setUserPasswd(userPasswd);
					users.add(user);
				}
				User user = users.get(users.size()-1);

				int userGroupId = rs.getInt("id2");
				String userGroupName = rs.getString("name2");
				if( user.getUserGroups().isEmpty() || 
					user.getUserGroups().get(user.getUserGroups().size()-1).getGroupId()!=userGroupId)
				{
					UserGroup userGroup = new UserGroup();
					userGroup.setGroupId(userGroupId);
					userGroup.setGroupName(userGroupName);

					List<UserGroup> userGroups = user.getUserGroups();
					userGroups.add(userGroup);
					user.setUserGroups(userGroups);
				}
				UserGroup userGroup = user.getUserGroups().get(user.getUserGroups().size()-1);

				int priviledgeId = rs.getInt("id3");
				String priviledgeName = rs.getString("name3");
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
		});
		return (users.isEmpty()?null:users.get(0));
	}
}

