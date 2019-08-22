/**
 * 创建store
 * @param url
 * @returns
 */
function getStore(obj, eagerLoad){
	var store = undefined;
	if(obj.type == "local"){
		store = Ext.create('Ext.data.Store', {
		    fields: obj.fields,
		    data : obj.data
		});
	}else{
		
		Ext.data.Store.prototype._load = Ext.data.Store.prototype.load
		store = new Ext.data.Store({
			pageSize: obj.pageSize ? obj.pageSize : 30,
			fields: obj.fields,
			proxy: new Ext.data.HttpProxy({
					getMethod: function(){ return 'POST';},
					url: webRoot+obj.url,
					timeout: 90000,
					reader: new Ext.data.JsonReader({ totalProperty: "total", root: "rows" })
			}),
			load:function(options){
				var self = this;
				options = options || {};
		        options.params = options.params || {};
				options['callback'] = obj.callBack
				self._load(options)
			}
		});
		if(eagerLoad){
			store.loadPage(1);
		}
	}
	return store;
}

function createCheckBox(config){
	function build(){
		var item = [];
		config.data.forEach(function(o){
			item.push({
                id : o.id,
                name : config.name,
                width:config.labelWidth==null?8*config.name.length:config.labelWidth,
                boxLabel : o.bl,
                inputValue : o.val,
                checked: o.checked,
                readOnly : o.readOnly,
            })
		})
		return item;
	}
	return {
        id : 'fxkGroup',
        columnWidth: config.columnWidth,
        autoHeight: config.autoHeight,
        fieldLabel: config.fl,
        labelStyle:"text-align: right; font-size:12px;",
        xtype : 'checkboxgroup',
        name : 'fxkGroup',
        margin: config.margin ? config.margin : '5 10 0 0',
		padding: '0 0 0 0',
        columns : config.columns, // 在上面定义的宽度上展示3列
        value: config.value,
        items : build()
    };
}


function createRadio(config){
	function build(){
		var item = [];
		config.data.forEach(function(o){
			item.push({
                id : o.id,
                name : config.name,
                boxLabel : o.bl,
                inputValue : o.val,
                checked: o.checked,
                readOnly : o.readOnly,
            })
		})
		return item;
	}
	return {
        id : config.id,
        columnWidth: config.columnWidth,
        autoHeight: config.autoHeight,
        fieldLabel: config.fl,
        labelStyle:"text-align: right; font-size:12px",
        labelWidth: config.lw,
        xtype : 'radiogroup',
        name : config.name,
        columns : config.columns,
        value: config.value,
        items : build()
    };
}

function createCombobox(config){
	var labelNameLength = config.ab ? (config.fl.length+3) : (config.fl.length+2);
	var worldNum = config.ab ? config.fl.getWorldNum()+1 : config.fl.getWorldNum();
	
	var combobox = {
		id: config.id,
		xtype:'combobox',  
		listConfig:{//控制下拉列表的样式  
			emptyText:'没有找到匹配的数值',  
			maxHeight:200  
		},
		columnWidth: config.columnWidth,
		autoHeight: config.autoHeight,
		fieldLabel: config.ab ? config.fl : "<span style='color:red'>*</span>" + config.fl,
		name: config.name,
		hiddenName : config.name,
		margin: config.margin ? config.margin : '5 10 0 0',
		padding: '0 0 0 0',
		labelStyle:"text-align: right; font-size:12px",
		queryMode:'local',
		store: getStore(config.store, true),  
		valueField: config.vf,  
		displayField: config.df,//展示的下拉项名称  
		editable: true,//是否可编辑  
		forceSelection: config.fs ? config.fs : true,//不允许输入数据集合中没有的数值  
		typeAhead : true,//自动补全，默认为false 
		anyMatch: true,
		allowBlank: config.ab,
		multiSelect: config.ms,
		/*emptyText: '请选择'+config.fl,*/
		/*emptyText: config.readOnly ? '': '请选择'+config.fl,*/
		labelWidth: 'NaN',//12*(labelNameLength-worldNum) + 6*worldNum,
		value: config.value,
		readOnly: config.readOnly,
		fieldStyle: config.readOnly ? "background: #C1C1C1" : '',
		listeners: config.listenersObj instanceof Function ? config.listenersObj() : config.listenersObj
	}
	
	if(config.store.pageable){
		combobox['pageSize'] = 25
	}
	return combobox;
}

/**
 * 创建form表单
 * @param comboboxProperties  表单内下拉框数据配置项
 * @param reportExcelUrl  导出excel url
 * @param grid 数据表格
 * @returns
 */
