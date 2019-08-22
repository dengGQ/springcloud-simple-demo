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

    <!-- layer -->
    <script src="${webRoot}/plug-in/layer/layer.js"></script>

    <!-- showLoadLayer.js Add -->
    <script src="${webRoot}/common/js/showLoadLayer.js"></script>

    <!-- ext6.2.js Add -->
    <link rel="stylesheet" href="${webRoot}/plug-in/ext/css/theme-gray-all.css">
    <script src="${webRoot}/plug-in/ext/js/ext-all-debug.js"></script>
    <script src="${webRoot}/plug-in/ext/js/ext-all.js"></script>
    <script src="${webRoot}/plug-in/ext/js/locale-zh_CN.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ext/css/theme-gray-all.css">
    <script src="${webRoot}/common/js/extUtil.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>行政区划代码</title>
</head>
<body>
	<div id="center-container" style="height: 100%">
	</div>
	<script type="text/javascript" src="${webRoot}/content/specialStatements/ext_common.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	    var status = '${status}';
	    var reportButtons = '${reportButtons}';
	    var pageUrl = '${reportDic.pageName}';
	    var reportCode = '${reportDic.reportId}';
	    var store = undefined;
	    Ext.onReady(function () {
	    	
	    	var fields = ['xzqhlx', 'xzqhdm', 'xzqhmc']

	    	store = getStore({type: "remote", url: "/crs/areaNumberList", fields: fields}, true);

	    	var columnTitleAndField = [
				['行政区划类型', 'xzqhlx', {"type": 'textfield', "editor": false, 
					renderer: function(value){
						switch(value){
							case "1":
								return "省";
							case "2":
								return "市";
							case "3":
								return "县";
							default:
								return "异常值";
						}
					}
				}],
	            ['行政区划代码', 'xzqhdm', {"type": 'textfield', "editor": false}],
	            ['单位名称', 'xzqhmc', {"type": 'textfield', "editor": false}]
			];
	    	
	    	 //表单
		    var gridConfig = {store: store, columns: columnTitleAndField, renderTo: "center-container", height:'100%', checkboxable: true}
	
		    grid = createGridTablePanel(gridConfig);
		    parent.maingrid = grid;
		    parent.areaNumberGrid = grid;
	    });
	   
	</script>
</body>
</html>