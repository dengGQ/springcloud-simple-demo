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
<title>短信发送管理</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline" role="form" method="post" id="queryForm"
				action="${webRoot}/sms/exportSmsSendDetail">
				<div class="form-group">
					<label>开始时间</label> <input placeholder="开始时间"
						class="form-control layer-date" id="startDate" name="startDate">
					<input type="hidden" id="dtp_input1" value="" /><br />
				</div>
				<div class="form-group">
					<label for="endMonth">结束时间</label> <input placeholder="结束时间"
						class="form-control layer-date" id="endDate" name="endDate">
				</div>
				<!-- <div class="form-group">
					<label >短信状态:</label> 
					 <select id="smsCode" name="smsCode" class="form-control">
                            <option value="0" selected="selected">全部</option>
                            <option value="00">成功</option>
                            <option value="1">失败</option>
                        </select>
				</div> -->
				<div class="form-group">
					<label>类型:</label> <select id="smsModuleTypeId"
						name="smsModuleType" class="form-control">
						<option value="" selected="selected">全部</option>
						<c:forEach items="${smsModuleTypeList}" var="type">
							<option value="${type.dictId}">${type.dictName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>合作机构:</label> <input type="hidden" class="form-control"
						name="coOrgCode" id="coOrgCode" /> <input type="text"
						readonly="readonly" class="form-control" name="coOrgName"
						id="coOrgName" onclick="selectCoOrg()" placeholder="请选择" />
				</div>
				<div class="form-group">
					<label>项目名称:</label> <input type="hidden" class="form-control"
						name="projId" id="projId" /> <input type="text"
						readonly="readonly" class="form-control" name="projName"
						id="projName" onclick="selectProject()" placeholder="请选择" />
				</div>
				<div class="form-group">
					<label>产品名称:</label> <input type="hidden" class="form-control"
						name="prodCode" id="prodCode"> <input type="text"
						readonly="readonly" class="form-control" name="prodName"
						id="prodName" onclick="selectProduct()" placeholder="请选择" />
				</div>
				<div class="form-group">
					<label>证件号码:</label> <input type="text" class="form-control"
						id="credNo" name="credNo">
				</div>
				<div class="form-group">
					<label>手机号码:</label> <input type="text" class="form-control"
						id="phone" name="phone">
				</div>
				<div class="form-group">
					<label>逾期时长:</label> <select name="overdue" id="overdue"
						class="form-control">
						<option class="form-control" value="" selected="selected">请选择</option>
						<c:forEach items="" var="type">
							<option value=""></option>
						</c:forEach>
					</select>
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
					<button class="btn btn-primary" id="export"
						onclick="exportSendDetail()">导出</button>
				</div>
			</div>
			<table id="smsSendDetail-table"></table>
		</div>
	</div>

	<script>
		var url = "${webRoot}/sms/querySmsSendList";
		var str ;
		$(function() {
			smsSendDetailTable();

			$("#resetBtn").click(function() {
				$('.form-control').val('');
			});

			$("#queryBtn").click(function() {
				$('#smsSendDetail-table').bootstrapTable('refresh');
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

		function smsSendDetailTable() {
			$('#smsSendDetail-table')
					.bootstrapTable(
							{
								method : 'GET',
								clickToSelect : true, //是否启用点击选中行
								singleSelect : true,//复选框单选
								height : tableHeight(),
								pagination : true,
								pageNumber : 1,
								pageSize : 10,
								url : url,
								queryParams : function queryParams(params) { //设置查询参数  
									var param = {
										pageSize : params.pageSize,
										pageNumber : params.pageNumber,
										startDate : $("#startDate").val(),
										endDate : $("#endDate").val(),
										smsCode : $("#smsCode").val(),
										coOrgCode : $("#coOrgCode").val(),
										projId : $("#projId").val(),
										prodCode : $("#prodCode").val(),
										credNo : $("#credNo").val(),
										phone : $('#phone').val(),
										smsModuleTypeName : $(
												'#smsModuleTypeId').val()
									};
									return param;
								},
								columns : [
										{
											field : 'custName',
											title : '客户姓名',
											align : 'center',
											valign : 'middle',
										},
										{
											field : 'phone',
											title : '手机号码',
											align : 'center',
											valign : 'middle'
										},
										{
											field : 'account',
											title : '业务号',
											align : 'center',
											valign : 'middle'
										},
										{
											field : 'id',
											visible : false
										},
										{
											field : 'createTime',
											title : '日期',
											align : 'center',
											valign : 'middle',
											 formatter : function(value) {
												 let date = new Date(value);
												 return date.format('yyyy-MM-dd hh:mm:ss');
												 
												 
												//return date ;
											}  
										},
										{
											field : 'coOrgCode',
											title : '合作机构',
											align : 'center',
											valign : 'middle',
										},
										{
											field : 'projId',
											title : '项目名称',
											align : 'center',
											valign : 'middle',
										},
										{
											field : 'prodName',
											title : '产品名称',
											align : 'center',
											valign : 'middle',
										},
										{
											field : 'status',
											title : '短信状态',
											align : 'center',
											valign : 'middle'
										},
										{
											field : 'credNo',
											title : '证件号码',
											align : 'center',
											valign : 'middle'
										},
										{
											field : 'smsContent',
											title : '短信内容',
											align : 'center',
											valign : 'middle'
										},
										{
											title : "操作",
											valign : "middle",
											align : "center",
											formatter : function(value, row,
													index) {
												var detailId = JSON.stringify(row)
												return "<button class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' id= 'modalID' onclick ='modelval("+detailId+")'>设置</button>";
											}
										} ]
							});
		}

		function formReset() {
			document.getElementById("queryForm").reset()
		}

		
		//选择合作机构
		function selectCoOrg() {
			var projectNum;
			var index = layer.open({
				type : 2,
				title : '选择机构',
				maxmin : true,
				content : '${webRoot}/publicquery/orgNameAndCode',
				area : [ '1000px', '500px' ],
				btn : [ '确定', '关闭' ],
				yes : function(index) {
					//当点击‘确定’按钮的时候，获取弹出层返回的值
					projectNum = window["layui-layer-iframe" + index]
							.callbackdata();
					//打印返回的值，看是否有我们想返回的值。
					if (projectNum == null || projectNum == undefined
							|| projectNum == '') {
						//layer.alert("未选择对象");
						return false;
					}
					$("#coOrgCode").val(projectNum[0]);
					$("#coOrgName").val(projectNum[1]);
					//最后关闭弹出层
					layer.close(index);
				},
				cancel : function() {
					//右上角关闭回调
				}

			});
		}
		Date.prototype.format = function(fmt){
			 var o = { 
				        "M+" : this.getMonth()+1,                 //月份 
				        "d+" : this.getDate(),                    //日 
				        "h+" : this.getHours(),                   //小时 
				        "m+" : this.getMinutes(),                 //分 
				        "s+" : this.getSeconds(),                 //秒 
				        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
				        "S"  : this.getMilliseconds()             //毫秒 
				    }; 
				    if(/(y+)/.test(fmt)) {
				            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
				    }
				     for(var k in o) {
				        if(new RegExp("("+ k +")").test(fmt)){
				             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
				         }
				     }
				    return fmt; 

		}
		function selectProject() {
			var projectNum;
			var index = layer.open({
				type : 2,
				title : '选择信托项目',
				maxmin : true,
				content : '${webRoot}/publicquery/projectNameAndId',
				area : [ '1000px', '500px' ],
				btn : [ '确定', '关闭' ],
				yes : function(index) {
					//当点击‘确定’按钮的时候，获取弹出层返回的值
					projectNum = window["layui-layer-iframe" + index]
							.callbackdata();
					//打印返回的值，看是否有我们想返回的值。
					$("#projId").val(projectNum[0]);
					$("#projName").val(projectNum[1]);
					//最后关闭弹出层
					layer.close(index);
				},
				cancel : function() {
					//右上角关闭回调
				}

			});
		}

		function selectProduct() {
			var projectNum;
			var index = layer.open({
				type : 2,
				title : '选择产品',
				maxmin : true,
				content : '${webRoot}/publicquery/productAndOrg',
				area : [ '1000px', '500px' ],
				btn : [ '确定', '关闭' ],
				yes : function(index) {
					//当点击‘确定’按钮的时候，获取弹出层返回的值
					projectNum = window["layui-layer-iframe" + index]
							.callbackdata();
					$("#prodCode").val(projectNum[0]);
					$("#prodName").val(projectNum[1]);
					//最后关闭弹出层
					layer.close(index);
				},
				cancel : function() {
					//右上角关闭回调
				}

			});
		}

		//导出短信发送详情
		function exportSendDetail() {
			$("#queryForm").submit();
		}

		function initDate() {
			//开始日期
			var start = {
				elem : '#startDate',
				format : 'YYYY-MM-DD',
				max : '9999-12-31',
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
				format : 'YYYY-MM-DD',
				max : '9999-12-31',
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
		 function modelval(value){
			str = value;
			return str
			
		} 
		 $(function() {	
			$("#commit").click(function() {
				debugger;
				var data =str;
				data.sms = $("#name").val()

				$.ajax({
					url : '${webRoot}/sms/updateSms',
					data : data,
					async:true,
					type : 'POST',
					success : function(data) {
						
						layer.alert(data.msg);
						
						}
						
					
				});
				
				$('#myModal').map(function () {
			         if (!$(this).is(":hidden")){
			             $(this).modal('hide');
			         }
			     });
			});
		 })
	</script>
	<!-- 短信模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="background-color: turquoise;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">短信发送变更</h4>
				</div>
				<div class="modal-body">
					<ul>
						<ol>
							<label>暂缓理由&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control"
								style="width: 330px; display: inline;" id="name"></input>
							 	<!-- <label>手机号码&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial" id = "phone"/>  -->
						</ol>
						<!-- <ol>
							<label>业&nbsp;&nbsp;务&nbsp;&nbsp;号&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial" id="accont"/>
							<label>合作机构&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial"  />
						</ol>
						<ol>
							<label>项目名称&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial" id ="projName"/>
							<label>产品名称&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial" id="prodName" />
						</ol>
						<ol>
							<label>短信状态&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial" />
							<label>证件号码&nbsp;&nbsp;:&nbsp;&nbsp;</label>
							<input class="form-control" style="width: 130px;"
								dispaly="initial" />
						</ol> -->
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id = "commit">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>