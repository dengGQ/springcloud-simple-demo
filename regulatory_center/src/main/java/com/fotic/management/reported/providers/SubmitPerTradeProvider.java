package com.fotic.management.reported.providers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

/**
 * 交易结果表, 编写复杂sql
 * @author zhaoqh
 */
@Component
public class SubmitPerTradeProvider {

	/**
	 * 按天统计需上报的数据信息
	 * @return sql语句
	 */
	public String findList(Map<String, Object> para){
		String sql = new SQL(){{
            SELECT(" TO_CHAR(BUSS_DATE, 'YYYY-MM') insertDttm, data_src dataSrc, count(1) allNum ");
            SELECT(" SUM(DECODE(DATA_STATUS,1,1,0)) WAITINGNUM,sum(decode(data_status,2,1,0)) successNum, sum(decode(data_status,3,1,0)) failNum");
            FROM(" rhzx_submt_per_trade");
            if(para.get("dataSrc")!=null){
            	WHERE(" data_src=" +para.get("dataSrc"));
            }     
            GROUP_BY(" TO_CHAR(BUSS_DATE, 'YYYY-MM'),  data_src");
        }}.toString();
        return sql;
	}
	public String findListOrg(Map<String, Object> para){
		String sql = new SQL(){{
            SELECT("org.co_org_name coOrgName,rh.co_org_code coOrgCode");
           /* SELECT(" SUM(DECODE(DATA_STATUS,1,1,0)) WAITINGNUM,sum(decode(data_status,2,1,0)) successNum, sum(decode(data_status,3,1,0)) failNum");*/
            FROM(" rhzx_submt_per_trade rh LEFT JOIN V_MFS_ORG org ON rh.co_org_code = org.co_org_code ");
            if(para.get("dataSrc")!=null){
            	WHERE(" data_src=" +para.get("dataSrc"));
            }     
            GROUP_BY(" org.co_org_name,rh.co_org_code");
        }}.toString();
        return sql;
	}
	public String findListAll(Map<String, Object> para){
		String startDate = (String) para.get("startDate");
		String endDate = (String) para.get("endDate");
		String coOrgCode = (String) para.get("coOrgName");
		String sql = new SQL(){{
			if(StringUtils.isNotBlank(endDate)){
				SELECT("  data_src dataSrc, count(1) allNum ");
			}else {
				SELECT("  TO_CHAR(billingdate, 'YYYY-MM-dd') insertDttm, data_src dataSrc, count(1) allNum ");
			}
            
            SELECT(" SUM(DECODE(DATA_STATUS,1,1,0)) WAITINGNUM,sum(decode(data_status,2,1,0)) successNum, sum(decode(data_status,3,1,0)) failNum ,org.co_org_name coOrgName");
            FROM(" rhzx_submt_per_trade rh LEFT JOIN V_MFS_ORG org ON rh.co_org_code = org.co_org_code");
            if(para.get("dataSrc")!=null){
            	WHERE(" data_src=" +para.get("dataSrc"));
            }
            if(StringUtils.isNotBlank(endDate)){
            	WHERE(" TO_CHAR(billingdate, 'YYYY-MM-dd')<='" +para.get("endDate")+"'");
            }
            if(StringUtils.isNotBlank(startDate)){
            	WHERE(" TO_CHAR(billingdate, 'YYYY-MM-dd')>='" +para.get("startDate")+"'");
            }
            if(StringUtils.isNotBlank(coOrgCode)){
            	WHERE("org.co_org_code ='" +para.get("coOrgName")+"'");
            }
            if(StringUtils.isNotBlank(startDate)){
            	GROUP_BY(" data_src,org.co_org_name");
            }else {
            	GROUP_BY(" TO_CHAR(billingdate, 'YYYY-MM-dd'),  data_src ,org.co_org_name");
			}
            
        }}.toString();
        return sql;
	}
	/**
	 * 查询全部需上报的数据信息
	 * @param para
	 * @return sql语句
	 */
	public String findAll(Map<String, Object> para){
		String sql = new SQL(){{
            SELECT(" count(1) allNum ");
            SELECT(" SUM(DECODE(DATA_STATUS,1,1,0)) WAITINGNUM,sum(decode(data_status,2,1,0)) successNum, sum(decode(data_status,3,1,0)) failNum");
            FROM(" rhzx_submt_per_trade");
            if(para.get("dataSrc")!=null){
            	WHERE(" data_src=" +para.get("dataSrc"));
            }     
        }}.toString();
        return sql;
	}
	
	
	/**
	 * 查询全部需上报的数据信息
	 * @param para
	 * @return sql语句
	 */
	public String findByBussDate(Map<String, Object> para){
		String sql = new SQL(){{
            SELECT(" count(1) allNum ");
            SELECT(" sum(decode(data_status,2,1,0)) successNum, sum(decode(data_status,3,1,0)) failNum");
            FROM(" rhzx_submt_per_trade");
            if(para.get("dataSrc")!=null){
            	WHERE(" data_src=" +para.get("dataSrc"));
            }   
            if(para.get("bussDate")!=null){
            	WHERE("buss_date >= TO_DATE('" + para.get("bussDate") + "','yyyy-MM-dd')");
            } 
        }}.toString();
        return sql;
	}
}
