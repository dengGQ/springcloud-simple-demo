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
							<button id="submit" type="button" class="btn btn-primary">提交</button>
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
									<p>短信宏变量范围：</p>
									<p>
										<c:forEach items="${variablesSettingList }" var="o">
											<button onclick="insertContentForArea(this)" id="${o.id }">${o.variablesName }</button>
										</c:forEach>
									</p>
									<p>注：请参考以上提供宏变量范围编写短信模板，宏变量要素以“#”间隔如#合作机构#。</p>
									<p>模板内容：</p>
									<p><textarea style="line-height: 16px;" cols="80" rows="5" wrap="hard" id="smsModuleContent" name="smsModuleContent">${smsModule.smsModuleContent }</textarea></p>
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
				$('#myTab a:first').tab('show');
				$("#tt").tree({
					url: webRoot+"/sms/loadTreeData",
					method: 'POST',
					animate: true,
					checkbox: true,
					onlyLeafCheck: true,
					onBeforeExpand: function(node){
						loadingIndex = ityzl_SHOW_LOAD_LAYER();
						var params = "?level="+(parseInt(node.attributes)+1);
						if((parseInt(node.attributes)+1) == 3){
							var coOrgCode = $("#tt").tree('getParent',$("#"+node.domId)).id;
							params += "&coOrgCode="+coOrgCode;
						}
						$(this).tree('options').url = webRoot+"/sms/loadTreeData"+params;
					},
					onLoadSuccess: function(){
						ityzl_CLOSE_LOAD_LAYER(loadingIndex)
					}
				})
			
			})
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			})
			
			$("#submit").click(function(){
				var data = $("#tt").tree('getChecked');
				if(notEmptCheckAndWordCount($("#smsModuleName").val(), "模板名称！", 20) && notEmptCheckAndWordCount($("#smsModuleContent").val(), "模板内容！", 350) && arrayNotEmptCheck(data, "请选择消息发送规则！")){
					var params = [];
					data.forEach(function(o){
						var parentNode = $("#tt").tree('getParent',$("#"+o.domId));
						var projId = parentNode.id;
						var orgId = $("#tt").tree('getParent',$("#"+parentNode.domId)).id;
						var obj = new NodeData(orgId, projId, o.id)
						params.push(obj)
					})
					$.ajax({
						url: webRoot+"/sms/saveSmsModule?smsModuleName="+$("#smsModuleName").val()+"&smsModuleContent="+encodeURIComponent($("#smsModuleContent").val()),
						type: "post",
						contentType : 'application/json;charset=utf-8',
						data: JSON.stringify(params),
						dataType: 'json',
						success: function(obj){
							layer.alert(obj.msg, function(index){
								layer.close(index);
								parent.resetBtn();
								parent.layer.close(parent.addLayerIndex);
							});
						},
						error: function(msg){
						}
						
					})
				}
			})
			
			function NodeData(orgId, projId, prodId){
				this.coOrgCode = orgId;
				this.projId = projId;
				this.prodId = prodId;
			}
			
			function insertContentForArea(o){
				var v = $(o).html();
				$("#smsModuleContent").val($("#smsModuleContent").val()+"#"+v+"#");
			}
			
			function notEmptCheckAndWordCount(value, msg, wordCount){
				if(!value){
					layer.alert("请输入"+msg);
					return false;
				}else if(value.length > wordCount){
					layer.alert(msg+"字数上限"+wordCount+"!");
					return false;
				}else{
					return true;
				}
			}
			function arrayNotEmptCheck(arr, msg){
				if(arr.length == 0){
					layer.alert(msg);
					return false;
				}else{
					return true;
				}
			}
		</script>
	</body>

</html>