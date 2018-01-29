package com.lhm.app.quartjob;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lhm.app.config.Task;
import com.lhm.app.entity.Product;
import com.lhm.app.service.DemoService;


/**
 * 创建一个定时任务
 * @author lhm 2017-11-10
 */
//@Task(corn = "0 51 11 * * ? ", jobName = "sampleJob", groupName = "sampleGroup")
@Task(corn = "0 0/1 * * * ? ", jobName = "sampleJob", groupName = "sampleGroup")
public class SampleJob implements Job {
	
	@Autowired 
	private DemoService demoService;
	
	private Logger logger = LoggerFactory.getLogger(SampleJob.class);
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		List<Product> productlist;
		try {
			productlist = demoService.findproduct();
			if(productlist != null && productlist.size() > 0){
				for(Product pro : productlist){
					System.out.println(pro.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
