<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>合同基本信息</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline" role="form" method="post" id="queryForm">			
				<div class="form-group">
					<label>合同编号:</label> 
					<input type="text" class="form-control" id="conNo" name="conNo"> 
				</div>
				<div class="form-group">
					<label>业务号:</label> 
					<input type="text" class="form-control" id="iouNo" name="iouNo"> 
				</div>
				<div class="form-group">
					<label >数据来源:</label> 
					 <select id="dataSrc" name="dataSrc" class="form-control">
                            <option value="1" selected="selected">信贷</option>
                            <option value="2">CSV</option>
                        </select>
				</div>
				<div class="form-group group1 ">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
				</div>
				<div class="form-group group2">
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container fit-body">
			<table id="contract-table"></table>
		</div>
	</div>
	
	<script>
	var contractUrl = "${webRoot}/trademanage/findContractPage";
		$(function() {
			initContractTable();

			$("#resetBtn").click(function() {
				$('.form-control').val('');
			});
			
			
			$("#queryBtn").click(function() {
				$('#contract-table').bootstrapTable('refresh');
			});

		});
		
		function doQuery(params) {
			$('#contract-table').bootstrapTable('refresh'); //刷新表格
		}
		
		function tableHeight() {
			var window_height = $(window).height();
			var obj_off_y = $(".fit-body").offset().top;
			var result_height = window_height - obj_off_y;
			return result_height;
		}
		
		function initContractTable() {
			$('#contract-table').bootstrapTable(
			{
				method : 'GET',
				clickToSelect : true, //是否启用点击选中行
				singleSelect : true,//复选框单选
				height:tableHeight(),
				url : contractUrl,
				queryParams : function queryParams(params) { //设置查询参数  
					var param = {
						pageSize : params.pageSize,
						pageNumber : params.pageNumber,
						conNo : $("#conNo").val(),
						iouNo : $('#iouNo').val(),
						dataSrc : $('#dataSrc').val()
					};
					return param;
				},
				onDblClickRow: function(row, el){
					var index = layer.open({
						title : [ '查看', 'font-size:18px;' ],
						type : 2,
						maxmin: true,
						area : [ '1000px', '500px' ],
						content : 'findDetailsContract?conNo='+row.conNo,
						cancel : function() {
						}
					});
					layer.full(index);
				},
				onPostBody:function(){ //表体DOM中可用之后触发
					$("#contract-table tr").attr("title", "双击查看");
				},
				columns : [
					{
						field: '', 
						title: '序号', 
						formatter: function (value, row, index) { 
							return index+1; }
					},{
						field : 'conNo',
						title : '合同号',
						align : 'left',
						valign : 'middle',
					},{
						field : 'iouNo',
						title : '业务号',
						align : 'left',
						valign : 'middle',
					},  {
						field : 'orgName',
						title : '合作机构',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'credType',
						title : '证件类型',
						align : 'left',
						valign : 'middle',
						formatter: function (value) {
							return getCredType(value);
						}
					}, {
						field : 'credCode',
						title : '证件号码',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'conStartDate',
						title : '开始日期',
						align : 'left',
						valign : 'middle',
						formatter: function (value) {
							return dateFormatUtil(value);
						}
					}, {
						field : 'conEndDate',
						title : '结束日期',
						align : 'left',
						valign : 'middle',
						formatter: function (value) {
							return dateFormatUtil(value);
						}
					}, {
						field : 'loanTerm',
						title : '贷款期限',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'dataSrc',
						title : '数据来源',
						align : 'left',
						valign : 'middle'
					}]
			});
		}

		
		function formReset() {
			document.getElementById("queryForm").reset()
		}
		
	</script>
</body>
</html>