function createFormPanel(formId, comboboxProperties, btns, formPanelConfig, layoutType){
	 var form = Ext.create("Ext.form.Panel", {
	    	id: formId,
			style: 'padding: 0px 0px;margin: 0px 0px;',
			collapsible: formPanelConfig.collapsible,
			collapsed: false,
			resizable: true,
			titleCollapse: formPanelConfig.titleCollapse,
		    buttons: btns,
	        items: [{
						xtype: "container",
						layout: layoutType,
						style: "padding: 0 0 5 0",
						items: createComboboxArrays(comboboxProperties),
					}]
	    });
	 
	 return form;
}

/**
 * 创建时间框组件
 * @param dataObj
 * @returns
 */
function createDateComponent(config){
	var labelNameLength = config.ab ? (config.fl.length+3) : (config.fl.length+2);
	var worldNum = config.ab ? config.fl.getWorldNum()+1 : config.fl.getWorldNum();
	return {
		id: config.id,
		columnWidth: config.columnWidth,
		autoHeight: config.autoHeight,
		xtype:'datefield',
		renderTo: config.renderTo,
		fieldLabel: config.ab ? config.fl : "<span style='color:red'>*</span>"+config.fl,
		/*emptyText: '请选择'+config.fl,*/
		/*emptyText: config.readOnly ? '': '请选择'+config.fl,*/
		margin: config.margin ? config.margin : '5 10 0 0',
		padding: '0 0 0 0',
		labelStyle:"text-align: right;font-size:12px",
		maxValue: config.maxValue,//new Date(new Date()-24*60*60*1000),
		format: config.format ? config.format : 'Y-m-d',
		labelWidth: 'NaN',//12*(labelNameLength - worldNum) + 6*worldNum,
		editable: config.editable,
		readOnly: config.readOnly,
		fieldStyle: config.readOnly ? "background: #C1C1C1" : '',
		allowBlank: config.ab,
		name : config.name,
		submitFormat: config.format ? config.format : 'Y-m-d',
		listeners: config.listenersObj,
		value: config.value ? new Date(config.value) : ""
	};
}
/**
 * 创建时间框组件
 * @param dataObj
 * @returns
 */
function createDateYearComponent(config){
	var labelNameLength = config.ab ? (config.fl.length+3) : (config.fl.length+2);
	var worldNum = config.ab ? config.fl.getWorldNum()+1 : config.fl.getWorldNum();
	return Ext.create('Ext.form.field.Month', {
		id: config.id,
		columnWidth: config.columnWidth,
		autoHeight: config.autoHeight,
		renderTo: config.renderTo,
		fieldLabel: config.ab ? config.fl : "<span style='color:red'>*</span>"+config.fl,
		/*emptyText: '请选择'+config.fl,*/
		/*emptyText: config.readOnly ? '': '请选择'+config.fl,*/
		margin: config.margin ? config.margin : '5 10 0 0',
		padding: '0 0 0 0',
		labelStyle:"text-align: right;font-size:12px",
		maxValue: config.maxValue,//new Date(new Date()-24*60*60*1000),
		format: config.format ? config.format : 'Y-m-d',
		labelWidth: 'NaN',//12*(labelNameLength - worldNum) + 6*worldNum,
		editable: false,
		readOnly: config.readOnly,
		fieldStyle: config.readOnly ? "background: #C1C1C1" : '',
		allowBlank: config.ab,
		name : config.name,
		submitFormat: config.format ? config.format : 'Y-m-d',
		listeners: config.listenersObj,
		value: config.value ? new Date(config.value) : ""
	});
}

