<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>信贷信息管理(厂检)-重新生成-机构查询</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
		<form class="form-inline"   method="post"  id="queryForm">
            <div class="form-group">
                <label for="orgName">机构名称 :</label> 
                <input type="text"  class="form-control" name="orgName" id="input_orgName" >
            </div>
            <div class="form-group">
                <button type="button" id="queryBtn" class="btn btn-primary">查询</button>
            </div>
        </form>
    </div>
    <div class="container fit-body">
        <table id="org-table"> </table>
    </div>
	</div>
<script>
	var table;
	$(function () {
		/**
		 * 查询
		 */
		$("#queryBtn").click(function(){
			$('#org-table').bootstrapTable('refresh');
		});
	    initTable();
	});
	
	function tableHeight() {
		var window_height = $(window).height();
		var obj_off_y = $(".fit-body").offset().top;
		var result_height = window_height - obj_off_y;
		return result_height;
		
	}

	function initTable(){
	    var url = "${webRoot}/publicquery/orgSearch";
	        table = $('#org-table').bootstrapTable({
	        method:'GET',
	        height:tableHeight(),
	        clickToSelect: true,                
	        url:url,
	        singleSelect    : false, 
	        queryParams: function queryParams(params) { 
	              var param = {    
	            		  pageSize: params.pageSize,
	            		  pageNumber: params.pageNumber, 
	            		  orgName : $("#input_orgName").val()
	              };    
	              return param;                   
	        }, 
	        pageNumber:pageNumber, 
	        pageSize: pageSecondSize, 
	        pageList: pageList,                 
	        columns: [{
	        	checkbox: true  
			}, {
	            field : 'coOrgCode',
	            visible :false
	        },{
	            field : 'coOrgName',
	            title : '机构名称',
	            align : 'left',
	            width: '300px',
	            valign : 'middle',
	            sortable : true
	        }]
	    });
	}
	
	function callbackdata(){
		var rows=table.bootstrapTable('getSelections');
		var arr = [];
		if (rows == null || rows == undefined || rows == '') { 
    		layer.alert("未选择对象");
    		return ;
    	}else{
			for (var i = 0; i < rows.length; i++) {
				var paramsListB = {};
				paramsListB.coOrgCode = rows[i].coOrgCode;//实际还款表ID
				paramsListB.coOrgName = rows[i].coOrgName;//合同号
				arr.push(paramsListB);
			}
    		return arr;
    	}
	}

</script>
</body>
</html>