<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>自检</title>
</head>
<body>

	<div class="container-fluid">
			<div class="row base-margin" id="query">
	        <form class="form-inline">
	            <div class="form-group">
	                <label>开始日期</label> 
	            	<input placeholder="开始日期" class="form-control form_datetime" id="startDate" name="startDate"> 
	            </div>
	            <div class="form-group">
	                <label>结束日期</label> 
	                <input placeholder="截止月份" class="form-control form_datetime" id="endDate" name="endDate"> 
	            </div>
	            <div class="form-group btn2">
	                <button type="button" id="resetBtn"  class="btn btn-primary">重置</button>
	            </div>
	        </form>
	    </div>
    <div class="container" style="width: 100%">
    	<div id="toolbar" class="btn-group group3">
			 <div class="btn-group">
			       <button class="btn btn-primary" id="buildBtn">生成</button>
			       <button class="btn btn-primary" id="upReportBtn">上报</button>
			 </div>
		</div>
    </div>
	</div>


   
	<script>
	$(function () {
		$("#startDate").datetimepicker({
			 format: "yyyy-mm-dd", 
			 weekStart: 1,  
	         autoclose: true,  
	         startView: 2,  
	         minView: 2,  
	         forceParse: false,  
	         language: 'zh-CN' ,
             maxView:'decade',todayHighlight: true
       });
		
		$("#endDate").datetimepicker({
			 format: "yyyy-mm-dd", 
			 weekStart: 1,  
	         autoclose: true,  
	         startView: 2,  
	         minView: 2,  
	         forceParse: false,  
	         language: 'zh-CN' ,
             maxView:'decade',todayHighlight: true
        });
		
		$("#resetBtn").click(function(){
			  $('.form-control').val('');
		});
		
	});
	</script>
</body>
</html>