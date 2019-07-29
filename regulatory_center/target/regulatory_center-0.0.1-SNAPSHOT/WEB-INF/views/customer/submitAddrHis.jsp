<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>客户家庭住址信息_历史</title>

</head>
<body>
	<div class="container-fluid">
		<br/>
		<div class="container fit-body">
			<table id="addrHis-table">
			</table>
		</div>
	</div>
		
	<script>
		var data_status = "2";//默认查询校验通过的信息
		var addrHisTableUrl = "${webRoot}/customer/addrHisPage?dataStatus="+data_status;
		$(function() {
			$("#resetBtn").click(function(){
				  $('.form-control').val('');
			});
			
			$("#queryBtn").click(function(){
				$('#addrHis-table').bootstrapTable('refresh');
			});
			
			initAddrHisTable();
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
		
		function initAddrHisTable() {
			$('#addrHis-table').bootstrapTable(
			{
				method : 'GET',
				url : addrHisTableUrl,
				height:tableHeight(),
				showRefresh : true,
				showColumns : true,
				pageNumber : pageNumber, //初始化加载第一页，默认第一页
				pageSize : pageIndexSize, //每页的记录行数（*）
				pageList : pageList, //可供选择的每页的行数（*）
				queryParams : function queryParams(params) { //设置查询参数  
					var param = {
						pageSize : params.pageSize,
						pageNumber : params.pageNumber,
						certNo : $("#certNo").val(),
						name : $("#name").val()
					};
					return param;
				},
				columns : [
					{field: '', title: '序号', formatter: function (value, row, index) { return index+1; }},
       				{field : 'name', title : '姓名', align : 'center', valign : 'middle', sortable : true},
	       			{field : 'certType', title : '证件类型', align : 'center', valign : 'middle' , formatter: function (value) {return getCredType(value);}}, 
	       			{field : 'certNo', title : '证件号码', align : 'center', valign : 'middle'}, 
	       			{field : 'deptCode', title : '数据发生机构代码', align : 'center', valign : 'middle'}, 
	       			{field : 'residence', title : '居住地址', align : 'center', valign : 'middle'}, 
	       			{field : 'resZip', title : '居住地址邮编', align : 'center', valign : 'middle'}, 
	       			{field : 'resCondition', title : '居住状况', align : 'center', valign : 'middle',formatter: function (value) {return getLivStatus(value);}}, 
	       			{field : 'bussDate', title : '数据业务日期', align : 'center', valign : 'middle', formatter: function (value) {return dateFormatUtil(value);}}, 
	       			{field : 'dataStatus', title : '数据检验状态', align : 'center', valign : 'middle', formatter: function (value) {return getValidateStatus(value)}}, 
	       			{field : 'insertDttm', title : '数据生成时间', align : 'center', valign : 'middle', formatter: function (value) {return dateFormatUtil(value);}}, 
	       			{field : 'dataSrc',title : '数据来源',align : 'center',valign : 'middle', formatter: function (value) {return getDataSrc(value)}}
        		  ]
			});
		}

	</script>
</body>
</html>