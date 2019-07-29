<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>客户个人信息</title>
<!-- 导入jquery bootstrap js css lib -->
<%@include file="/common/jsp/bootstrap.jsp"%>

</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline" role="form" method="post" id="queryForm">			
				<div class="form-group">
					<label>合同编号:</label> 
					<input type="text" class="form-control" id="conNo" name="conNo"> 
				</div>
				<div class="form-group group1 ">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
				</div>
				<div class="form-group group2">
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container">
			<ul id="myTab" class="nav nav-tabs">
			    <li class="active"><a href="#contractInfoList" data-toggle="tab">合同基本信息</a></li>
			 	<li><a href="#rebuyInfoList" data-toggle="tab">特殊交易查询</a></li>
			   <!--   <li><a href="#repayInfoList" data-toggle="tab">还款信息</a></li> -->
			</ul>
			<div id="myTabContent" class="tab-content">
				 <div class="tab-pane fade" id="rebuyInfoList">
			    	<jsp:include page="./rebuyInfoList.jsp" flush="true" /> 
			    </div>   
			    <div class="tab-pane fade in active" id="contractInfoList">
			    	<jsp:include page="./contractInfoList.jsp" flush="true" />
			    </div>
			   <%--  <div class="tab-pane fade" id="repayInfoList">
			    	<jsp:include page="./repayInfoList.jsp" flush="true" />
			    </div> --%>
			</div>
		</div>
	</div>
	<script>
		 

	</script>
</body>
</html>