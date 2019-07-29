<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<meta>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>自检数据管理</title>
</head>
<body>
	<div class="container-fluid">
			<div class="row base-margin" id="query">
	        <form class="form-inline"  role="form"  method="post"  id="queryForm">
	        	<div class="form-group">
	            	<label>年份:</label>
	            	<input placeholder="年份" class="input-append date form-control" id="year" name="year"> 
	            </div>
	            <%-- <div class="form-group">
	                <label for="quarter" class="control-label">季度:</label>
	                <select name="quarter" id="quarter" class="form-control">
	                	<option value="0" selected="selected">请选择</option>
	                	<c:forEach items="${list}" var="info">
	                		<option value="${info.quatr}">${info.quatr}</option>
	                	</c:forEach>
					</select> 
	            </div> --%>
	            <div class="form-group btn2">
	                <button type="button" id="queryBtn" class="btn btn-primary">查询</button>
	                <button type="button" id="resetBtn"  class="btn btn-primary">重置</button>
	            </div>
	        </form>
	    </div>
    <div class="container fit-body">
      <div class="fixed-table-toolbar">
         <div class="bs-bars pull-left">
         
        
	    <div id="toolbar" class="btn-group group3">
	       <div class="btn-group">
	        <button id="exportBtn" class="btn btn-primary " >导出</button>
	       </div>
	    </div>
	     </div>
      </div>
        <table id="dataGrid" ></table>
    </div>
	</div>

   
	<script>
	var url = "${webRoot}/datacheck/queryDataCheck";
	$(function () {
		
		 $exportBtn = $('#exportBtn'),
	     selections = [];
		 dataGrid = $('#dataGrid')
		 
		 /*
		 导出数据文件
		 */
		 $exportBtn.click(function () {
	            var quatr = getIdSelections();
	            if(quatr==""){
	            	layer.msg("请勾选要导出的数据",{icon: 2})
	            	return false;
	            }
	            window.location.href="${webRoot}/datacheck/exportSelfCheckDataFile?quatr="+quatr;
	        });
		 
		 function getIdSelections() {
		        var rows = $('#dataGrid').bootstrapTable('getSelections');
		        var quatr = "";
		        for(var i=0;i<rows.length;i++){
		        	if(""==quatr){
		        		quatr = "'"+rows[i].quatr+"'" ;
		        	}else{
		        		quatr = quatr +",'" + rows[i].quatr+"'" ;
		        	}
		        }
		     return   quatr;
		 }
		
		$("#resetBtn").click(function(){
			  $('.form-control').val('');
		});
		
		$("#queryBtn").click(function(){
			var param = {
		        url: url,
		        query:{
		       	  	pageNumber:pageNumber, 
			 	  	pageSize: pageIndexSize,
			 	  	quarter : $("#quarter").val(),
			 	  	year : $("#year").val()
		        }      
			} 
			 $('#dataGrid').bootstrapTable('refresh',  param );
		});
		
		$("#year").datetimepicker({
			 format: 'yyyy',  
	         weekStart: 1,  
	         autoclose: true,  
	         startView: 4,  
	         minView: 4,  
	         forceParse: false,  
	         language: 'zh-CN'  
        });
		
		initTable();
	});

	/*获取表格高度，根据浏览器自适应*/
	/* function getHeight() {
        return $(window).height() - 95;
    } 
	
	$(window).resize(function () {
		 $('#dataGrid').bootstrapTable('resetView', {
            height : getHeight()
        });
    });*/
	
    /**
	 * 去掉双重滚动条
	 */
	function tableHeight() {
		var window_height = $(window).height();
		var obj_off_y = $(".fit-body").offset().top;
		var result_height = window_height - obj_off_y;
		return result_height;
	}
    
    
	function initTable(){
	    $('#dataGrid').bootstrapTable({
	        method:'GET',
	        dataType:'json',
	        contentType: "application/json;charset=utf-8",
	        striped: true,  
	        pagination: true,  
	        //height : getHeight(),
	        sidePagination: "server",          
	        url:url,
	        pageNumber:pageNumber,                     
	        pageSize: pageIndexSize,                      
	        pageList: pageList,   
	    	height:tableHeight(),
	        uniqueId: "id",                   
	        queryParamsType : "undefined",
	        queryParams: function queryParams(params) { 
	              var param = {    
	            		  	pageSize: params.pageSize,
	            		  	pageNumber: params.pageNumber, 
	            		  	year : $("#year").val()
	              };    
	              return param;                   
	            }, 
	        columns: [{
                field: 'state',
                checkbox: true,
                align: 'center'
            },{
	            field : 'quatr',
	            title : '季度',
	            align : 'left',
	            width : 120,
	            sortable : true
	        }, {
	            field : 'fileName',
	            title : '文件名称',
	            width : 300,
	            align : 'left'
	        }, {
	            field : 'bussDate',
	            title : '数据日期',
	            width : 100,
	            align : 'left',
	            sortable : true
	        }]
	    });
	}
</script>
</body>
</html>