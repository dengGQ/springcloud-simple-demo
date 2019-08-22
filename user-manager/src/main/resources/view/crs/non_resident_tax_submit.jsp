<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/jsp/mytag.jsp"%>

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
    <title>CRS非居民金融账户涉税信息报送</title>
</head>
<body>
	<script type="text/javascript" src="${webRoot}/content/specialStatements/ext_common.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	    var reportId = ${reportId};
	    var status = '${status}';
	    var reportButtons = '${reportButtons}';
	    var form = undefined;
	    var maingrid = undefined;
	    var rateGrid = undefined;
	    var datalistGrid = undefined;
	    var editLogGrid = undefined;
	    var areaNumberGrid = undefined;
	    var reportFailReasonGrid = undefined;
	    var activePanel = "exchange_rate";
	    var addable = true;
	    Ext.onReady(function () {
	    	var btnConfigs = [{text: '查询', handler: query},
                {text: '重置', handler: function(){form.reset()}},
	    		{text: '导出报送文件excel', handler: exportExcel},
	    		{text: '生成报送文件XML', handler: generateReportXml},
	    		{text: '行政区划代码导入',  handler: areaNumberImport},
	    		{text: '报送失败反馈文件导入',  handler: reportFailACDFileImport}]
	    	var comboboxstore = {type: 'local', fields:['label', 'value'], 
	    				data:[['2015', '2015'],['2016','2016'],['2017','2017'],['2018','2018'],['2019','2019']]}
	    	var reportStatusComboboxstore = {type: 'local', fields:['label', 'value'], 
    				data:[['全部', '全部'],['报送失败','报送失败'],['报送成功','报送成功'], ['未报送', '未报送']]}
	    	var comboboxProperties = [
				{id: 'dgq-1', type: "combobox", fl: "年份", columnWidth: .2, autoHeight: true, name: "dataYear", ab: false, vf: "label", df: "value", store: comboboxstore},
				{id: 'dgq-2', type: "textfield", fl: "账号", columnWidth: .2, autoHeight: true, name: "account", ab: true, editable: true},
				{id: 'dgq-3', type: "textfield", fl: "错误码", columnWidth: .2, autoHeight: true, name: "errCode", ab: true, editable: true},
				{id: 'dgq-4', type: "combobox", fl: "报送状态", columnWidth: .2, autoHeight: true, name: "reportStatus", ab: true, vf: "label", df: "value", store: reportStatusComboboxstore}
			];
	    	
	    	//表格
			form = createFormPanel("formPanel", comboboxProperties, buildBtn(btnConfigs), {titleCollapse: false, collapsible: true}, "column");
	    	
		    var panel = createPanel("${webRoot}/crs/jumpPage?page=/crs/exchange_rate&reportButtons="+reportButtons,
		    		"${webRoot}/crs/jumpPage?page=/crs/data_list&reportButtons="+reportButtons,
		    		"${webRoot}/crs/jumpPage?page=/crs/data_update_log&reportButtons="+reportButtons,
		    		"${webRoot}/crs/jumpPage?page=/crs/area_number&reportButtons="+reportButtons,
		    		"${webRoot}/crs/jumpPage?page=/crs/report_fail_reason_list&reportButtons="+reportButtons)
		    
		    //布局视图	
		    createViewPort(form, panel, 'CRS非居民金融账户涉税信息报送');
		    
		   // Ext.getCmp("dgq-2").setVisible(false);
	    });
	    
	    
	    function createPanel(url1, url2, url3, url4, url5){
			return Ext.create('Ext.tab.Panel',{
		    	title:'',
		    	activeTab:0,//默认激活第一个tab页
		    	items:[{
		    		id:'exchange_rate',
		    		title:'汇率管理',
		    		html: '<iframe id="mapIframeId" scrolling="yes" frameborder="0" width="100%" height="100%" src="'+url1+'"></iframe>'
		    	},{
		    		id:'data_list',
		    		title:'数据列表',
		    		html: '<iframe id="mapIframeId" scrolling="yes" frameborder="0" width="100%" height="100%" src="'+url2+'"></iframe>'
		    	},{
		    		id: 'edit_log',
		    		title: "修改日志",
		    		html: '<iframe id="mapIframeId" scrolling="yes" frameborder="0" width="100%" height="100%" src="'+url3+'"></iframe>'
		    	},{
		    		id: 'area_number',
		    		title: "行政区划代码",
		    		html: '<iframe id="mapIframeId" scrolling="yes" frameborder="0" width="100%" height="100%" src="'+url4+'"></iframe>'
		    	},{
		    		id: 'report_fail_reason_list',
		    		title: "报送反馈文件",
		    		html: '<iframe id="report_fail_reason_list" scrolling="yes" frameborder="0" width="100%" height="100%" src="'+url5+'"></iframe>'
		    	}],
		    	listeners:{
		    		tabchange:function(tp,p){
		    			console.log(p.id)
		    			if(p.id == 'data_list'){
		    				maingrid = datalistGrid;
		    				activePanel = "data_list";
		    				//Ext.getCmp("dgq-2").setVisible(true);
		    			}else if(p.id == 'edit_log'){		    				
		    				maingrid = editLogGrid;
		    				activePanel = "edit_log";
		    			}else if(p.id == 'area_number'){		    				
		    				maingrid = areaNumberGrid;
		    				activePanel = "area_number";
		    			}else if(p.id == 'exchange_rate'){
		    				maingrid = rateGrid;
		    				activePanel = "exchange_rate";
		    			}else if(p.id == 'report_fail_reason_list'){
		    				maingrid = reportFailReasonGrid;
		    				activePanel = "report_fail_reason_list";
		    			}
		    		}
		    	}
		    });
		}
	    
	    function buildBtn(btnConfigArr){
	    	return btnConfigArr.map(function(btn){
	    		btn["listeners"]={
	    			'beforerender': function(){
	    				if(reportButtons.indexOf(btn.text)!=-1){
							this.hidden=false;
						}else{
							this.hidden=true;
						}
	    			}
	    		}
				return btn;
	    	})
	    }

	    //查询汇率列表/数据列表
        function query() {
	        //获取年份
            var fieldValues = form.getForm().getFieldValues();
            var store = maingrid.getStore();
            store.on('beforeload', function (store, options) {
                Ext.apply(store.proxy.extraParams, fieldValues);
            });
            store.load();
            addable = true;
        }

	    //行政区划代码导入
	    function areaNumberImport(){
	    	var dataYear = Ext.getCmp("dgq-1").getValue();
	    	if(form.isValid()){
	    		var comboboxConfiguration = [
					{id: "fileUpload", type: "filefield", buttonText: "选择文件", name: "file", width: 300, listeners: {
					    afterrender:function(obj,ops){
					        $("#fileUpload input").attr("accept","application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					    }
					}}
				];
				var areaNumberform = createFormPanel("formPanel1", comboboxConfiguration, null, {titleCollapse: false}, "column");
				var popBox = createWin("行政区划代码导入", [areaNumberform],380, 110, "确定", function(){
					areaNumberform.getForm().submit({
	                    url: webRoot+'/crs/importAreaNumberExcel/'+dataYear,
	                    waitMsg: '正在导入..',
	                    success : function(form, action){
	                    	console.log(form);
	                    	console.log(action);
	                        var result = Ext.decode(action.response.responseText);
	                        layer.alert(result.msg);
	                    	if(result.success){
	                    		popBox.close();
	                    	}
	                    },
	                    failure: function(form, action) {
	                    	var result = Ext.decode(action.response.responseText);
	                        layer.alert(result.msg);
	                    }
	                });
				}).show();
	    	}
	    }
	    
	    //报送失败反馈文件导入
	    function reportFailACDFileImport(){
	    	var dataYear = Ext.getCmp("dgq-1").getValue();
	    	if(dataYear==null || dataYear == ""){
	    	      layer.alert("请先选择年份！");
	    	      return;
	    	}
	    	
    		var comboboxConfiguration = [
				{id: "fileUpload", type: "filefield", buttonText: "选择文件", name: "file", width: 300, listeners: {
				    afterrender:function(obj,ops){
				    }
				}}
			];
			var reportFailform = createFormPanel("formPanel1", comboboxConfiguration, null, {titleCollapse: false}, "column");
			var popBox = createWin("报送失败反馈文件导入", [reportFailform],380, 110, "确定", function(){
				reportFailform.getForm().submit({
                    url: webRoot+'/crs/reportFailACDFileImport/'+dataYear,
                    waitMsg: '正在导入...',
                    success : function(form, action){
                        var result = Ext.decode(action.response.responseText);
                        layer.alert(result.msg);
                    	if(result.success){
                    		popBox.close();
                    	}
                    },
                    failure: function(form, action) {
                    	var result = Ext.decode(action.response.responseText);
                        layer.alert(result.msg);
                    }
                });
			}).show();
	    }
	    
	  	//生成报送文件xml
	    function generateReportXml(){
	  		
	    	var dataYear = Ext.getCmp("dgq-1").getValue();
	    	if(dataYear==null || dataYear == ""){
	    	      layer.alert("请先选择年份！");
	    	      return;
	    	}
	    	
	    	var requestParams = createRequestParams(dataYear)
	    	
	    	var selectedRows = maingrid.getView().getSelectionModel().getSelection();
	    	requestParams["appointReportData"] = selectedRows.map(function(o){return o.data})
	  		
	    	$.ajax({
	            url: webRoot+"/crs/generateXml",
	            type: 'post',
	            data: JSON.stringify(requestParams),
	            dataType:'JSON',
	            contentType:"application/json",
	            success: function (result, status, xhr) {
	            	console.log(result);
                    if(result.success) {
                        window.open(webRoot+"/crs/downloadCheckResult?cacheKey="+result.key);
                    }else{
                        layer.alert(result.msg);
                    }
	            }
	        });
	    }
	    
	    //导出报送文件excele
	    function exportExcel(){
	    	var dataYear = Ext.getCmp("dgq-1").getValue();
	    	if(dataYear==null || dataYear == ""){
	    	      layer.alert("请先选择年份！");
	    	      return;
	    	}
	    	
	    	var requestParams = createRequestParams(dataYear)
	    	
	    	var selectedRows = maingrid.getView().getSelectionModel().getSelection();
	    	requestParams["appointReportData"] = selectedRows.map(function(o){return o.data})
	    	
	    	$.ajax({
                url: webRoot+"/crs/checkReportInfoByCondition",
                type: 'post',
                data: JSON.stringify(requestParams),
                dataType:'JSON',
	            contentType:"application/json",
                success: function (result) {
                	console.log(result);
                    if(result.success) {
                        window.open(webRoot+"/crs/exportReportInfo/"+dataYear+"?cacheKey="+result.key);
                    }else{
                        layer.alert(result.msg);
                    }
                },
                error:function (data) {
                    layer.alert("请求异常！");
                }
            });
	    }
	    
	    function createRequestParams(dataYear) {
	    	var requestparams = {
	    		"dataYear": dataYear,
	    		"accountNumber": Ext.getCmp("dgq-2").getValue(),
	    		"errCode": Ext.getCmp("dgq-3").getValue(),
	    		"reportStatus": Ext.getCmp("dgq-4").getValue(),
	    	}
	    	console.log(requestparams)
	    	return requestparams
	    }
	</script>
</body>
</html>