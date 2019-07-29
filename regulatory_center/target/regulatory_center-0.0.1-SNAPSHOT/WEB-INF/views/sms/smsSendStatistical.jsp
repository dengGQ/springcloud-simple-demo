<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>短信发送统计台账</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline" role="form" method="post" id="queryForm" action = "${webRoot}/sms/exportSmsSendStatistical">			
				<div class="form-group">
					<label>合作机构:</label> 
					<input type="hidden" class="form-control" name="coOrgCode" id="coOrgCode" /> 
					<input type="text" readonly="readonly" class="form-control" name="coOrgName" id="coOrgName"   onclick="selectCoOrg()"  placeholder="请选择"/>
				</div>
				<div class="form-group">
					<label>项目名称:</label> 
					<input type="hidden" class="form-control" name="projId" id="projId" /> 
                	<input type="text" readonly="readonly" class="form-control" name="projName" id="projName"   onclick="selectProject()"  placeholder="请选择"/>
				</div>
				<div class="form-group">
					<label>产品名称:</label> 
					<input  type="hidden" class="form-control"   name="prodCode" id="prodCode" >
                	<input type="text" readonly="readonly" class="form-control" name="prodName" id="prodName"   onclick="selectProduct()"  placeholder="请选择"/>
				</div>
				<div class="form-group">
	                <label>开始时间</label> 
	            	<input placeholder="开始时间" class="form-control layer-date" id="startDate" name="startDate"> 
					<input type="hidden" id="dtp_input1" value="" /><br/>
	            </div>
	            <div class="form-group">
	                <label for="endMonth">结束时间</label> 
	                <input placeholder="结束时间" class="form-control layer-date" id="endDate" name="endDate"> 
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
			<div id="toolbar" class="btn-group group3">
				<div class="btn-group">
			       <button class="btn btn-primary" id="export" onclick="exportSmsSendStatistical()">导出</button>
			    </div>
			</div>
			<table id="smsSendStatistical-table"></table>
		</div>
	</div>
	
	<script>
	var url = "${webRoot}/sms/querySmsSendStatistical";
		$(function() {
			smsSendStatisticalTable();

			$("#resetBtn").click(function() {
				$('.form-control').val('');
			});
			
			
			$("#queryBtn").click(function() {
				$('#smsSendStatistical-table').bootstrapTable('refresh');
			});
			
			//初始化日期
			initDate();

		});
		
		
		
		function tableHeight() {
			var window_height = $(window).height();
			var obj_off_y = $(".fit-body").offset().top;
			var result_height = window_height - obj_off_y;
			return result_height;
		}
		
		function smsSendStatisticalTable() {
			$('#smsSendStatistical-table').bootstrapTable(
			{
				method : 'GET',
				clickToSelect : true, //是否启用点击选中行
				singleSelect : true,//复选框单选
				height:tableHeight(),
				pagination:true,
				pageNumber:1,
				pageSize:10,
				url : url,
				queryParams : function queryParams(params) { //设置查询参数  
					var param = {
							pageSize : params.pageSize,
							pageNumber : params.pageNumber,
							startMonthStr : $("#startDate").val(),
							endMonthStr : $("#endDate").val(),
							coOrgCode : $("#coOrgCode").val(),
							projId : $("#projId").val(),
							prodCode : $("#prodCode").val()
					};
					return param;
				},
				columns : [
					{
						field:'id',
						visible:false
					},{
						field : 'sendMonthStr',
						title : '日期',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'coOrgName',
						title : '合作机构',
						align : 'center',
						valign : 'middle',
					}, {
						field : 'projName',
						title : '项目名称',
						align : 'center',
						valign : 'middle',
					}, {
						field : 'prodName',
						title : '产品名称',
						align : 'center',
						valign : 'middle',
					}, {
						field : 'sendTotalNum',
						title : '发送总条数',
						align : 'center',
						valign : 'middle',
					}, {
						field : 'sendSuccessNum',
						title : '发送成功条数',
						align : 'center',
						valign : 'middle',
					},  {
						field : 'sendFailNum',
						title : '发送失败条数',
						align : 'center',
						valign : 'middle'
					}]
			});
		}

		
		function formReset() {
			document.getElementById("queryForm").reset()
		}
		
		//选择合作机构
		function selectCoOrg(){
			var projectNum;
			var index = layer.open({
				  type: 2,
				  title: '选择机构',
				  maxmin: true,
				  content: '${webRoot}/publicquery/orgNameAndCode',
				  area: ['1000px', '500px'],
				  btn: ['确定','关闭'],
	              yes: function(index){
	                  //当点击‘确定’按钮的时候，获取弹出层返回的值
	                  projectNum = window["layui-layer-iframe" + index].callbackdata();
	                  //打印返回的值，看是否有我们想返回的值。
				      if (projectNum == null || projectNum == undefined || projectNum == '') { 
				    	//layer.alert("未选择对象");
				    	return false;
				      }
	                  $("#coOrgCode").val(projectNum[0]);
	                  $("#coOrgName").val(projectNum[1]);
	                  //最后关闭弹出层
	                  layer.close(index);
	              },
	              cancel: function(){
	                  //右上角关闭回调
	              }
				  
				});
		}
		
		function selectProject(){
			var projectNum;
			var index = layer.open({
				  type: 2,
				  title: '选择信托项目',
				  maxmin: true,
				  content: '${webRoot}/publicquery/projectNameAndId',
				  area: ['1000px', '500px'],
				  btn: ['确定','关闭'],
	              yes: function(index){
	                  //当点击‘确定’按钮的时候，获取弹出层返回的值
	                  projectNum = window["layui-layer-iframe" + index].callbackdata();
	                  //打印返回的值，看是否有我们想返回的值。
	                  $("#projId").val(projectNum[0]);
	                  $("#projName").val(projectNum[1]);
	                  //最后关闭弹出层
	                  layer.close(index);
	              },
	              cancel: function(){
	                  //右上角关闭回调
	              }
				  
				});
		}
		
		function selectProduct(){
			var projectNum;
			var index = layer.open({
				  type: 2,
				  title: '选择产品',
				  maxmin: true,
				  content: '${webRoot}/publicquery/productAndOrg',
				  area: ['1000px', '500px'],
				  btn: ['确定','关闭'],
	              yes: function(index){
	                  //当点击‘确定’按钮的时候，获取弹出层返回的值
	                  projectNum = window["layui-layer-iframe" + index].callbackdata();
	                  $("#prodCode").val(projectNum[0]);
	                  $("#prodName").val(projectNum[1]);
	                  //最后关闭弹出层
	                  layer.close(index);
	              },
	              cancel: function(){
	                  //右上角关闭回调
	              }
				  
				});		
		}
		
		//导出短信发送统计台账
		function exportSmsSendStatistical(){
			$("#queryForm").submit();
		}
		
		
		
		function initDate() {
			//开始日期
			var start = {
				elem : '#startDate',
				format : 'YYYYMM',
				max : '999912',
				istoday : true, //是否显示今天
				isclear : true, //是否显示清空
				issure : true, //是否显示确认
				choose : function(datas) {
					end.min = datas; //开始日选好后，重置结束日的最小日期
					end.start = datas //将结束日的初始值设定为开始日
				}
			};
			//结束日期
			var end = {
				elem : '#endDate',
				format : 'YYYYMM',
				max : '999912',
				isclear : true, //是否显示清空
				istoday : true, //是否显示今天
				issure : true, //是否显示确认
				choose : function(datas) {
					start.max = datas; //结束日选好后，重置开始日的最大日期
				}
			};
			laydate(start);
			laydate(end);
		}
		
	</script>
</body>
</html>