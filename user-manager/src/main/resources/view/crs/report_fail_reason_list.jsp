<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/mytag.jsp"%>
<%@ include file="/common/jsp/bootstrap.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <!--jquery-->
    <script src="${webRoot}/plug-in/jquery/1.10.2/jquery.js"></script>
    <script src="${webRoot}/common/js/util.js" type="text/javascript"></script>
    <script src="${webRoot}/common/js/json-eps.js" type="text/javascript"></script>
    <script src="${webRoot}/common/js/prefixfree.min.js"></script>
    <script src="${webRoot}/common/js/drag-arrange.js"></script>
    <script src="${webRoot}/common/js/ajaxfileupload.js" type="text/javascript"></script>

    <!--bootstrap base-->
    <link href="${webRoot }/common/css/bootstrap.css" rel="stylesheet">
    <link href="${webRoot }/common/css/style.css" rel="stylesheet">
    <script src="${webRoot}/plug-in/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!-- layer -->
    <script src="${webRoot}/plug-in/layer/layer.js"></script>

    <!-- data status converter -->

    <!-- default pageNumber pageSize -->
    <script src="${webRoot}/common/js/pageDefaultSize.js"></script>

    <!-- showLoadLayer.js Add -->
    <script src="${webRoot}/common/js/showLoadLayer.js"></script>

    <!-- bootstrap-fileinput.js Add -->
    <script src="${webRoot}/plug-in/bootstrap-fileinput/js/fileinput.min.js"></script>
    <script src="${webRoot}/plug-in/bootstrap-fileinput/js/locales/zh.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap-fileinput/css/fileinput.min.css">

    <!-- ext6.2.js Add -->
    <link rel="stylesheet" href="${webRoot}/plug-in/ext/css/theme-gray-all.css">
    <script src="${webRoot}/plug-in/ext/js/ext-all-debug.js"></script>
    <script src="${webRoot}/plug-in/ext/js/ext-all.js"></script>
    <script src="${webRoot}/plug-in/ext/js/locale-zh_CN.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ext/css/theme-gray-all.css">
    <script src="${webRoot}/common/js/extUtil.js" type="text/javascript"></script>
    <%-- <script type="text/javascript" src="${webRoot}/content/crs/dollar_rate_maintain.js"></script>
    <script type="text/javascript" src="${webRoot}/content/crs/non_resident_tax_submit.js"></script> --%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改日志</title>
</head>
<body>
	<div id="center-container" style="height: 100%"></div>
	<script type="text/javascript" src="${webRoot}/content/specialStatements/ext_common.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	    var status = '${status}';
	    var reportButtons = '${reportButtons}';
	    var pageUrl = '${reportDic.pageName}';
	    var reportCode = '${reportDic.reportId}';
	    var store = undefined;
	    Ext.onReady(function () {

	    	var fields = ['id', 'docRefId', 'errCode', 'dictName', 'dictTwoLevel', 'createTime']
	    	store = getStore({type: "remote", url: "/crs/reportFailACDFileList", fields: fields}, true);

	    	var columnTitleAndField = [
	    		['序号', '', {"type": 'textfield', "editor": false, width: 50, 
					renderer:function (value, metadata, record, rowIndex) {
	                   return rowIndex + 1; // 获取序号，方法2
	               }}],
	            ['报告唯一编码', 'messageRefId', {"type": 'textfield', "editor": false, "width": 260}],
				['账户记录编号', 'docRefId', {"type": 'textfield', "editor": false, "width": 260}],
		        ['错误码', 'errCode', {"type": 'textfield', "editor": false}],
		        ['中文描述', 'dictName', {"type": 'textfield', "editor": false, "width": 260}],
				['指标名称', 'dictTwoLevel', {"type": 'textfield', "editor": false, "width": 200}]
		    ];
	    	
	    	 //表单
		    var gridConfig = {store: store, columns: columnTitleAndField, renderTo: "center-container", height:'100%', enableTextSelection: true}
	
		    grid = createGridTablePanel(gridConfig);
		    parent.maingrid = grid;
		    parent.reportFailReasonGrid = grid;
	    });
	</script>
</body>
</html>