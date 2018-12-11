package com.lxdmp.springtest.schedule;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.apache.log4j.Logger;
import com.lxdmp.springtest.service.UserService;
import com.lxdmp.springtest.service.UserGroupService;
import com.lxdmp.springtest.service.UserPriviledgeService;
import com.lxdmp.springtest.dto.UserDto;
import com.lxdmp.springtest.dto.UserGroupDto;
import com.lxdmp.springtest.dto.UserPriviledgeDto;

@Component
public class TaskInitializer implements ApplicationListener<ContextRefreshedEvent>
{
	private static final Logger logger = Logger.getLogger(TaskInitializer.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserPriviledgeService userPriviledgeService;

	@Override 
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if(event.getApplicationContext().getParent()!=null)
			return;
		//logger.info("execute some initialization...");
		
		/*
		 * 用户 : admin
		 * 密码 : admin
		 * 用户组 : 管理员
		 * 用户权限 : all
		 */
		final String username = "admin";
		final String passwd = "admin";
		final String group = "管理员";
		final String priviledge = "all";

		UserDto userDto = new UserDto();
		userDto.setUserName(username);
		userDto.setUserPasswd(passwd);
		userService.addUser(userDto);

		UserGroupDto userGroupDto = new UserGroupDto();
		userGroupDto.setGroupName(group);
		userGroupService.addUserGroup(userGroupDto);

		UserPriviledgeDto userPriviledgeDto = new UserPriviledgeDto();
		userPriviledgeDto.setPriviledgeName(priviledge);
		userPriviledgeService.addUserPriviledge(userPriviledgeDto);

		userService.userJoinGroup(username, group);
		userGroupService.userGroupAddPriviledge(group, priviledge);
	}
}

