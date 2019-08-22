package com.dgq.crs.dao;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dgq.crs.entity.AreaNumber;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/*
* @Description: public class AreaNumberMapper{ }
* @author dgq 
* @date 2019年5月16日
*/
public interface AreaNumberMapper extends Mapper<AreaNumber>, InsertListMapper<AreaNumber>{
	
	/**
	 * 根据数据年度查询行政区划代码
	 * @param dataYear
	 * @return
	 * @throws Exception
	 */
	@Select("select * from REG.CRS_IMP_XZQH where yeardt = #{dataYear} order by xzqhdm asc")
	List<AreaNumber> queryAreaNumberByDataYear(@Param("dataYear")LocalDate dataYear) throws Exception;
	
	/**
	 * 查询所有年度的行政区划代码
	 * @return
	 * @throws Exception
	 */
	@Select("select * from REG.CRS_IMP_XZQH order by xzqhdm asc")
	List<AreaNumber> queryAreaNumberForPage() throws Exception;
	
	/**
	 * 删除指定数据年度的数据
	 * @param dataYear
	 * @return
	 */
	@Delete("delete from REG.CRS_IMP_XZQH where yeardt = #{dataYear}")
	int deleteAreaNumber(LocalDate dataYear);
}
