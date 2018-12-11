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
import com.lxdmp.springtest.domain.repository.GroupAndPriviledgeRepository;

@Repository("mysqlGroupAndPriviledgeRepo")
public class MysqlGroupAndPriviledgeRepository extends BaseRepository implements GroupAndPriviledgeRepository
{
	// 用户组给予某权限
	public void addPriviledgeToGroup(int userGroupId, int priviledgeId)
	{
		final String SQL = "insert into GroupWithPriviledge (" + 
			"groupId, priviledgeId" +
			") values (" + 
			":groupId, :priviledgeId)";
		Map<String, Object> params = new HashMap<>();
		params.put("groupId", userGroupId);
		params.put("priviledgeId", priviledgeId);
		jdbcTemplate.update(SQL, params);
	}

	// 用户组去除某权限
	public void delPriviledgeFromGroup(int userGroupId, int priviledgeId)
	{
		final String SQL = "delete from GroupWithPriviledge where groupId = :groupId and priviledgeId = :priviledgeId";
		Map<String, Object> params = new HashMap<>();
		params.put("groupId", userGroupId);
		params.put("priviledgeId", priviledgeId);
		jdbcTemplate.update(SQL, params);
	}
}

