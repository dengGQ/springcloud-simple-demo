<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>短信内容</title>
<style>
   .pagination-detail,.fixed-table-toolbar{display:none}
</style>
</head>
<body>
	<div class="container-fluid">
	    <div class="title_header">
			   <span class="title_header_left">短信内容</span>
			   <span class="title_header_right"><button type="button" onclick="submitSms()" class="btn btn-primary">提交</button></span> 
			</div>
		<div class="row base-margin" id="query">
			<form class="form-inline form_style" role="form" method="post" id="queryForm">			
				<input type="hidden" id="detailId" name="detailId" value="${detailId}"/>
				<div class="row">
				<div class="col-md-6 text_center">
					<div class="form-group">
						<label>客户姓名:</label> 
						<input type="text" class="hidden" name="custCode" id="custCode" /> 
						<input type="text"  class="form-control" name="custName" id="custName"/>
					</div>
				</div>
				<div class="col-md-6 text_center">
					<div class="form-group">
						<label>联系电话:</label> 
	                	<input type="text" class="form-control" name="phone" id="phone" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</div>
				</div>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-md-12">
				<p class="du_p1">类型:
				  <input type="text"  id="smsModuleTypeId" disabled="disabled"/>
                     <input type="hidden"  id="smsModuleId" />
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
					<p class="du_p1">短信内容:</p>
					<textarea style="line-height: 16px;" cols="100" rows="5" class="form-control" id="smsSendContent" name="smsSendContent"></textarea>
			</div>
		</div>
		<div>
			<input type="hidden" id="coOrgCode" name="coOrgCode"/>
			<input type="hidden" id="projId" name="projId"/>
			<input type="hidden" id="prodCode" name="prodCode"/>
			<input type="hidden" id="credType" name="credType"/>
			<input type="hidden" id="credNo" name="credNo"/>
			<input type="hidden" id="iouNo" name="iouNo"/>
		</div>
		<div class="container fit-body">
		    <p class="du_p2">发送记录：</p>
			<table id="sendRecord-table"></table>
		</div>
	</div>
	
	<script>
	var url = "${webRoot}/sms/querySmsSendDetailById";
		$(function() {
			sendRecordTable();
		});
		
		function tableHeight() {
			var window_height = $(window).height();
			var obj_off_y = $(".fit-body").offset().top;
			var result_height = window_height - obj_off_y;
			return result_height;
		}
		
		function sendRecordTable() {
			$('#sendRecord-table').bootstrapTable(
			{
				method : 'GET',
				clickToSelect : true, //是否启用点击选中行
				singleSelect : true,//复选框单选
				height:tableHeight(),
				url : url,
				queryParams : function queryParams(params) { //设置查询参数  
					var param = {
							id : $("#detailId").val()
					};
					return param;
				},
				columns : [
					{
						title : '序号',
						align : 'center',
						valign : 'middle',
						formatter: function (value, row, index) {
							$("#custCode").val(row.custCode);
							$("#custName").val(row.custName);
							$("#phone").val(row.phone);
							$("#smsSendContent").val(row.smsSendContent);
							$("#coOrgCode").val(row.coOrgCode);
							$("#projId").val(row.projId);
							$("#prodCode").val(row.prodCode);
							$("#credType").val(row.credType);
							$("#credNo").val(row.credNo);
							$("#iouNo").val(row.iouNo);
							$("#smsModuleTypeId").val(row.smsModuleTypeName);
							//模版类型不支持编辑短信内容
							if(row.smsModuleType==2){
								$('#smsSendContent').attr('disabled','disabled');
							}
							$("#smsModuleId").val(row.moduleId);
							return index+1;  
						}
					},{
						field:'coOrgCode',
						title:'合作机构编码',
						align : 'center',
						valign : 'middle',
						visible:false
					},{
						field:'projId',
						title:'项目编码',
						align : 'center',
						valign : 'middle',
						visible:false
					},{
						field:'prodCode',
						title:'产品编码',
						align : 'center',
						valign : 'middle',
						visible:false
					}, {
						field : 'sendDateStr',
						title : '发送时间',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'sendStatus',
						title : '短信状态',
						align : 'center',
						valign : 'middle',
						formatter:function(value, row, index){
							var code = row.smsCode;
							if(code=='00'){
								return '成功';
							}else{
								return '失败';
							}
						}
					}, {
						field : 'smsCodeDetail',
						title : '原因说明',
						align : 'center',
						valign : 'middle',
					}]
			});
		}
		
		//提交、手工发送短信
		function submitSms(){
			//获取客户姓名、联系电话、短信内容
			var custCode = $("#custCode").val();
			var custName = $("#custName").val();
			var phone = $("#phone").val();
			var smsSendContent = $("#smsSendContent").val();
			var coOrgCode = $("#coOrgCode").val();
			var projId = $("#projId").val();
			var prodCode = $("#prodCode").val();
			var credType = $("#credType").val();
			var credNo = $("#credNo").val();
			var iouNo = $("#iouNo").val();
			var smsModuleId = $("#smsModuleId").val();
			var smsModuleTypeName = $("#smsModuleTypeId").val();
			if(custName == null || custName == ''){
				alert("请输入客户姓名");
				return;
			}
			if(phone == null || phone == ''){
				alert("请输入手机号");
				return;
			}else{
				if(phone.length != 11){
					alert("请输入有效的手机号");
					return;
				}
			}
			if(smsSendContent == null || smsSendContent == ''){
				alert("请输入短信内容");
				return;
			}else{
				if(smsSendContent.length>350){
					alert("短信内容不能超过350个字符");
					return;
				}
			}
			var index = layer.confirm("是否确认提交本次短信发送内容信息？", {
				  btn: ['确认','取消'] 
			},function(){
				sendSms(custCode,custName,phone,smsSendContent,coOrgCode,projId,prodCode,credType,credNo,iouNo,smsModuleId,smsModuleTypeName);
			},function(){
				layer.close(index);
			});
		}
		
		function sendSms(custCode,custName,phone,smsSendContent,coOrgCode,projId,prodCode,credType,credNo,iouNo,smsModuleId,smsModuleTypeName){
			$.ajax({
				type : 'post',
				url : '${webRoot}/sms/manulSendSms',
				data : { 'custCode' : custCode ,'custName':custName,'phone':phone,'smsSendContent':smsSendContent,'coOrgCode':coOrgCode,'projId':projId,'prodCode':prodCode,'credType':credType,'credNo':credNo,'iouNo':iouNo,'moduleId':smsModuleId,'smsModuleTypeName':smsModuleTypeName},
				beforeSend: function () {  
		        	i = ityzl_SHOW_LOAD_LAYER();  
		    	}, 
				success : function(data) {
					ityzl_CLOSE_LOAD_LAYER(i); 
					if (data.smsCode == '00') {
						parent.layer.alert("成功",{icon: 1});
						//跳转到列表页面
						window.location.href = "${webRoot}/sms/smsSendDetailList";
					}else if(data.smsCode == '-1'){
						parent.layer.alert("请输入有效的手机号");
					} else {
						parent.layer.alert("失败,原因："+data.smsCodeDetail);
					}
				},
				error : function() {
					ityzl_CLOSE_LOAD_LAYER(i);  
					parent.layer.alert("发送短信出错",{icon: 2});
				},
			});
		}
	</script>
</body>
</html>