function createTextfieldComponent(config){
	var labelNameLength = config.ab ? (config.fl.length+3) : (config.fl+2);
	var worldNum = config.ab ? config.fl.getWorldNum()+1 : config.fl.getWorldNum();
	return {
		id: config.id,
		columnWidth: config.columnWidth,
		autoHeight: config.autoHeight,
		xtype: config.type,
		renderTo: config.renderTo,
		fieldLabel: config.ab ? config.fl : "<span style='color:red'>*</span>"+config.fl,
		/*emptyText: config.tip ? '请输入'+config.fl+config.tip : '请输入'+config.fl,*/
		/*emptyText: config.readOnly ? '': (config.tip ? '请输入'+config.fl+config.tip : '请输入'+config.fl),*/
		margin: config.margin ? config.margin : '5 10 0 0',
		padding: '0 0 0 0',
		labelStyle:"text-align: right;font-size:12px",
		readonly: config.readOnly,
		fieldStyle: config.readOnly ? "background: #C1C1C1" : '',
		hidden:config.hidden?config.hidden:false,
		editable: config.editable,
		labelWidth: 'NaN',//12*(labelNameLength - worldNum) + 6*worldNum,
		allowBlank: config.ab,
		name : config.name,
		value: config.value,
		listeners: config.listenersObj,
		maxLength:config.maxLength?config.maxLength:Number.MAX_VALUE,
    	maxLengthText:config.maxLengthText?config.maxLengthText:'',
    	regex:config.regex?config.regex:null,
        regexText:config.regexText?config.regexText:'',
        enableKeyEvents:true,
	}
}
function createNumberfieldComponent(config){
	var labelNameLength = config.ab ? (config.fl.length+3) : (config.fl+2);
	var worldNum = config.ab ? config.fl.getWorldNum()+1 : config.fl.getWorldNum();
	return {
		id : config.id,
		columnWidth: config.columnWidth,
		autoHeight: config.autoHeight,
		xtype: config.type,
		fieldLabel : config.ab ? config.fl : "<span style='color:red'>*</span>"+config.fl,
		name : config.name,
		labelAlign: 'right',
		margin: config.margin ? config.margin : '5 10 0 0',
		padding: '0 0 0 0',
		labelStyle:"text-align: right; font-size:12px",
		labelWidth: 'NaN',//11*(labelNameLength - worldNum) + 6*worldNum,
		allowBlank: config.ab,
		editable: config.editable,
		minValue: config.minValue,
		maxLength : config.maxLength,
		allowDecimals: config.allowDecimals,
		decimalPrecision: config.dp,
		/*emptyText: config.tip ? '请输入'+config.fl+config.tip : '请输入'+config.fl,*/
		/*emptyText: config.readOnly ? '': (config.tip ? '请输入'+config.fl+config.tip : '请输入'+config.fl),*/
		regex: config.regex, ///^[0-9]{0,}$/
		regexText: config.regexText,
		value: config.value,
		readonly: config.readOnly,
		fieldStyle: config.readOnly ? "background: #C1C1C1" : '',
		listeners: config.listenersObj
	}
}
function createFilefieldComponent(config) {
	
	return {
		xtype:'filefield',
		id: "fileUpload",
		margin: '10 0 0 30',
		buttonText: config.buttonText,
		width: config.width,
		name: config.name,
		listeners: config.listeners
	}
}
function createComboboxArrays(configs){
	var comboboxs = [];
	for (var i = 0; i < configs.length; i++) {
		if(configs[i].type == 'combobox'){
			comboboxs.push(createCombobox(configs[i]));
		}else if(configs[i].type == 'textfield'){
			comboboxs.push(createTextfieldComponent(configs[i]))
		}else if(configs[i].type == 'numberfield'){
			comboboxs.push(createNumberfieldComponent(configs[i]))
		}else if(configs[i].type == 'checkboxgroup'){
			comboboxs.push(createCheckBox(configs[i]))
		}else if(configs[i].type == 'radiogroup'){
			comboboxs.push(createRadio(configs[i]))
		}else if(configs[i].type == 'yearfield'){
			comboboxs.push(createDateYearComponent(configs[i]))
		}else if(configs[i].type == 'filefield'){
			comboboxs.push(createFilefieldComponent(configs[i]))
		}else{
			comboboxs.push(createDateComponent(configs[i]))
		}
	}
	
	return comboboxs;
}

/**
 * 列对象
 * @param text
 * @param dataIndexOrSubColumn
 * @returns
 */
