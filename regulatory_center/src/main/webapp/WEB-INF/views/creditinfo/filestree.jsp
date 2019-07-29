<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<!-- 导入jquery bootstrap js css lib -->
<%@include file="/common/jsp/bootstrap.jsp"%>
<!-- 导入bootstrap-fileinput js css -->
<%@include file="/common/jsp/fileupload.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>文件树形展示</title>
<link rel="stylesheet" href="${webRoot}/plug-in/zTree/v3/css/demo.css" type="text/css">
<link rel="stylesheet" href="${webRoot}/plug-in/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${webRoot}/plug-in/jquery/1.10.2/jquery.js"></script>
<!--[if lte IE 9]>
 <script src="${webRoot}/plug-in/html5shiv/3.7/html5shiv.min.js"></script>
 <script src="${webRoot}/plug-in/respond/1.4.2/respond.js"></script>
<![endif]-->
<script type="text/javascript" src="${webRoot}/plug-in/zTree/v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/zTree/v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
var setting = {
		async: {
			enable: true,
			url:"${webRoot}/fileOperate/treedata",
		    autoParam:["id", "name", "level","pid","open","isParent","click","path"]
		},
		check: {
			enable: true,
			chkDisabledInherit: true
		},
		view : {  
            dblClickExpand : false,  
            showLine : false,
            selectedMulti: false
        },  
        data : {  
            simpleData : {  
                enable : true  
            }  
        },  
    	callback: {
			beforeAsync: beforeAsync,
			onAsyncError: onAsyncError,
			onAsyncSuccess: onAsyncSuccess,
			onCheck: onCheck
		},
        dataFilter: filter
	};

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	
	//关键代码，通过treeNode遍历父亲节点，根节点再次调用getParentNode得到null终止循环
	function getPathText(node){
        var s=node.name;
        while(node=node.getParentNode())s=node.name+'/'+s;
        return s;
    }
	
	function showLog(str) {
		console.log("showlog:"+str);
	}
	
	function onCheck(e, treeId, treeNode) {
		showLog("[ "+getTime()+" onCheck ];" + getPathText(treeNode) );
		var treeObj=$.fn.zTree.getZTreeObj("tree");
		var nodes=treeObj.getCheckedNodes(true);
	 	v="";
        for(var i=0;i<nodes.length;i++){
	        v+=nodes[i].path + ",";
        }
		$("#filePath").val(v);
		var areaStr = "";
	  	for(var i=0;i<nodes.length;i++){
	  		areaStr+=nodes[i].path + "\n";
        }
		$('#area').val(areaStr);
	}		
	

	var log, className = "dark";
	function beforeAsync(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		//showLog("[ "+getTime()+" beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		return true;
	}
	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		//showLog("[ "+getTime()+" onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		//showLog("[ "+getTime()+" onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}

	function refreshNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("tree"),
		type = e.data.type,
		silent = e.data.silent,
		nodes = zTree.getSelectedNodes();
		if (nodes.length == 0) {
			alert("请先选择一个父节点");
		}
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.reAsyncChildNodes(nodes[i], type, silent);
			if (!silent) zTree.selectNode(nodes[i]);
		}
	}

	$(document).ready(function(){
	   $.fn.zTree.init($("#tree"), setting);
	});

	/**
	 *上传
	 *jobName  job名称
	 *filePath 文件路径
	 */
	function upload() {
		var filePath = $("#filePath").val();
		if(filePath==null||filePath==""){
			layer.alert("请选择要导入的文件");
			return false;
		}
		var json = {"filePath" : filePath};
		$.ajax({
			type : 'post',
			url : '${webRoot}/fileOperate/batchReadCsvFile',
			data : json,
			beforeSend: function () {
				index = parent.layer.load(0, {shade: false}); 
			},
			success : function(data) {
				parent.layer.close(index);
				layer.alert(data.msg);
			},
			error: function(request) {
	        	parent.layer.close(index);
	            layer.alert("导入文件失败");
		    }
		}); 
	};
</script>
<style type="text/css">
div.content_wrap{width:360px;float:left;margin-left:10px;}
div.content_wrap div.left{width:100%;}
ul.ztree{width:350px;height:380px;}
div.content_textarea{width:320px;height:380px;float:right;border:1px solid #617775;position:absolute;top:10px;right:10px;}
div.content_textarea textarea{width:100%;height:100%;}
.content_foot{margin-top:10px;text-align:right;clear:both;}
.content_foot input{display:none;}
.content_foot button{margin:10px 10px 0 0;}
</style>
</head>

<body style="position:relative;">
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="tree" class="ztree"></ul>

	</div>
</div>
<div class="content_textarea">
	<textarea id="area" readonly="readonly"></textarea>
</div>
<div class="content_foot">
		<input id="filePath" name="filePath" type="text" size="50px" readonly="readonly" />
		<button class="btn btn-primary dropdown-toggle" onclick = "upload();">导入</button>
</div>
</body>
</html>