<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>定时器管理</title>
</head>
<body style="margin: 15px 15px">
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<%-- <button onclick="pauseAndResumeJob('${webRoot}/qm/pauseJob?jobName=push_overduedata')">暂定数据推送任务</button>
			<button onclick="pauseAndResumeJob('${webRoot}/qm/resumeJob?jobName=push_overduedata')">恢复数据推送任务</button> --%>
		
		
			<%-- <button onclick="startOrShutdiwnSchedule('${webRoot}/qm/shutdown')">停止定时器</button>
		
			<button onclick="pauseAndResumeJob('${webRoot}/qm/pauseJob?jobName=sms_send')">暂定短信发送任务</button>
			
			<button onclick="pauseAndResumeJob('${webRoot}/qm/resumeJob?jobName=sms_send')">恢复短信发送任务</button>  --%>
		</div>
		<div class="container fit-body">
			<div id="toolbar" class="btn-group group3">
				<div class="btn-group">
					<%-- <button class="btn btn-primary" onclick="startOrShutdiwnSchedule('${webRoot}/qm/startSchedule')">启动定时器</button> --%>
			    </div>
			</div>
			<table id="job-table"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			initContractTable();
		})
		
		function initContractTable() {
			$('#job-table').bootstrapTable({
				method : 'GET',
				url : '${webRoot}/qm/jobList',
				dataType: 'JSON',
				pageNumber:1,
				pageSize:10,
				pagination: true,
				height:tableHeight(),
				queryParams : function queryParams(params) {
					var param = {
						pageSize : params.pageSize,
						pageNumber : params.pageNumber
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
						field : 'SCHEDNAME',
						title : '调度器',
						align : 'left',
						valign : 'middle',
					},{
						field : 'JOBNAME',
						title : '任务名称',
						align : 'left',
						valign : 'middle',
						formatter : function(value){
							return value.substring(0, value.length - 4)
						}
					},  {
						field : 'JOBGROUP',
						title : '任务组',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'JOBCLASSNAME',
						title : '执行任务类',
						align : 'left',
						valign : 'middle'
					}, {
						field : 'TRIGGERSTATE',
						title : '触发器状态',
						align : 'left',
						valign : 'middle',
						formatter: function (value) {
							return value;
						}
					}, {
						field : 'CRONEXPRESSION',
						title : '触发规则',
						align : 'left',
						valign : 'middle'
					},{
						title : '操作',
						align : 'left',
						valign : 'middle',
						formatter : function (value,row,index) {
							
							return "<a onclick='pauseAndResumeJob(\"${webRoot}/qm/pauseJob?jobName="+row.JOBNAME.substring(0, row.JOBNAME.length - 4)+"\")'>暂定</a> | <a onclick='pauseAndResumeJob(\"${webRoot}/qm/resumeJob?jobName="+row.JOBNAME.substring(0, row.JOBNAME.length - 4)+"\")'>恢复</a>"
						}
					}]
			});
		}
		
		function startOrShutdiwnSchedule(url){
			simpleAjax(url);
		}
		
		function pauseAndResumeJob(url){
			console.log(url);
			simpleAjax(url);
		}
		
		function simpleAjax(url){
			$.ajax({
				type: 'GET',
				url: url,
				success: function(msg){
					layer.alert(msg)
				}
			})
		}
		
		function tableHeight() {
			var window_height = $(window).height();
			var obj_off_y = $(".fit-body").offset().top;
			var result_height = window_height - obj_off_y;
			return result_height;
		}
	</script>
</body>
</html>