package com.lxdmp.springtest.schedule;

import java.util.*;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.apache.log4j.Logger;

@Component
public class TaskScheduler
{
	private static final Logger logger = Logger.getLogger(TaskScheduler.class);
	
	// 格式与cron一致(与cron相比多了秒的精度,每天hh:mm:ss执行)
	@Scheduled(cron = "0 30 12 * * *")
	public void dailyWork()
	{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(String.format("daily work : %s", f.format(new Date())));
	}

	// 启动时执行一次,之后以若干时间周期执行(单位ms)
	@Scheduled(fixedRate = 1000*5)
	public void periodcTask()
	{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(String.format("periodic work : %s", f.format(new Date())));
	}
}

