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
<title>信贷信息管理(厂检)_重新生成</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline"  method="post" id="queryForm">
				<div class="form-group">
					<label>开始日期:</label> 
					<input class="form-control contract-date" readonly="readonly" id="reBuildStratDate"name="reBuildStratDate"  /> 
				</div>
				<div class="form-group">
					<label >结束日期:</label> 
					<input class="form-control contract-date" readonly="readonly" id="reBuildEndDate"name="reBuildEndDate"  />
				</div>
				<div class="form-group">
					<label >合作机构:</label> 
					<input type="text" class="form-control"  readonly="readonly" id="coOrgName"name="coOrgName" onclick="selectOrg()" placeholder="请选择"  required />
					<input type="text" class="hidden" name="coOrgCode"  id="coOrgCode">
				</div>
				<div class="form-group btn3">
					<button type="button" id="buildBtn" class="btn btn-primary">生成</button>
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
	</div>
	<script>
		$(function() {
			
			/**
			 * 重置按钮
			 */
			$("#resetBtn").click(function() {
				document.getElementById('queryForm').reset();
			});
			
			
			$("#buildBtn").click(function() {
				var reBuildStratDate = $("#reBuildStratDate").val();
				var reBuildEndDate = $("#reBuildEndDate").val();
				var coOrgCode = $("#coOrgCode").val();
				if(reBuildStratDate==""||reBuildStratDate==null|| 
						reBuildEndDate==""||reBuildEndDate==null||
						coOrgCode==""||coOrgCode==null){
					layer.alert("日期或机构为空");
					return;
				}
				var i ;
				$.ajax({
					type : "post",
					url : "${webRoot}/check/factoryInspectionRebuild",
					data : {
						'reBuildStratDate' : $("#reBuildStratDate").val(),
						'reBuildEndDate' : $("#reBuildEndDate").val(),
						'coOrgCode' : $("#coOrgCode").val()
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

		/**
		 * 机构下拉框
		 */
		function selectOrg(){
			var projectNum;
			var index = parent.layer.open({
				  type: 2,
				  title: '选择机构',
				  content: '${webRoot}/check/orgRebuild',
				  area: ['850px','500px'],
				  btn: ['确定','关闭'],
	              yes: function(index){
	                  projectNum = parent.window["layui-layer-iframe" + index].callbackdata();
	                  var orgCode = "";
	                  var orgName = "";
	                  for(var i=0;i<projectNum.length;i++){
	                	  orgCode += ","+projectNum[i].coOrgCode;
	                	  orgName += ","+projectNum[i].coOrgName;
	                  }
	                  var coOrg = orgCode.substring(1);
	                  var coName = orgName.substring(1);
	                  $("#coOrgCode").val(coOrg);
	                  $("#coOrgName").val(coName);
	                  //最后关闭弹出层
	                  parent.layer.close(index);
	              },
	              cancel: function(){
	              }
			});
			parent.layer.iframeAuto(index);
		}
		
		function initDate() {
			//开始日期
			var start = {
				elem : '#reBuildStratDate',
				format : 'YYYYMMDD',
				min : laydate.now(),
				istoday : true, //是否显示今天
				isclear : true, //是否显示清空
				issure : true //是否显示确认
			};
			//结束日期
			var end = {
				elem : '#reBuildEndDate',
				format : 'YYYYMMDD',
				min : laydate.now(),
				isclear : true, //是否显示清空
				istoday : true, //是否显示今天
				issure : true //是否显示确认
			};
			laydate(start);
			laydate(end);
		}
	</script>
</body>
</html>