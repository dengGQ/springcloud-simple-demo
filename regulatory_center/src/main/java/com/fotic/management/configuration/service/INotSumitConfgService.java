package com.fotic.management.configuration.service;
import java.util.List;
import com.fotic.management.configuration.entity.NotSumitConfg;
import com.fotic.management.configuration.entity.NotSumitConfgInfo;
public interface INotSumitConfgService {
	int insertNotSumitConfg(NotSumitConfg entity);
	int updateNotSumitConfg(NotSumitConfg entity);
	List<NotSumitConfg> findAll(); 
	/**
	 * 条件过滤，不显示失效的配置信息
	 * @param project 项目编号
	 * @param prod 产品编号
	 * @param org 机构编号
	 * @param conno 合同编号
	 */
	List<NotSumitConfg> findAllValid(String project, String prod, String org, String conno); 
	/**
	 * 不报信息条件查询
	 * @param project 信托项目
	 * @param prod	   信托产品
	 * @param org	   合作机构
	 * @param conno	   合同号
	 * @param dataSrc 数据来源
	 * @return
	 */
	List<NotSumitConfgInfo> findList(String project, String prod, String org, String conno, String dataSrc);
	/**
	 * 查找单条数据
	 * @param entity
	 * @return
	 */
	NotSumitConfg findOne(NotSumitConfg entity);
	
	void insertNotsubMitDatas(List<NotSumitConfg> list);
}
