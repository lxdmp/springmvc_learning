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
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;
import com.lxdmp.springtest.domain.repository.UserAndGroupRepository;

@Repository("mysqlUserAndGroupRepo")
public class MysqlUserAndGroupRepository extends BaseRepository implements UserAndGroupRepository
{
	// 用户加入某用户组
	@Override
	public void userJoinGroup(Integer userId, Integer userGroupId)
	{
		final String SQL = "insert into UserWithGroup (" + 
			"userId, groupId" +
			") values (" + 
			":userId, :groupId)";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("groupId", userGroupId);
		jdbcTemplate.update(SQL, params);
	}

	// 用户离开某用户组
	@Override
	public void userLeaveGroup(Integer userId, Integer userGroupId)
	{
		final String SQL = "delete from UserWithGroup where userId = :userId and groupId = :groupId";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("groupId", userGroupId);
		jdbcTemplate.update(SQL, params);
	}
}

