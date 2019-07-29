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
					<label>合作机构:</label> 
					<select id="coOrgNameId"
						name="coOrgName" class="form-control">
						<option value="" selected="selected">全部</option>
						<c:forEach items="${coOrgNameList}" var="type">
							<option value="${type.coOrgCode}">${type.coOrgName}</option>
						</c:forEach>
					</select>
				</div>
	        	<div class="form-group">
					<label>开始日期:</label> 
					<input placeholder="应上报开始日期" class="form-control layer-date" id="startDateo" name="startDateo"> 
				</div>
				<div class="form-group">
					<label >结束日期:</label> 
					<input placeholder="应上报结束日期" class="form-control layer-date" id="endDateo" name="endDateo" >
				</div>
				<div class="form-group">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
				 <button type="button" id="resetBtn" class="btn btn-primary">重置</button> 
				</div>
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
	function initDateo() {
		//开始日期
		var start = {
			elem : '#startDateo',
			format : 'YYYY-MM-DD',
			max : laydate.now(),
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
			elem : '#endDateo',
			format : 'YYYY-MM-DD',
			max : laydate.now(),
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
	//findPageAll
	/**
		 * 重置按钮
		 */
		$("#resetBtn").click(function() {
			document.getElementById('queryForm').reset();
		});
	/**
	 * 机构下拉框 coOrgNameId
	 */
	function selectOrg(){
		var projectNum;
		var index = layer.open({
			  type: 2,
			  title: '选择机构',
			  maxmin: false,
			  content: '${webRoot}/publicquery/org',
			  area: ['850px', '500px'],
			  btn: ['确定','关闭'],
              yes: function(index){
                  projectNum = window["layui-layer-iframe" + index].callbackdata();
                  $("#coOrgCode").val(projectNum[0]);
                  $("#coOrgName").val(projectNum[1]);
                  //最后关闭弹出层
                  layer.close(index);
              },
              cancel: function(){
              }
		});
	}
	
	var url = "${webRoot}/reported/findPage?dataSrc="+${dataSrc};
	var urlAll = "${webRoot}/reported/findPageAll?dataSrc="+${dataSrc};
	$(function () {
		initDateo();
		
		/* $.ajaxSetup({
		  async: false
		}); */
		//推送按钮点击事件
		/**
	 * 初始化日期reported-table
	 */

     $("#queryBtn").click(function() {
    	 $('#reported-table').bootstrapTable( 'refresh');
		});
		$("#submitBtn").click(function(){
			var i ;
			var selected = $('#reported-table').bootstrapTable('getSelections');
			if(($('#startDateo').val() !="") && ($('#endDateo').val() !="")){
				$("input[type='radio']").attr("disabled","disabled");
			    }else{
			    	if(!selected || selected.length==0){
						layer.msg("请选择需推送的数据记录",{icon: 2})
						return false;
					}
			    	var successNum = getSuccessNumSelections(selected);
					if(successNum==0){
						layer.msg("没有可推送的数据",{icon: 2})
						return false;
					}
			    };
			
			if($("#startDateo").val() == ""){
				layer.msg("请选择需要推送的合作机构",{icon: 2});
				return false;
			}
			
			
			
			
			//存储校验结果 -- 暂时没显示
			/* var validateMsg = new Array(); */
			var sourceType = new Array();
			var param = new Array();
			
			$.each(selected, function(index, row){
				debugger;
				var startDate= $("#startDateo").val();
				var endDate= $("#endDateo").val();
				var coOrgCode = $("#coOrgNameId").val();
				if($("#coOrgNameId").val() != ""){
					if (($('#startDateo').val() !="") && ($('#endDateo').val() !="")){
						
						param[index] =dateFormatUtil($("#startDateo").val(), 'yyyyMMdd')+":"+ dateFormatUtil($("#endDateo").val(), 'yyyyMMdd') +':'  + row.dataSrc+":"+$("#coOrgNameId").val();
					}else{
						param[index] =  dateFormatUtil(row.insertDttm, 'yyyyMMdd')+":"+dateFormatUtil(row.insertDttm, 'yyyyMMdd')+ ':' + row.dataSrc+":" +$("#coOrgNameId").val();
					}
				} else {
					/* if(coOrgCode == ""){
						layer.msg("请选择需要推送的合作机构",{icon: 2});
					} */
					
					
					//return false;
					 if (($('#startDateo').val() !="") && ($('#endDateo').val() !="")){
						
						param[index] = dateFormatUtil($("#startDateo").val(), 'yyyyMMdd')+":"+ dateFormatUtil($("#endDateo").val(), 'yyyyMMdd') +':'  + row.dataSrc;
					}else{
						param[index] = dateFormatUtil(row.insertDttm, 'yyyyMMdd')+":"+dateFormatUtil(row.insertDttm, 'yyyyMMdd')+ ':' + row.dataSrc;
					} 
				}
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
		
		initTable(urlAll);
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
	
	function initTable(urls) {
		
		debugger;
		$('#reported-table').bootstrapTable(
		{
			method : 'GET',
			showRefresh : true,
			height:tableHeight(),
			clickToSelect : true, //是否启用点击选中行
			showToggle : true, //是否显示详细视图和列表视图的切换按钮
			pageSize : pageSecondSize,
			url : urls,
			queryParams : function queryParams(params) { 
				var param = {
					pageSize : params.pageSize,
					pageNumber : params.pageNumber,
					startDate : $("#startDateo").val(),
					endDate : $("#endDateo").val(),
					coOrgName : $("#coOrgNameId").val()
					
				};
				return param;
			},
			columns :  [
		        { field : 'checked', radio : true},
		        { field : 'insertDttm', title : '日期', align : 'center', valign : 'middle',formatter: function (value) {return	dateFormatUtil(value)} }, 
		        { field : 'dataSrc', title : '数据来源', align : 'center', valign : 'middle',formatter: function (value) {return value==1?'信贷':'CSV'}}, 
		        { field : 'allNum', title : '总条数', align : 'center', valign : 'middle' },
		        { field : 'waitingNum', title : '待提交校验', align : 'center', valign : 'middle' }, 
		        { field : 'successNum', title : '校验通过', align : 'center', valign : 'middle'  }, 
		        { field : 'failNum', title : '校验未通过', align : 'center', valign : 'middle' }
		         ,{ field : 'coOrgName', title : '合作机构', align : 'center', valign : 'middle' } 
		        ]
		});
	}

	</script>
</body>
</html>