package com.fotic.test.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fotic.test.model.Test;

import tk.mybatis.mapper.common.Mapper;
public interface TestMapper   extends Mapper<Test>{

	//int insert(Test record);

    //int insertSelective(Test record);
    
	@Select(value="select * from Test where id = #{id}")
    Test getById(long id);
	
	@Select(value="select name from Test where id = #{id}")
	Map<String,String> getName(@Param(value="id") long id);
}