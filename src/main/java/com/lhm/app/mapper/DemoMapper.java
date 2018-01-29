package com.lhm.app.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lhm.app.entity.Product;

@Repository
public interface DemoMapper{

	List<Product> testDemo() throws Exception;
}
