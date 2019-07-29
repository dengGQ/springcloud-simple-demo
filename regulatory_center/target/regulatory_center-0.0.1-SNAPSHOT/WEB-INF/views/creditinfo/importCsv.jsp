<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CSV导入文件管理</title>
<style>
#layui-layer1{top:30px !important;}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline"  method="post" id="queryForm">
				<div class="form-group">
					<label >证件号码:</label> 
					<input type="text" class="form-control" name="credNo" id="credNo" size="20" />
				</div>
				
				<div class="form-group">
					<label>校验结果:</label> 
					<select name="checkResult" id="checkResult" class="form-control">
						<option class="form-control"  value="0"  selected="selected">请选择</option>
						<option  value="1">未校验</option>
						<option  value="2">校验通过</option>
						<option  value="3">校验失败</option>
					</select>
				</div>
				<div class="form-group btn2">
					<button type="button" id="queryBtn" class="btn btn-primary">查询</button>
					<button type="button" id="resetBtn" class="btn btn-primary">重置</button>
				</div>
			</form>
		</div>
		<div class="container fit-body">
			    <div id="toolbar" class="btn-group group3">
			    <div class="btn-group">
			      <button type="button" id="filestree" class="btn btn-primary">选择CSV文件(批量导入)</button>
			    </div>
			    <div class="btn-group">
			       <button class="btn btn-primary" id="importCSVBtn">导入CSV文件</button>
			    </div>
			     <div class="btn-group">
			      <button type="button" id=noSubmitBtn class="btn btn-primary">不报</button>
			    </div>
				<div class="btn-group">
					<button type="button" class="btn btn-primary" id="preCheckBtn">预校验 </button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-primary" id="reportDataBtn">上报 </button>
				</div>
			  	</div>
			<table id="importCsv-table"></table>
		</div>
	</div>
		
	<script>
	//数据来源：csv导入
	var dataSrc_formSys = 2;
	var url = "${webRoot}/trademanage/findList?dataSrc="+dataSrc_formSys;
		$(function() {
			
			/**
			 * CSV导入按钮调用
			 */
			$("#importCSVBtn").click(function(){
				layer.open({
				  type: 2,
				  title: 'CSV-导入',
				  //maxmin: true,
				  skin: 'layui-layer-rim', //加上边框
				  content:'${webRoot}/fileOperate/rediretCvsUploadPage', 
				  area: ['630px', '205px'], //宽高
		          cancel: function(){  }
				});
			});

			
			$("#resetBtn").click(function() {
				$('.form-control').val('');
			});
			
			
		   /**
			*  上报跳转
			**/
			$("#reportDataBtn").click(function(){
				layer.open({
					title : [ '推送数据', 'font-size:18px;' ],
					maxmin : false,
					type : 2,
					offset: '80px',
					area : [ '1000px', '500px' ],
					content : '${webRoot}/reported/index?dataSrc='+dataSrc_formSys,
					cancel : function() {
					}
				});
			});
		   
			/**
			 * 不报按钮
			 */
			$("#noSubmitBtn").click(function(){
				var selected = $('#importCsv-table').bootstrapTable('getSelections');
				if(!selected || selected.length==0){
					layer.msg("请选择需不报的数据",{icon: 2})
					return false;
				}
				var account = getAccountSelections(selected);
				var certNo = getCertNoSelections(selected);
				var certType = getCertTypeSelections(selected);
			    $.ajax({
		            type: "post",
		            url: "${webRoot}/datareportcfg/notSubMitDatas",
		            data: {'account':account,'dataSrc':dataSrc_formSys,'certType':certType,'certNo':certNo},
		            beforeSend: function () {  
			        	i = ityzl_SHOW_LOAD_LAYER();  
			        }, 
		            success: function(data){
		            	ityzl_CLOSE_LOAD_LAYER(i);  
			    		if (data.success == "true") {
			    			  layer.alert("设置成功！");
		                }else{
		                	  layer.alert(data.msg);
		                }
			    		$('#importCsv-table').bootstrapTable('refresh');
			    	},
			    	error: function () {
			    		ityzl_CLOSE_LOAD_LAYER(i);  
			    		layer.alert(data.msg);
		        	},
		        });
			});
		   
			/**
			 * 预校验，AJAX调用存储过程
			 **/
			$("#preCheckBtn").click(function (){
				//数据来源 1代表信贷系统 2代表CSV导入
				$.ajax({
					type: "post",
					url: "${webRoot}/check/preCheck",
					data: {'dataSrc_formSys': dataSrc_formSys},
					beforeSend: function () {  
				        	i = ityzl_SHOW_LOAD_LAYER();  
				    }, 
					success: function(data){
						ityzl_CLOSE_LOAD_LAYER(i);  
						layer.alert(data.msg);
						$('#importCsv-table').bootstrapTable('refresh');
					},
					error: function () {
			    		ityzl_CLOSE_LOAD_LAYER(i);  
			    		layer.alert(data.msg);
		        	}
				});
			});
			/**
			* 主页查询按钮
			*/
			$("#queryBtn").click( function() {
				$('#importCsv-table').bootstrapTable('refresh');
			});
			
			initTable();
		});
		
		/**
		 * 获取选中的业务号
		 */
		function getAccountSelections(rows) {
			var account = "";
			for (var i = 0; i < rows.length; i++) {
				if ("" == account) {
					account = rows[i].account;
				} else {
					account = account + ","+ rows[i].account
				}
			}
			return account;
		};
		
		/**
		 * 获取选中的证件类型
		 */
		function getCertTypeSelections(rows) {
			var certType = "";
			for (var i = 0; i < rows.length; i++) {
				if ("" == certType) {
					certType = rows[i].certtype;
				} else {
					certType = certType + "," + rows[i].certtype;
				}
			}
			return certType;
		};
		
		/**
		 * 获取选中的证件号
		 */
		function getCertNoSelections(rows) {
			var certno = "";
			for (var i = 0; i < rows.length; i++) {
				if ("" == certno) {
					certno = "'"+rows[i].certno+"'";
				} else {
					certno = certno + "," +"'"+ rows[i].certno+"'";
				}
			}
			return certno;
		};

		/**
		 * 去掉双重滚动条
		 */
		function tableHeight() {
			var window_height = $(window).height();
			var obj_off_y = $(".fit-body").offset().top;
			var result_height = window_height - obj_off_y;
			return result_height;
		}
		
		function doQuery(params) {
			$('#importCsv-table').bootstrapTable('refresh'); //刷新表格
		}

		function initTable() {
			$('#importCsv-table').bootstrapTable({
				method : 'GET',
				url : url,
				height:tableHeight(),
				clickToSelect : true, //是否启用点击选中行
				queryParams : function queryParams(params) { //设置查询参数  
					var param = {
						pageSize : params.pageSize,
						pageNumber : params.pageNumber,
						certno : $("#credNo").val(),
						dataStatus : $("#checkResult").val()
					};
					return param;
				},
				columns : [ 
					{
						checkbox : true
					}, 
					{
						field: '',
						title: '序号', 
						valign : 'left',
						formatter: function (value, row, index) { 
							return index+1; }
					},
					{
						field : 'name',
						title : '姓名',
						align : 'center',
						valign : 'middle',
					}, {
						field : 'certtype',
						title : '证件类型',
						align : 'center',
						valign : 'middle',
						formatter: function (value) {
							return getCredType(value)}
					}, {
						field : 'certno',
						title : '证件号',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'deptcode',
						title : '数据发生机构代码',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'account',
						title : '业务号',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'tradeid',
						title : '交易ID',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'creditlimit',
						title : '授信额度',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'shareaccount',
						title : '共享额度',
						align : 'center',
						valign : 'middle'
					}, {
						field : 'maxdebt',
						title : '最大负债额',
						align : 'center',
						valign : 'middle'
					},{
						field : 'guaranteeway',
						title : '担保方式',
						align : 'center',
						valign : 'middle',
						formatter: function (value) {
							return getGuaranteeway(value)}
					},{
						field : 'termsfreq',
						title : '还款频率',
						align : 'center',
						valign : 'middle',
						formatter: function (value) {
							return getTermsfreq(value)}
					},{
						field : 'monthduration',
						title : '还款月数',
						align : 'center',
						valign : 'middle'
					},{
						field : 'dataSrc',
						title : '数据来源',
						align : 'left',
						valign : 'middle',
						formatter: function (value) {
							return value==1?'信贷':'CSV'}
					}]
			});
		}
		
		/**
		 * 信托项目下拉框
		 */
		function selectProject(){
			var projectNum;
			var index = layer.open({
				  type: 2,
				  title: '选择信托项目',
				  maxmin: false,
				  content: '${webRoot}/publicquery/project',
				  area: ['850px', '500px'],
				  btn: ['确定','关闭'],
	              yes: function(index){
	                  projectNum = window["layui-layer-iframe" + index].callbackdata();
	                  $("#projectId").val(projectNum[0]);
	                  $("#projectName").val(projectNum[1]);
	                  layer.close(index);
	              },
	              cancel: function(){
	              }
				});
		}
		
		/**
		 * 机构下拉框
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
		
		function formReset() {
			document.getElementById("queryForm").reset()
		}
		
		$("#filestree").click(function(){
			var array;
			var index = layer.open({
				  type: 2,
				  title: '选择csv文件',
				  maxmin: false,
				  content: '${webRoot}/fileOperate/filestree',
				  area: ['700px', '500px'],
	              yes: function(index){
	                array = window["layui-layer-iframe" + index].callbackdata();
	                layer.close(index);
	              },
	              cancel: function(){
	              }
			});	
		}
	);
	</script>
</body>
</html>