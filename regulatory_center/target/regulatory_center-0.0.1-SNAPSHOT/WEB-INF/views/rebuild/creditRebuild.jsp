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
<title>信贷信息管理_重新生成</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline"  method="post" id="queryForm">
				<div class="form-group">
					<label>日期:</label> 
					<input type="text" class="form-control layer-date" readonly="readonly" id="reBuildDate"name="reBuildDate" value="<fmt:formatDate value='${dayBefore}' pattern='yyyyMMdd'/>" /> 
				</div>
				<div class="form-group btn3">
					<button type="button" id="buildBtn" class="btn btn-primary">生成</button>
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container">
			<table></table>
		</div>
	</div>
	<script>
		var dataSrc_formSys = 1;
		$(function() {
			
			/**
			 * 重置按钮
			 */
			$("#resetBtn").click(function() {
				document.getElementById('queryForm').reset();
			});
			
			$("#buildBtn").click(function() {
				var reBuildDate = $("#reBuildDate").val();
				if(reBuildDate == "" || reBuildDate == null){
					layer.alert("日期为空");
					return;
				}
				var i;
				$.ajax({
					type : "post",
					url : "${webRoot}/check/creditRebuild",
					data : {
						'reBuildDate' : $("#reBuildDate").val(),
						'dataSrc' : dataSrc_formSys
					},
					beforeSend: function () {  
			        	i = ityzl_SHOW_LOAD_LAYER();  
			        }, 
					success : function(data) {
						ityzl_CLOSE_LOAD_LAYER(i);  
						if(data.po_status==0){
							layer.alert("生成成功");
						}else{
							layer.alert("生成失败");	
						}
					},
			        error: function () {
			        	ityzl_CLOSE_LOAD_LAYER(i);  
		            },
				});
			});
			initDate();
		});
		
		function initDate() {
			//开始日期
			var start = {
				elem : '#reBuildDate',
				format : 'YYYYMMDD',
				max : laydate.now(),
				istoday : true, //是否显示今天
				isclear : true, //是否显示清空
				issure : true, //是否显示确认
				choose : function(datas) {
					end.min = datas; //开始日选好后，重置结束日的最小日期
					end.start = datas //将结束日的初始值设定为开始日
				}
			};
			laydate(start);
		}
	</script>
</body>
</html>