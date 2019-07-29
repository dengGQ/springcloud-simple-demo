package com.dgq.quartz.commons.page;

import java.util.List;

import com.github.pagehelper.PageInfo;

/*
* @Description: 构建页面所需的分页属性信息，用其他前端框架时只需实现自己的ResultPage
* @author dgq 
* @date 2019年5月16日
*/
public class ResultPageFactory {
	private static final ResultPageFactory intance;
	static {
		intance = new ResultPageFactory();
	}
	
	public ResultPage build(List<?> data) {
		PageInfo<?> pageInfo = new PageInfo<>(data);
		ResultPage resultPage = new ResultPageForTotalAndRows(pageInfo.getTotal(), pageInfo.getList());
		return resultPage;
	}
	
	public ResultPage build(List<?> data, long total) {
		PageInfo<?> pageInfo = new PageInfo<>(data);
		ResultPage resultPage = new ResultPageForTotalAndRows(total, pageInfo.getList());
		return resultPage;
	}
	
	public static ResultPageFactory newIntance() {
		return intance;
	}
	private ResultPageFactory() {}
	
	public final class ResultPageForTotalAndRows implements ResultPage{
		private long total;
		private List<?> rows;
		public ResultPageForTotalAndRows(long total, List<?> rows) {
			super();
			this.total = total;
			this.rows = rows;
		}
		public long getTotal() {
			return total;
		}
		public List<?> getRows() {
			return rows;
		}
	}
}