function Column(text, dataIndexOrSubColumn, typeObj){
	this.text = text;
	this.width = typeObj.width;
	this.align =typeObj.align?typeObj.align:'left';
	if(typeObj.editor && typeObj.type == 'selectfield'){
		this.field= new Ext.form.ComboBox({
				name: typeObj.name,
				queryMode: 'local',
				editable: false,//不允许输入数据集合中没有的数值
		        valueField: typeObj.valueField, 
			    displayField: typeObj.displayField, 
			    store: typeObj.store,
			    listeners: {
			    	'select' : function(o, v){
			    		/*console.log(v.data[this.displayField])*/
			    		if(v.data[this.displayField] == "自有财产承担"){ 
			    			setValue("中国对外经济贸易信托有限公司", "0200048519200530321", "中国工商银行北京复外支行", false)
			    		}else if(v.data[this.displayField] == "信托财产承担"){ 
			    			$.ajax({
			    				url: webRoot+"/insuranceFunds/queryAccountNoAndNameAndOpenbankWithPaymentWay",
			    				data: {"projectId": currentEditRecordData.projectId},
			    				type: 'POST',
			    				success: function(msg){
			    					/*console.log(msg);*/
			    					setValue(msg.accountName, msg.accountNum, msg.openBank, false)
			    				}
			    			})
			    		}else{ //第三方承担
			    			setValue("", "", "", true);
			    		}
			    	}
			    }
		    })
	}else if(typeObj.editor){
		this.editor={
					xtype: typeObj.editType ? typeObj.editType : typeObj.type,
		        	allowBlank: typeObj.allowBlank != undefined ? typeObj.allowBlank : true,
        			listeners: typeObj.listenersObj,
        			format: 'Y-m-d'
				}
	}else{
		this.setEditor = false;
	}
	if(typeObj.type == 'datefield' || typeObj.editType == 'datefield'){
		this.renderer = function(val){
			return dateFormat(val, typeObj.format);
		}
	}else{
		this.renderer = typeObj.renderer;
	}
	if(typeObj.type == 'numberfield' && typeObj.precision){
		this.allowDecimals = true
		this.renderer = function(val){
			
           return val.toFixed(typeObj.precision);
		}
	}
	
	if(dataIndexOrSubColumn instanceof Array){
		this.columns = dataIndexOrSubColumn
	}else{
		this.dataIndex = dataIndexOrSubColumn;
		this.id = dataIndexOrSubColumn;
	}
}
function setValue(v1,v2, v3, editable){
	$("input[name='accountName']").val(v1);
	$("input[name='accountNum']").val(v2);
	$("input[name='openBank']").val(v3);
	if(editable){
		$("input[name='accountName']").removeAttr('disabled');
		$("input[name='accountNum']").removeAttr('disabled');
		$("input[name='openBank']").removeAttr('disabled');
	}else{
		$("input[name='accountName']").attr('disabled', 'disabled')
		$("input[name='accountNum']").attr('disabled', 'disabled')
		$("input[name='openBank']").attr('disabled', 'disabled')
	}
}

function createColumns(columnTitleAndFiled){
	var columnArray = [];
	for (var i = 0; i < columnTitleAndFiled.length; i++) {
		var columns = columnTitleAndFiled[i];
		if(columns[1] instanceof Array){
			columnArray.push(new Column(columns[0], createColumns(columns[1]), columns[2]));
		}else{
			columnArray.push(new Column(columns[0], columns[1], columns[2]));
		}
	}
	 
	return columnArray;
}

/**
 * 创建gridtable
 * @param storeUrl  数据地址
 * @param columnTitleAndFiled  列表题和字段
 * @returns
 */
function createGridTablePanel(gridConfig, pluginsObj, listenersObj){
	var selModel = Ext.create('Ext.selection.CheckboxModel', { 
	     columns: [{xtype : 'checkcolumn', text : 'Active', dataIndex : 'id'}], 
	     checkOnly: false, 
	     mode: gridConfig.mode ? gridConfig.mode : 'multi', 
	     enableKeyNav: false,
	     listeners: {
	    	 selectionchange: function(value, meta, record, row, rowIndex, colIndex){ 
	    	 }
	     }
	}); 

	
	return Ext.create('Ext.grid.Panel', {
		autoWidth: true,
		columnLines: true,
		renderTo: gridConfig.renderTo,
		height: gridConfig.height,
		plugins: pluginsObj,
        store: gridConfig.store,
	    viewConfig:{  
	    	   enableTextSelection:gridConfig.enableTextSelection?true:false,
			   getRowClass: gridConfig.rowColor==null?null:gridConfig.rowColor
	    },
        columns: createColumns(gridConfig.columns),
        selModel: gridConfig.checkboxable?selModel:null,
        bbar: Ext.create('Ext.PagingToolbar', {
        	store: gridConfig.store,
        	displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
        	emptyMsg: "没有数据",
            beforePageText: "当前页",
            afterPageText: "共{0}页",
            displayInfo: true,
            style: {
            	position: 'absolute !important',
        		bottom: '0px'
            }
        }),
        listeners: listenersObj
    });
}

Array.prototype.indexOfByObj = function (o) {
    for (var i = 0; i < this.length; i++) {
        if (o.id != null && this[i].id == o.id) return i;
    }
    return -1;
};
Array.prototype.remove = function (o) {
    var index = this.indexOfByObj(o);
    if (index > -1) {
        this.splice(index, 1);
    }
};

