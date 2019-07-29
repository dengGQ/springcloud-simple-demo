<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>客户信息管理</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline" role="form" method="post" id="queryForm">

				<div class="form-group">
					<label >客户姓名:</label> 
					<input type="text" class="form-control" name="custName" id="custName" />
				</div>
				
				<div class="form-group">
					<label >证件号码:</label> 
					<input type="text" class="form-control" name="credCode" id="credCode" />
				</div>
				<br/>
				<div class="form-group group1 ">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
				</div>
				<div class="form-group group2">
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container fit-body">
			<table id="custom-table"> </table>
		</div>
	</div>
		
	<script>
	var queryUrl = "${webRoot}/customer/customInfoPage";
	
	$(function() {
		
		initTable();
		/**
		* 主页查询按钮
		*/
		$("#queryBtn").click(function() {
			$('#custom-table').bootstrapTable( 'refresh');
		});
		
		/**
		 * 重置按钮
		 */
		$("#resetBtn").click(function() {
			document.getElementById('queryForm').reset();
		});
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
	
	function doQuery(params) {
		$('#custom-table').bootstrapTable('refresh'); //刷新表格
	}
	
		/**
		 * 初始化table
		 */
		function initTable() {
			$('#custom-table').bootstrapTable({
				url : queryUrl,
				method : 'GET',
				singleSelect : true,//复选框单选
				height:tableHeight(),
				queryParams : function queryParams(params) { 
					var param = {
						pageSize : params.pageSize,
						pageNumber : params.pageNumber,
						custName : $("#custName").val(),
						credCode : $("#credCode").val()
					};
					return param;
				},
				onPostBody:function(){ //表体DOM中可用之后触发
					$("#custom-table tr").attr("title", "双击查看");
				},
				onDblClickRow: function(row, el){
					var index = layer.open({
						title : [ '查看', 'font-size:18px;' ],
						type : 2,
						area : [ '950px', '500px' ],
						content : 'customDetailsPage?credCode='+row.credCode,
						cancel : function() {
						}
					});
					layer.full(index);
				},
				columns : [
						{
							field: '',
							title: '序号', 
							valign : 'left',
							formatter: function (value, row, index) { 
								return index+1; }
						},
						{
							field : 'custName',
							title : '客户姓名',
							align : 'left',
							valign : 'middle'
						}, 
						{
							field : 'credType',
							title : '证件类型',
							align : 'left',
							valign : 'middle',
							formatter: function (value) {
								return getCredType(value);
							}
						}, 
						{
							field : 'credCode',
							title : '证件号',
							align : 'left',
							valign : 'middle',
						}, 
						{
							field : 'custType',
							title : '客户类型',
							align : 'left',
							valign : 'middle',
							formatter: function (value) {
								return getCustType(value);
							}
						},
						{
							field : 'tel',
							title : '联系电话',
							align : 'left',
							valign : 'middle',
						}
					]
			});
		}
	</script>
</body>
</html>