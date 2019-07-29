<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>短信配置功能</title>
<style>
.table-striped thead tr:last-of-type {
	visibility: hidden;
}

.table-striped thead tr:last-of-type .th-inner {
	height: 0;
	overflow: hidden;
	padding: 0;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline" method="post" id="queryForm">
				<div class="form-group">
					<label>登记时间:</label> <input placeholder="登记时间"
						class="form-control layer-date" id="createTime" name="createTime">
				</div>
				<div class="form-group">
					<label>类型:</label> <select id="smsModuleTypeId"
						name="smsModuleType" class="form-control">
						<option value="" selected="selected">全部</option>
						<c:forEach items="${smsModuleTypeList}" var="type">
							<option value="${type.dictId}">${type.dictName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group btn2">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container" style="width: 100%">
			<div id="toolbar" class="btn-group group3">
				<div class="btn-group">
					<button class="btn btn-primary" id="addSmsModule">新增</button>
					<button type="button" id="stateBtn" class="btn btn-primary"
						style="    position: fixed;
    right: 143px;">开启</button>
					<button type="button" id="closeBtn" class="btn btn-primary"
						style="    position: fixed;
    right: 80px;">关闭</button>
				</div>
				
			</div>
			<table id="notsubmitdata-table">
			</table>
		</div>
		<div class="container fit-body">
			<table id="smsModule-table">
			</table>
		</div>
	</div>

	<script>
		$(function() {
			initTable();

			$("#queryBtn").click(function() {
				$('#smsModule-table').bootstrapTable('refresh');
			});

			$("#resetBtn").click(function() {
				resetBtn();
			})
			initDate();
		});

		function resetBtn() {
			$("#createTime").val("");
			$("#smsModuleTypeId").val("");
			$('#smsModule-table').bootstrapTable('refresh');
		}
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
				elem : '#createTime',
				format : 'YYYY-MM-DD',
				max : laydate.now(),
				istoday : true, //是否显示今天
				isclear : true, //是否显示清空
				issure : true
			//是否显示确认
			};
			laydate(start);
		}
		/**
		 * 初始化table
		 */
		function initTable() {
			$('#smsModule-table')
					.bootstrapTable(
							{
								url : "${webRoot}/sms/queryModuleList",
								method : 'GET',
								dataType : 'JSON',
								pageNumber : 1,
								pageSize : 10,
								pagination : true,
								height : tableHeight(),
								queryParams : function queryParams(params) {
									var param = {
										pageSize : params.pageSize,
										pageNumber : params.pageNumber,
										createTime : $("#createTime").val(),
										smsModuleType : $("#smsModuleTypeId")
												.val()
									};
									return param;
								},
								columns : [
										[
												{
													field : 'id',
													title : '模板id',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2,
													visible : false
												},
												{
													field : 'smsModuleName',
													title : '模板名称',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2
												},
												{
													field : 'smsModuleTypeName',
													title : '类型',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2
												},
												{
													field : 'smsModuleContent',
													title : '模板内容',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2
												},
												{
													field : 'operator',
													title : '登记人',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2
												},
												{
													field : 'createTime',
													title : '登记时间',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2,
													formatter : function(value) {
														return dateFormatUtil(value)
													}
												},
												{
													field : 'status',
													title : '状态',
													align : 'center',
													valign : 'middle',
													colspan : 1,
													rowspan : 2,
													formatter : function(value) {
														return value == '0' ? "未操作"
																: (value == '1' ? "停用"
																		: "启用")
													}
												}, {
													title : "操作",
													valign : "middle",
													align : "center",
													colspan : 3,
													rowspan : 1
												} ],
										[
												{
													title : "启用/停用",
													valign : "middle",
													align : "center",
													formatter : function(value,
															row, index) {
														var moduleId = row.id
																+ "";
														var status = row.status;
														return "<a href='javascript:void(0)' onclick='updateStatus("
																+ moduleId
																+ ","
																+ status
																+ ")'>启用/停用</a>";
													}
												},
												{
													title : "修改",
													valign : "middle",
													align : "center",
													formatter : function(value,
															row, index) {
														var moduleId = row.id
																+ "";
														return "<a href='javascript:void(0)' onclick='editModule("
																+ moduleId
																+ ")'>修改</a>";
													}
												},
												{
													title : "详情",
													valign : "middle",
													align : "center",
													formatter : function(value,
															row, index) {
														var moduleId = row.id
																+ "";
														return "<a href='javascript:void(0)' onclick='moduleDetail("
																+ moduleId
																+ ")'>详情</a>";
													}
												} ] ]
							});
		}

		//点击启用/停用触发此方法
		function updateStatus(moduleId, status) {
			/* if(status == "2"){
				layer.alert("该模板已经停用,不可再重新启用");
				return;
			} */
			var message = "";
			if (status == "0") {
				message = "启用";
			} else if (status == "1") {
				message = "停用";
			} else if (status == "2") {
				message = "启用";
			}
			var index = layer.confirm("是否确认" + message + "该短信模板？", {
				btn : [ '是', '否' ]
			}, function() {
				updateStatusFunc(moduleId, status, message);
			}, function() {
				layer.close(index);
			});
		}

		//请求后台求改状态
		function updateStatusFunc(moduleId, status, message) {
			$.ajax({
				type : 'post',
				url : '${webRoot}/sms/updateStatus',
				data : {
					'moduleId' : moduleId,
					'status' : status
				},
				beforeSend : function() {
					i = ityzl_SHOW_LOAD_LAYER();
				},
				success : function(data) {
					ityzl_CLOSE_LOAD_LAYER(i);
					if (data.status == 1) {
						parent.layer.alert(message + "成功", {
							icon : 1
						});
					} else {
						parent.layer.alert(message + "失败");
					}
					$('#smsModule-table').bootstrapTable('refresh'); //刷新表格
				},
				error : function() {
					ityzl_CLOSE_LOAD_LAYER(i);
					parent.layer.alert(message + "出错", {
						icon : 2
					});
				},
			});
		}

		function moduleDetail(moduleId) {
			layer.open({
				type : 2,
				area : [ '100%', '100%' ],
				title : '详情',
				content : webRoot + "/sms/smsModuleDetailPage?moduleId="
						+ moduleId
			})
		}

		function editModule(moduleId) {
			editLayerIndex = layer.open({
				type : 2,
				area : [ '100%', '100%' ],
				title : '模板修改',
				content : webRoot + "/sms/toModuleEditPage?moduleId="
						+ moduleId
			})
		}

		$('#addSmsModule').click(function() {
			addLayerIndex = layer.open({
				title : '模板添加',
				area : [ '100%', '100%' ],
				type : 2,
				content : webRoot + "/sms/addSmsModulePage"
			});
		});

		//return "<a onclick='pauseAndResumeJob(\"${webRoot}/qm/pauseJob?jobName="+row.JOBNAME.substring(0, row.JOBNAME.length - 4)+"\")'>暂定</a> | <a onclick='pauseAndResumeJob(\"${webRoot}/qm/resumeJob?jobName="+row.JOBNAME.substring(0, row.JOBNAME.length - 4)+"\")'>恢复</a>"

		$('#stateBtn').click(function(url) {
			var url = "${webRoot}/qm/startJob";
			console.log(url);
			$.ajax({
				type : 'GET',
				url : url,

				success : function(msg) {
					if (msg == "ok") {
						msg = "短信过滤功能已启动"
						$("#stateBtn").attr({
							"disabled" : true
						});
						$("#closeBtn").attr({
							"disabled" : false
						});

					} else {
						msg = "短信功能启动失败"
					}

					layer.alert(msg)
				}
			})
		});
		$('#closeBtn').click(function(url) {
			var url = "${webRoot}/qm/stopJob";
			console.log(url);
			$.ajax({
				type : 'GET',
				url : url,

				success : function(msg) {
					if (msg == "ok") {
						msg = "短信功能过滤已关闭"
						$("#closeBtn").attr({
							"disabled" : true
						});
						$("#stateBtn").attr({
							"disabled" : false
						})
					} else {
						msg = "短信功能关闭失败"
					}

					layer.alert(msg)
				}
			})
		});

		function simpleAjax(url) {
			$.ajax({
				type : 'GET',
				url : url,

				success : function(msg) {
					if (msg == "ok") {
						msg = "短信功能已启动"
					} else {
						msg = "短信功能启动失败"
					}

					layer.alert(msg)
				}
			})
		}

		/**=============对开关进行监控============*/
		$.ajax({

			type : "GET",
			url : '${webRoot}/qm/jobListBtn',
			cache : false,
			dataType : "json",
			success : function(msg) {
				debugger;
				//msg是后台查询返回的数据值  开：PAUSED，关：T
				if (msg.rows[0].JOBGROUP == "push_overduedata_group") {
					var flag = msg.rows[0].STATE
					// 返回信息
					if (flag == '2') { //是否相等
						$("#stateBtn").attr({
							"disabled" : false
						});
						$("#closeBtn").attr({
							"disabled" : true
						});
					} else {
						$("#stateBtn").attr({
							"disabled" : true
						});
						$("#closeBtn").attr({
							"disabled" : false
						});
					}
				}
			}
		});
	</script>
</body>
</html>