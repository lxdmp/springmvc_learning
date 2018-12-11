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

	@Override 
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if(event.getApplicationContext().getParent()!=null)
			return;
		//logger.info("execute some initialization...");
	}
}

