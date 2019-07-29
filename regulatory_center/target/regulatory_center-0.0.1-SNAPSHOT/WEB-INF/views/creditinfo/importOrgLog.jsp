<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>机构导入日志</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline"  method="post" id="queryForm">
				<div class="form-group">
					<label>导入日期:</label> 
					<input placeholder="导入日期" class="form-control layer-date" id="loadDate" name="loadDate"> 
				</div>
				
			<!-- 	<div class="form-group">
					<label>校验结果:</label> 
					<select name="checkResult" id="checkResult" class="form-control">
						<option class="form-control"  value="0"  selected="selected">请选择</option>
						<option  value="1">未校验</option>
						<option  value="2">校验通过</option>
						<option  value="3">校验失败</option>
					</select>
				</div> -->
				<div class="form-group btn2">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container fit-body">
			<table id="importOrgLog-table"> </table>
		</div>
	</div>
		
<script>
	$(function() {
		initTable();
		
		$("#queryBtn").click(function() {
			$('#importOrgLog-table').bootstrapTable( 'refresh');
		});
		
		$("#resetBtn").click(function(){
			$("#loadDate").val("");
			$('#importOrgLog-table').bootstrapTable( 'refresh');
		})
		initDate();
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
	/**
	 * 初始化日期
	 */
	function initDate() {
		//开始日期
		var start = {
			elem : '#loadDate',
			format : 'YYYY-MM-DD',
			max : laydate.now(),
			istoday : true, //是否显示今天
			isclear : true, //是否显示清空
			issure : true //是否显示确认
		};
		laydate(start);
	}
	/**
	 * 初始化table
	 */
	function initTable() {
		$('#importOrgLog-table').bootstrapTable({
			url : webRoot+"/creditinfo/importOrgLogList",
			method : 'GET',
			dataType: 'JSON',
			pageNumber:1,
			pageSize:10,
			height:tableHeight(),
			queryParams : function queryParams(params) {
				var param = {
					pageSize : params.pageSize,
					pageNumber : params.pageNumber,
					loadDate: $("#loadDate").val()
				};
				return param;
			},
			columns: [{
		            field : 'fifldName',
		            title : '文件名称',
		            align : 'center',
		            valign : 'middle',
		            sortable : true
				},{
					field : 'name',
		            title : '操作人',
		            align : 'center',
		            valign : 'middle',
		            sortable : true
				},{
					field : 'loadDate',
		            title : '导入时间',
		            align : 'center',
		            valign : 'middle',
		            sortable : true
				},/* {
					field : 'state',
		            title : '状态',
		            align : 'center',
		            valign : 'middle',
		            sortable : true,
		            formatter: function(v){
		            	return
		            }
				}, */{
					field : 'importType',
		            title : '导入方式',
		            align : 'center',
		            valign : 'middle',
		            sortable : true,
		            formatter:function(v){
		            	if(v == "0"){
		            		return "单文件导入";
		            	}else if(v == "1"){
		            		return "批量导入";
		            	}else{
		            		return "单文件导入";
		            	}
		            }
				}
			]
		});
	}
	</script>
</body>
</html>