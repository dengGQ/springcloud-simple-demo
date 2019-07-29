<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="${webRoot }/common/css/easyui.css" rel="stylesheet">
		<link href="${webRoot }/common/css/demo.css" rel="stylesheet">
		<script src="${webRoot}/plug-in/hplus/js/jquery.easyui.min.js"></script>
		<title></title>
		<style>
			.p1 {margin-left: 115px;}
			.close{float:none;font-size:22px;opacity:1;font-weight: normal;}
			.form-group{margin-top:20px}
		</style>
	</head>

	<body>
		<div class="container-fluid">
			<div class="row" id="">
				<div class="col-md-12">
					<ul class="nav nav-tabs" id="myTab" style="position:relative">
						<li class="active">
							<a href="#home">模板管理</a>
						</li>
						<li>
							<a href="#profile">规则管理</a>
						</li>
						<li style="position:absolute;right:0;margin-top:5px">
						</li>
					</ul>

					<div class="tab-content">
						<div class="tab-pane active" id="home">
							<div class="row">
								<div class="col-md-12">
									<form class="form-horizontal">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">模板名称：</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="smsModuleName" name="smsModuleName" value="${smsModule.smsModuleName }">
											</div>
										</div>
									</form>
									<div class="p1">
									<p>模板内容：</p>
									<p><textarea cols="80" rows="5" wrap="hard" id="smsModuleContent" name="smsModuleContent">${smsModule.smsModuleContent }</textarea></p>
									<p><span>登记人：${operator }
									</span><span style="margin-left:50px">登记时间：${createTime }</span></p>
									</div>
								</div>
							</div>

						</div>
						<div class="tab-pane" id="profile">
						  <h2>发送对象规则（合作机构项目）</h2>
					      <div class="easyui-panel" style="padding:5px">
							<ul id="tt" ></ul>
						  </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
			var loadingIndex = -1;
			$(function() {
				$("input").attr('disabled', 'disabled');
				$("#smsModuleContent").attr('disabled', 'disabled');

				$('#myTab a:first').tab('show');
				$("#tt").tree({
					url: webRoot+"/sms/loadTreeData",
					method: 'POST',
					animate: true,
					checkbox: false,
					onlyLeafCheck: true,
					onBeforeExpand: function(node){
						loadingIndex = ityzl_SHOW_LOAD_LAYER();
						var params = "?level="+(parseInt(node.attributes)+1)+"&moduleId="+${smsModule.id};
						if((parseInt(node.attributes)+1) == 3){
							var coOrgCode = $("#tt").tree('getParent',$("#"+node.domId)).id;
							params += "&coOrgCode="+coOrgCode;
						}
						$(this).tree('options').url = webRoot+"/sms/loadTreeDataModuleDetail"+params
					},
					onLoadSuccess: function(node, data){
						ityzl_CLOSE_LOAD_LAYER(loadingIndex);
						$('#tt').tree("expandAll", $('#tt').tree("getRoot").target)
					}
				})
			})
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			})
		</script>
	</body>

</html>