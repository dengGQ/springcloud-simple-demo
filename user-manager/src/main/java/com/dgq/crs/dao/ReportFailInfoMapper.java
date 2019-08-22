package com.dgq.crs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dgq.crs.entity.ReportFailInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/*
* @Description: public class ReportFailInfoMapper{ }
* @author dgq 
* @date 2019年6月21日
*/
public interface ReportFailInfoMapper extends Mapper<ReportFailInfo>, InsertListMapper<ReportFailInfo>{
	
	
	@Select("select f.*, d.dict_two_level, d.dict_name from REG.REPORT_FAIL_INFO f left join reg.crs_sys_dict d on f.err_code = d.dict_code and d.dict_type = 'chk_error_info' ${condition} order by f.create_time desc")
	List<ReportFailInfo> queryReportFailInfoForPageByErrCode(@Param("condition")String condition) throws Exception;

	@Select("select f.*, d.dict_two_level, d.dict_name from REG.REPORT_FAIL_INFO f left join reg.crs_sys_dict d on f.err_code = d.dict_code and d.dict_type = 'chk_error_info' order by f.create_time desc")
	List<ReportFailInfo> queryReportFailInfoForPage() throws Exception;
	
	@Delete("delete from REG.REPORT_FAIL_INFO i where i.data_year = #{dataYear} and i.message_ref_id = #{messageRefId}")
	void delByMessageRefIdAndDataYear(@Param("dataYear")Date dataYear, @Param("messageRefId") String messageRefId);
}
