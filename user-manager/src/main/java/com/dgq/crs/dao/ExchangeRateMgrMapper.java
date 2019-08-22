package com.dgq.crs.dao;

import com.dgq.crs.entity.ExchangeRateMgr;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/*
* @Description: public class ExchangeRateMapper{ }
* @author dgq 
* @date 2018年12月7日
*/
public interface ExchangeRateMgrMapper extends Mapper<ExchangeRateMgr>{

    /**
     * 查询当前范围内的汇率信息
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @Select("select * from reg.exchange_rate_mgr where datee between #{startDate} and #{endDate} order by num desc")
    List<ExchangeRateMgr> queryExchangeRate(@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

    @Select("select * from reg.exchange_rate_mgr where datee = #{datee}")
    List<ExchangeRateMgr> queryExchangeRateByDatee(@Param(value="datee")Date datee);
}
