<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>特殊交易信息</title>
</head>
<body>
	<div class="container-fluid">
		<br/>
		<div class="container">
			<table id="rebuy-table"></table>
		</div>
	</div>
	
	<script>
	var rebuyUrl = "${webRoot}/trademanage/findRebuyPage";
		$(function() {
			
			initRebuyTable();

			$("#resetBtn").click(function() {
				$('.form-control').val('');
			});
			
			
			$("#queryBtn").click(function() {
				$('#rebuy-table').bootstrapTable('refresh');
			});

		});

		function doQuery(params) {
			$('#rebuy-table').bootstrapTable('refresh'); //刷新表格
		}

		function initRebuyTable() {
			$('#rebuy-table').bootstrapTable(
			{
				method : 'GET',
				clickToSelect : true, //是否启用点击选中行
				url : rebuyUrl,
				queryParams : function queryParams(params) { //设置查询参数  
					var param = {
						pageSize : params.pageSize,
						pageNumber : params.pageNumber,
						conNo : $("#conNo").val()
					};
					return param;
				},
				columns : [
					{
						field: '', 
						title: '序号', 
						formatter: function (value, row, index) { 
							return index+1; }
					},{
						field : 'coOrgCode',
						title : '合作机构号',
						align : 'left',
						valign : 'middle',
					}, {
						field : 'coOrgName',
						title : '合作机构名称',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'conNo',
						title : '合同号',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'projId',
						title : '信托项目编号',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'projName',
						title : '信托项目名称',
						align : 'left',
						valign : 'middle',
					}]
			});
		}

		
		function formReset() {
			document.getElementById("queryForm").reset()
		}
		
	</script>
</body>
</html>