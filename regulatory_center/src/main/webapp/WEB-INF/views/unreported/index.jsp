<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>应报未报数据查询</title>
    <!-- 导入jquery bootstrap js css lib -->
    <%@include file="/common/jsp/bootstrap.jsp"%>
</head>
<body>
	

	<div class="container-fluid">
			<div class="row base-margin" id="query">
	        <!-- <ol class="breadcrumb">
	            <li><strong><span style="color: #27a0d7"></span></strong></li>
	        </ol> -->
	        <form class="form-inline" role="form"  method="post" id="queryForm">
	            <div class="form-group">
	                <label>起始月份</label> 
	            	<input placeholder="起始月份" class="form-control form_datetime" id="startMonth" name="startMonth"> 
					<input type="hidden" id="dtp_input1" value="" /><br/>
	            </div>
	            <div class="form-group">
	                <label for="endMonth">截止月份</label> 
	                <input placeholder="截止月份" class="form-control form_datetime" id="endMonth" name="endMonth"> 
					<input type="hidden" id="dtp_input1" value="" /><br/>
	            </div>
	            <div class="form-group">
					<label >数据来源:</label> 
					 <select id="dataSrc" name="dataSrc" class="form-control">
                            <option value="1">信贷</option>
                            <option value="2" selected="selected">CSV</option>
                        </select>
				</div>
	            <div class="form-group btn2">
	                <button type="button" id="queryBtn" class="btn btn-primary">查询</button>
	                <button type="button" id="resetBtn"  class="btn btn-primary">重置</button>
	            </div>
	            
	        </form>
	    </div>
    <div class="container fit-body" style="width: 100%">
        <div class="fixed-table-toolbar">
        	<div class="bs-bars pull-left">
	    		<div id="toolbar" class="btn-group group3">
			       <div class="btn-group">
			        	<button id="exportBtn" class="btn btn-primary " >导出</button>
			       </div>
	    		</div>
	    	</div>
      	</div>
        <table id="unreported-table" > </table>
    </div>
		
	</div>


   
	<script>
	var url = "${webRoot}/unreported/findPage";
	$(function () {
		
		/*
		 * 默认导出全部数据文件
		 */
		$('#exportBtn').click(function () {
			var startMonth = $("#startMonth").val();
			var endMonth = $("#endMonth").val();
			var dataSrc = $("#dataSrc").val();
			window.location.href="${webRoot}/unreported/exportDatas?startMonth="+startMonth+"&&endMonth="+endMonth+"&&dataSrc="+dataSrc;
	    });
		
		
		$("#startMonth").datetimepicker({
			 format: "yyyy-mm", 
			 weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN' ,
             maxView:'decade',todayHighlight: true
       });
		
		$("#endMonth").datetimepicker({
			 format: "yyyy-mm", 
			 weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN' ,
             maxView:'decade',todayHighlight: true
        });
		
		/**
		* 主页查询按钮
		*/
		$("#queryBtn").click(function() {
			$('#unreported-table').bootstrapTable( 'refresh');
		});
		
		$("#resetBtn").click(function(){
			  $('.form-control').val('');
		});
		
		initTable();
	});
	
	/**
	 * 去掉双重滚动条
	 */
	function tableHeight() {
		var window_height = $(window).height();
		var obj_off_y = $(".fit-body").offset().top;
		var result_height = window_height - obj_off_y;
		return result_height;
	}
	
	function initTable() {
		$('#unreported-table').bootstrapTable(
		{
			method : 'GET',
			dataType : 'json',
			contentType : "application/json;charset=utf-8",
			pagination : true, //是否显示分页（*）
			cache : false,
			showRefresh : true,
			striped : true, //是否显示行间隔色
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			clickToSelect : true, //是否启用点击选中行
			showToggle : false, //是否显示详细视图和列表视图的切换按钮
			url : url,
			showColumns : true,
			showExport: false,
			height:tableHeight(),
			exportDataType: "basic",
			queryParamsType : "undefined",
			queryParams : function queryParams(params) { //设置查询参数  
				var param = {
					pageSize : params.pageSize,
					pageNumber : params.pageNumber,
					startMonth : $("#startMonth").val(),
					endMonth : $("#endMonth").val(),
					dataSrc : $("#dataSrc").val()
				};
				return param;
			},
			minimumCountColumns : 2,
			pageNumber : pageNumber, //初始化加载第一页，默认第一页
			pageSize : pageIndexSize, //每页的记录行数（*）
			pageList : pageList, //可供选择的每页的行数（*）
			uniqueId : "id" ,//每一行的唯一标识，一般为主键列
			columns :  [
				{field: '', title: '序号', formatter: function (value, row, index) { return index+1; }},
   				{field : 'iouNo', title : '业务编号', align : 'center', valign : 'middle', sortable : true},
       			{field : 'credCode', title : '证件号码', align : 'center', valign : 'middle' }, 
       			{field : 'conNo', title : '合同编号', align : 'center', valign : 'middle'}, 
       			{field : 'coOrgName', title : '所属机构', align : 'center', valign : 'middle'}, 
       			{field : 'repayDate',title : '应报日期',align : 'center',valign : 'middle'},
       			{field : 'dataSrc',title : '数据来源',align : 'center',valign : 'middle'}
    		  ]
		});
	}
	</script>
</body>
</html>