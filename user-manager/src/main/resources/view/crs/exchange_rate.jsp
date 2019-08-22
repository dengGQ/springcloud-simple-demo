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
    <title>汇率管理</title>
</head>
<body>
	<div id="center-container" style="height: 100%">
		<a onclick="inserRow()" style="font-size:18px;position: absolute;left:335px;z-index:999;cursor: pointer;">+</a>
	</div>
	<script type="text/javascript" src="${webRoot}/content/specialStatements/ext_common.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	    var status = '${status}';
	    var reportButtons = '${reportButtons}';
	    var pageUrl = '${reportDic.pageName}';
	    var reportCode = '${reportDic.reportId}';
	    var store = undefined;
	    Ext.onReady(function () {
	    	Ext.define('exchangeRate', {
			    extend: 'Ext.data.Model',
			    fields: ['dateeStr', 'exchangeRate']
			});
	    	var fields = ['dateeStr', 'exchangeRate']

	    	store = getStore({type: "remote", url: "/crs/exchangeRate", fields: fields}, true);

	    	var columnTitleAndField = [
				['序号', '', {"type": 'textfield', "editor": false, 
					renderer:function (value, metadata, record, rowIndex) {
	                   return rowIndex + 1; // 获取序号，方法2
	               }}],
		        ['日期', 'dateeStr', {"type": 'textfield', "editor": true, editType: 'datefield', allowBlank: false,
		        	listenersObj: {
		        		'select': function(field, value, eOpts){
   							$("input[name='dateeStr']").val(value.getFullYear()+"-12-31")
		        		}
      				}
	        	}],
		        ['美元汇率', 'exchangeRate',{"type": 'textfield', "editor": true, allowBlank: false}]
		    ];
	    	
	    	 //表单
		    var gridConfig = {store: store, columns: columnTitleAndField, renderTo: "center-container", height:'100%', checkboxable: true}
	
		    var rowEditing = buildPluginsObjForEdit();
		    grid = createGridTablePanel(gridConfig, rowEditing);
		    parent.maingrid = grid;
		    parent.rateGrid = grid;
	    });
	    
	    function inserRow(){
	    	console.log(store)
	    	if(parent.addable){
		    	var ransom = new exchangeRate()
		    	store.insert(store.getCount(), ransom);
		    	parent.addable = false;
	    	}else{
	    		layer.alert("请先保存已添加的数据！")
	    	}
	    }
	    
	    function buildPluginsObjForEdit(){
	    	return {
	            ptype: 'rowediting',
	            saveBtnText: '保存',
	      	  	cancelBtnText: "取消",
	            clicksToEdit: 2,
	            listeners: {
	                edit: function(editor, context, eOpts){
	                	console.log(context.record.data)
	                	if(typeof context.record.data.id == 'string'){
	                		context.record.data.id = null;
	                	}
	                	var submitObj = context.record.data;
	                	submitObj["datee"] = context.record.data.dateeStr;
	                	console.log(submitObj)
	                	$.ajax({
	                		type: 'POST',
	                		url: webRoot+"/crs/addOrUpdate",
	                		data: JSON.stringify(submitObj),
	                		contentType: 'application/json',
	                		dataType:'JSON',
	                		success: function(result){
	                			layer.alert(result.msg)
	                			parent.addable = true;
	                			store.load();
	                		}
	                	})
	                },
	                cancel: function(){
	                	parent.addable = true;
	                }
	            }
	        };
	    }
	</script>
</body>
</html>