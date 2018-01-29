package com.lhm.app.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public final class TaskFactory {
	private Logger logger = LoggerFactory.getLogger(TaskFactory.class);
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;
	@Autowired
	private Environment env;

	/**
	 * @Title: createTask
	 * @Description: (创建定时任务)
	 * @param cls
	 * @throws SchedulerException
	 */
	public void createTask(Class<? extends Job> cls) throws SchedulerException {
		Scheduler scheduler = schedulerFactory.getScheduler();

		Task task = cls.getAnnotation(Task.class);
		// 校验Task注解信息
		if (task == null || Strings.isNullOrEmpty(task.corn()) || Strings.isNullOrEmpty(task.jobName())
				|| Strings.isNullOrEmpty(task.groupName())) {
			throw new SchedulerException("Task信息有误...请检查Task注解");
		}
		JobKey key = new JobKey(task.jobName(), task.groupName());

		if ("dev".equals(env.getProperty("spring.profiles.active"))) {
			// 开发环境删除Job 重新添加
			scheduler.deleteJob(key);
		}
		// 校验定时任务是否存在
		if (scheduler.checkExists(key)) {
			logger.debug(String.format("jobName为 '%s',groupName为 '%s' 任务已存在...", task.jobName(), task.groupName()));
		} else {
			JobDetail job = JobBuilder.newJob(cls).withIdentity(task.jobName(), task.groupName()).build();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.jobName(), task.groupName())
					.withSchedule(CronScheduleBuilder.cronSchedule(task.corn())).build();

			// 注入SpringBean
			capableBeanFactory.autowireBean(trigger);
			scheduler.scheduleJob(job, trigger);
		}
	}

	/**
	 * @Title: start
	 * @Description: (开启定时任务)
	 * @throws SchedulerException
	 */
	public void start() throws SchedulerException {
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
	}
}