function assemblerRequestExportUrl(params, url){
	for(var key in params){
		if((params[key] instanceof Array && params[key].length > 0) || (!(params[key] instanceof Array ) && params[key])){
			/*console.log(params[key] instanceof Date);*/
			if(params[key] instanceof Date){
				url += "&"+key+"="+dateFormat(params[key]);
			}else{
				url += "&"+key+"="+params[key];
			}
		}
	}
	return url;
}

/**
 * 创建ViewPort布局
 * @param form  form表单
 * @param grid  数据table
 * @returns
 */
function createViewPort(form, grid, title, iframe){
	var items = [{  title : '',
			region : 'north',
			layout : 'form',
			items : [ form ]
		},{
			region : 'center',
			layout : 'fit',
			flex : 1,
			items : [ grid ],
			split : true
		}]
	if(iframe){
		items[1].region = 'south';
		items.push({
			region : 'center',
			layout : 'fit',
			flex : 1,
			items : [iframe],
			split : true
		})
	}
	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		title : title,
		items : items
	});
}

function createBtn(status){
	var btnMyReport =  new Ext.Button({
		text: status == "0" ? "取消收藏" : "收藏",
		listeners : {
			'beforerender' : function() {
				if(reportButtons.indexOf('收藏')!=-1){
					this.hidden=false;
				}else{
					this.hidden=true;
				}
				 
			}
		},
		handler: function(){
			var loadingShaowIndex;
			$.ajax({
				url: webRoot+"/myStatements/statementsEnshrine",
				type: 'POST',
				data: {reportId: reportId},
				beforeSend: function () {  
					loadingShaowIndex = layer.load(0, {shade: [0.01, '#fff']}); 
		        },
				success: function(result){
					layer.close(loadingShaowIndex);
					if(status == "0"){
						status = "1";
						btnMyReport.setText("收藏");
						$("#my"+reportId,parent.document).remove();
					}else{
						status = "0";
						btnMyReport.setText("取消收藏");
						var reportName = $("#tabid"+reportId,parent.document).text();
						var addHtml = '<li id="my'+reportId+'" mid="tab'+reportId+'" funurl=""><a tabindex="-1"  onclick="queryReportDetail(\''+reportId+'\',\''+reportCode+'\',\''+pageUrl+'\',0,this)" href="javascript:void(0);">'+reportName+'</a></li>';
						$("#myStatementUlId",parent.document).append(addHtml);
    					parent.addTab();
					}
					layer.alert(result.msg);
				},
				error: function(){
					layer.alert("操作失败！请联系管理员")
				}
			})
		}
	});
	
	return btnMyReport;
}
/**
 * 创建弹窗
 * @param title
 * @param form
 * @param w
 * @param h
 * @param btnName
 * @param btnHandler
 * @returns
 */
function createWin(title, items, w, h, btnName, btnHandler, scrollable){
	var btns = []
	if(btnName){
		btns.push({
			text: btnName,
			handler:function(){
				btnHandler()
			}
		});
	}
	
	btns.push({
		text: '关闭',
		handler:function(){
			win.close();
		}
	});
	
	var win = new Ext.Window({
		title: title,
		items:items,//嵌入表单;
		width: w,
		height: h,
		autoScroll: scrollable ? scrollable : false,
		draggable:false,
		draggable:false,
		resizable:false,
		modal:true,
		buttons: btns
	});
	return win;
}

function sendUpdateRequest(url, params, popBox){
	var loadingShaowIndex;
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function () {  
			loadingShaowIndex = layer.load(0, {shade: [0.01, '#fff']}); 
        },
		data: JSON.stringify(params),
		contentType: 'application/json',
		dataType:'JSON',
		success: function(result){
			layer.close(loadingShaowIndex);
			layer.alert(result.msg);
			if(result.msg.indexOf("成功")!=-1){
				popBox.close();
				maingrid.getStore().reload();
			}
		},
		error: function(xhr){
			layer.close(loadingShaowIndex);
			layer.alert("请求失败！");
		}
	})
}
function dateFormat(value, pattern){ 
    if(null != value){ 
        return Ext.Date.format(new Date(value), pattern ? pattern : 'Y-m-d'); 
    }else{ 
        return null; 
    } 
}

function dateFormatMonth(value){
	if(null != value){
		return Ext.Date.format(new Date(value),'m');
	}else{
		return null;
	}
}