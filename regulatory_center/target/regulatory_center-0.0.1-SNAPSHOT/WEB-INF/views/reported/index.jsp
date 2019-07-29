<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>报送数据配置</title>
    <!-- 导入jquery bootstrap js css lib -->
    <%@include file="/common/jsp/bootstrap.jsp"%>
</head>
<body>
	

	<div class="container-fluid">
		
		<div class="row base-margin" id="query">
	        <ol class="breadcrumb">
	            <li><strong><span style="color: #27a0d7"></span></strong></li>
	        </ol>
	        <form class="form-inline" role="form"  method="post" id="queryForm">
	            <div class="form-group">
	                <button type="button" id="submitBtn"  class="btn btn-primary">推送数据</button>
	            </div>
	          
	        </form>
	    </div>
    <div class="container fit-body">
        <table id="reported-table" > </table>
    </div>
		
	</div>

	<script>
	var url = "${webRoot}/reported/findPage?dataSrc="+${dataSrc};
	$(function () {
		/* $.ajaxSetup({
		  async: false
		}); */
		//推送按钮点击事件
		$("#submitBtn").click(function(){
			var i ;
			var selected = $('#reported-table').bootstrapTable('getSelections');
			if(!selected || selected.length==0){
				layer.msg("请选择需推送的数据记录",{icon: 2})
				return false;
			}
			
			var successNum = getSuccessNumSelections(selected);
			if(successNum==0){
				layer.msg("没有可推送的数据",{icon: 2})
				return false;
			}
			
			//存储校验结果 -- 暂时没显示
			/* var validateMsg = new Array(); */
			var sourceType = new Array();
			var param = new Array();
			$.each(selected, function(index, row){
				param[index] = dateFormatUtil(row.insertDttm, 'yyyyMMdd')+ ':' + row.dataSrc;
				//因为校验参数是数据来源,最多校验2次
				if($.inArray(row.dataSrc, sourceType)==-1){
					/* //校验调用       上报不调用存储过程
					$.post("${webRoot}/check/preCheck", {'datatype':row.dataSrc}, function(data){
						validateMsg[index] = data;
					}); 
					sourceType[index] = row.dataSrc; */
				}
			});
			//推送数据调用
			$.ajax({
					type : "post",
					url : "${webRoot}/reported/reporting",
					data : {'insertDates':param.join(',')},
					beforeSend: function () {  
			        	i = ityzl_SHOW_LOAD_LAYER();  
			        },  
					success : function(data) {
						ityzl_CLOSE_LOAD_LAYER(i);  
						if(data.status==0){
				        	layer.alert('推送数据失败, 原因 \: ['+data.msg+']', {icon: 2});
				        }else{
				        	layer.alert('推送数据成功', {icon: 1});
				        }
					},
					error: function () {
			        	ityzl_CLOSE_LOAD_LAYER(i);  
		            },
				});
		});
		
		initTable();
	});
	

	/**
	 * 获取校验通过数据量
	 */
	function getSuccessNumSelections(rows) {
		var successNum = "";
		for (var i = 0; i < rows.length; i++) {
			if ("" == successNum) {
				successNum = rows[i].successNum;
			} else {
				successNum = successNum + "," + rows[i].successNum;
			}
		}
		return successNum;
	};
	
	
	function tableHeight() {
		var window_height = $(window).height();
		var obj_off_y = $(".fit-body").offset().top;
		var result_height = window_height - obj_off_y;
		return result_height;
		
	}
	
	function initTable() {
		$('#reported-table').bootstrapTable(
		{
			method : 'GET',
			showRefresh : true,
			height:tableHeight(),
			clickToSelect : true, //是否启用点击选中行
			showToggle : false, //是否显示详细视图和列表视图的切换按钮
			pageSize : pageSecondSize,
			url : url,
			columns :  [
		        { field : 'checked', radio : true},
		        { field : 'insertDttm', title : '日期', align : 'center', valign : 'middle',formatter: function (value) {return	dateFormatUtil(value)} }, 
		        { field : 'dataSrc', title : '数据来源', align : 'center', valign : 'middle',formatter: function (value) {return value==1?'信贷':'CSV'}}, 
		        { field : 'allNum', title : '总条数', align : 'center', valign : 'middle' },
		        { field : 'waitingNum', title : '待提交校验', align : 'center', valign : 'middle' }, 
		        { field : 'successNum', title : '校验通过', align : 'center', valign : 'middle'  }, 
		        { field : 'failNum', title : '校验未通过', align : 'center', valign : 'middle' }
		        ]
		});
	}

	</script>
</body>
</html>