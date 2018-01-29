package com.lhm.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lhm.app.entity.Product;
import com.lhm.app.service.DemoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/demo")
public class democontroller {

	@Autowired
	private DemoService service;
	
	@RequestMapping("/test1")
	@ApiOperation("得到商品名称")
	@ApiImplicitParam(name="ceshi",value="只是做一个测试")
	public void findProduct(){
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
	}
}
