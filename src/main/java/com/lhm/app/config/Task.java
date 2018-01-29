package com.lhm.app.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Task
 * @Description: (定时任务信息注解)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Task {
	/**
	 * @Title: corn 
	 * @Description: (Cron表达式) 
	 * @return
	 */
	String corn();

	/**
	 * @Title: jobName 
	 * @Description: (定时任务名称) 
	 * @return
	 */
	String jobName();

	/**
	 * @Title: groupName 
	 * @Description: (分组名称) 
	 * @return
	 */
	String groupName();
}
