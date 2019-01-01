package com.lxdmp.springtest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*
import com.lxdmp.springtest.entity.repository.UserRepository;
import com.lxdmp.springtest.entity.repository.UserAndGroupRepository;
import com.lxdmp.springtest.entity.repository.UserGroupRepository;
import com.lxdmp.springtest.entity.repository.GroupAndPriviledgeRepository;
import com.lxdmp.springtest.entity.repository.UserPriviledgeRepository;
*/
import com.lxdmp.springtest.dao.UserDao;
import com.lxdmp.springtest.dao.UserAndGroupDao;
import com.lxdmp.springtest.dao.UserGroupDao;
import com.lxdmp.springtest.dao.GroupAndPriviledgeDao;
import com.lxdmp.springtest.dao.UserPriviledgeDao;

public class UserServiceBaseImpl
{
	/*
	@Autowired
	@Qualifier("mysqlUserRepo")
	protected UserRepository userRepository;

	@Autowired
	@Qualifier("mysqlUserAndGroupRepo")
	protected UserAndGroupRepository userAndGroupRepository;

	@Autowired
	@Qualifier("mysqlUserGroupRepo")
	protected UserGroupRepository userGroupRepository;

	@Autowired
	@Qualifier("mysqlGroupAndPriviledgeRepo")
	protected GroupAndPriviledgeRepository groupAndPriviledgeRepository;

	@Autowired
	@Qualifier("mysqlUserPriviledgeRepo")
	protected UserPriviledgeRepository userPriviledgeRepository;
	*/

	@Autowired
	protected UserDao userRepository;

	@Autowired
	protected UserAndGroupDao userAndGroupRepository;

	@Autowired
	protected UserGroupDao userGroupRepository;

	@Autowired
	protected GroupAndPriviledgeDao groupAndPriviledgeRepository;

	@Autowired
	protected UserPriviledgeDao userPriviledgeRepository;

	protected boolean isIdValid(Integer userId)
	{
		return (userId!=null && userId>=0);
	}
}

