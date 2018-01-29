package com.lhm.app.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lhm.app.config.TaskFactory;
import com.lhm.app.entity.Product;
import com.lhm.app.quartjob.SampleJob;
import com.lhm.app.service.DemoService;

@Component
public class JobFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(JobFilter.class);
	
	@Autowired
	private TaskFactory factory;
	
	@Autowired
	private DemoService service;
	
	@Override
	public void destroy() {
		logger.debug("应用程序关闭...");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.debug("应用程序启动...");
		
		/**
		 * 直接触发程序启动
		 */
		try {
			List<Product> productlist;
			try {
				productlist = service.findproduct();
				if(productlist != null && productlist.size() > 0){
					for(Product pro : productlist){
						System.out.println(pro.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			/**
			 * 这里触发定时任务
			 * 可以有多个定时任务
			 */
			factory.createTask(SampleJob.class);
			factory.start();		
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
