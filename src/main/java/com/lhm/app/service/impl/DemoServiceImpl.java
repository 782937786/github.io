package com.lhm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhm.app.entity.Product;
import com.lhm.app.mapper.DemoMapper;
import com.lhm.app.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService{

	@Autowired
	private DemoMapper mapper;
	
	public List<Product> findproduct() throws Exception{
		List<Product> productlist = mapper.testDemo();
		return productlist;
	}
}
