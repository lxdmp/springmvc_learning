package com.lxdmp.springtest.schedule;
 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.apache.log4j.Logger;
import com.lxdmp.springtest.service.UserService;
import com.lxdmp.springtest.service.UserGroupService;
import com.lxdmp.springtest.service.UserPriviledgeService;
import com.lxdmp.springtest.entity.User;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.entity.UserPriviledge;
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.dto.UserPriviledgeDto;

@Component
public class TaskInitializer implements ApplicationListener<ContextRefreshedEvent>
{
	private static final Logger logger = Logger.getLogger(TaskInitializer.class);

	@Autowired
	UserService userService;

	@Autowired
	UserGroupService groupService;

	@Autowired
	UserPriviledgeService priviledgeService;

	@Override 
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if(event.getApplicationContext().getParent()!=null)
			return;
		//logger.info("execute some initialization...");
		
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		this.loadDefaultAdmin();
	}

	private void loadDefaultAdmin()
	{
		final String userName = "admin";
		final String userPassword = "admin";
		final String adminGroupName = "管理员";
		final String priviledgeName = "权限管理";

		// 管理员账户
		UserDto userDto = new UserDto();
		userDto.setUserName(userName);
		userDto.setUserPasswd(userPassword);
		userService.addUser(userDto);
		User user = userService.queryUserByName(userName);
		logger.debug(String.valueOf(user));
		
		// 管理员组
		UserGroupDto groupDto = new UserGroupDto();
		groupDto.setGroupName(adminGroupName);
		groupService.addUserGroup(groupDto);
		UserGroup userGroup = groupService.queryUserGroupByName(adminGroupName);
		logger.debug(String.valueOf(userGroup));

		// "管理员"加入"管理员组"
		userService.userJoinGroup(userName, adminGroupName);

		// "权限管理"权限
		UserPriviledgeDto priviledgeDto = new UserPriviledgeDto();
		priviledgeDto.setPriviledgeName(priviledgeName);
		priviledgeService.addUserPriviledge(priviledgeDto);
		UserPriviledge priviledge = priviledgeService.queryUserPriviledgeByName(priviledgeName);
		logger.debug(String.valueOf(priviledge));

		// "管理员组"赋予"权限管理"权限
		groupService.userGroupAddPriviledge(adminGroupName, priviledgeName);
		List<User> usersHasPriviledge = priviledgeService.usersWithPriviledge(priviledgeName);
		for(User userHasPriviledge : usersHasPriviledge)
		{
			logger.debug(String.valueOf(userHasPriviledge));
		}
		
		/*
		logger.info("all users:");
		List<User> allUsers = userService.queryAllUsers();
		for(User singleUser : allUsers)
		{
			logger.info(String.valueOf(singleUser));
		}

		logger.info("all groups:");
		List<UserGroup> allUserGroups = groupService.queryAllUserGroups();
		for(UserGroup singleGroup : allUserGroups)
		{
			logger.info(String.valueOf(singleGroup));
		}

		logger.info("all priviledges:");
		List<UserPriviledge> allUserPriviledges = priviledgeService.queryAllUserPriviledges();
		for(UserPriviledge singlePriviledge : allUserPriviledges)
		{
			logger.info(String.valueOf(singlePriviledge));
		}
		*/
	}
}

