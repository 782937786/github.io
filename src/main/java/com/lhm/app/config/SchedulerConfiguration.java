package com.lhm.app.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @ClassName: SchedulerConfiguration
 * @Description: (定时任务配置文件)
 * @date 2016年9月7日 上午9:06:52
 */
@Configuration
public class SchedulerConfiguration {
	
	/**
	 * @Title: quartZDataSource 
	 * @Description: (设置定时任务的数据源) 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
//	@Bean(name = "quartZDataSource")
//	public DataSource quartZDataSource(@Value("${spring.datasource.quartz.driverClassName}") String driver,
//			@Value("${spring.datasource.quartz.url}") String url,
//			@Value("${spring.datasource.quartz.username}") String username,
//			@Value("${spring.datasource.quartz.password}") String password) {
//		DruidDataSource druidDataSource = new DruidDataSource();
//		druidDataSource.setDriverClassName(driver);
//		druidDataSource.setUrl(url);
//		druidDataSource.setUsername(username);
//		druidDataSource.setPassword(password);
//		return druidDataSource;
//	}

	/**
	 * @Title: jobFactory 
	 * @Description: (交给Spring管理) 
	 * @param applicationContext
	 * @return
	 */
	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	/**
	 * @Title: schedulerFactoryBean 
	 * @Description: (配置定时任务基本信息) 
	 * @param dataSource
	 * @param jobFactory
	 * @return
	 * @throws IOException
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());

		return factory;
	}

	/**
	 * @Title: quartzProperties 
	 * @Description: (读取定时任务配置文件) 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
}
