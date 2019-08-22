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
			    fields: ['num', 'datee', 'exchangeRate']
			});
	    	var fields = ['num', 'datee', 'exchangeRate']

	    	store = getStore({type: "remote", url: "/crs/dataList", fields: fields}, true);

	    	var columnTitleAndField = [
				//['系统用户账号', 'reportingId', {"type": 'textfield'}],
		        //['金融机构注册码', 'fiId', {"type": 'textfield'}],
		        //['报告类型', 'reportingType', {"type": 'textfield'}],
		        ['报告唯一编码', 'messageRefId', {"type": 'textfield'}],
		        ['数据所属公立年度', 'reportingPeriod', {"type": 'datefield', format: 'Y-m-d'}],
		        ['报告类型', 'messageTypeIndic', {"type": 'textfield'}],
		        //['报告生成时间戳', 'tmstp', {"type": 'datefield', "format": "Y-m-d H:i:s"}],
		        ['账户记录编号', 'docRefId', {"type": 'textfield', "width": 260}],
		        ['账户报告的类型', 'docTypeIndic', {"type": 'textfield'}],
		        ['账号', 'accountNumber', {"type": 'textfield', "width": 200}],
		        ['账户类别', 'dueDiligenceInd', {"type": 'textfield'}],
		        ['是否取得账户持有人的自证声明', 'selfCertification', {"type": 'textfield'}],
		        ['账户持有人类别', 'accountHolderType', {"type": 'textfield'}],
		        ['报送状态', 'reportStatus', {"type": 'textfield'}],
		        ['错误代码', 'errCode', {"type": 'textfield'}],
		    ];
	    	
	    	 //表单
		    var gridConfig = {store: store, columns: columnTitleAndField, renderTo: "center-container", height:'100%', checkboxable: true, enableTextSelection: true}
	
		    var dataDetailDbClick = buildFundPoolInfoListenersObj();
		    grid = createGridTablePanel(gridConfig, null, dataDetailDbClick);
		    parent.maingrid = grid;
		    parent.datalistGrid = grid;
	    });
	    
	    function inserRow(){
	    	console.log(store)
	    	var ransom = new exchangeRate({num: store.totalCount})
	    	store.insert(store.getCount(), ransom);
	    }
	    
	  	//数据明细
	    function buildFundPoolInfoListenersObj(){
	    	return {
	    		celldblclick: function(el, td, cellIndex, record, tr, rowIndex, e, eOpts){
	    			var data = record.data;
	    			var comboboxConfiguration = [
		    				{id: "fundpool-dgq-1", type: "textfield", fl:"系统用户账号" ,name: "reportingId", value: data.reportingId,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-2", type: "textfield", fl:"金融机构注册码" ,name: "fiId", value: data.fiId,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-3", type: "textfield", fl:"报告类型" ,name: "reportingType", value: data.reportingType,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-4", type: "textfield", fl:"报告唯一编码" ,name: "messageRefId", value: data.messageRefId,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-5", type: "datefield", fl:"数据所属公立年度", name: "reportingPeriod", value: data.reportingPeriod,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-6", type: "textfield", fl:"报告类型" ,name: "messageTypeIndic", value: data.messageTypeIndic,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-7", type: "datefield", fl:"报告生成时间戳", name: "tmstp", value: data.tmstp,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-8", type: "textfield", fl:"账户记录编号" ,name: "docRefId", value: data.docRefId,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-9", type: "textfield", fl:"被修改会删除的账户记录编号" ,name: "corrDocRefId", value: data.corrDocRefId,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-10", type: "textfield", fl:"账户报告的类型" ,name: "docTypeIndic", value: data.docTypeIndic,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-11", type: "textfield", fl:"账号" ,name: "accountNumber", value: data.accountNumber,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-12", type: "textfield", fl:"账户是否注销" ,name: "closedAccount", value: data.closedAccount,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-13", type: "textfield", fl:"账户类别" ,name: "dueDiligenceInd", value: data.dueDiligenceInd,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-14", type: "textfield", fl:"是否取得自证声明" ,name: "selfCertification", value: data.selfCertification,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-15", type: "numberfield", fl:"账户余额" ,name: "accountBalance", value: data.accountBalance,ab: true,editable: true,columnWidth: .3, autoHeight: true, dp: 2, allowDecimals: true},
		    				{id: "fundpool-dgq-16", type: "textfield", fl:"账户余额的货币代码" ,name: "accountBalanceCurrCode", value: data.accountBalanceCurrCode,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-17", type: "textfield", fl:"账户持有人类别" ,name: "accountHolderType", value: data.accountHolderType,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-18", type: "textfield", fl:"开户金融机构名称" ,name: "openingFiname", value: data.openingFiname,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-19", type: "textfield", fl:"收入类型" ,name: "paymentType", value: data.paymentType,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-20", type: "numberfield", fl:"收入金额" ,name: "paymentAmnt", value: data.paymentAmnt,ab: true,editable: true,columnWidth: .3, autoHeight: true, dp: 2, allowDecimals:true},
		    				{id: "fundpool-dgq-21", type: "textfield", fl:"收入金额的货币代码" ,name: "paymentAmntCurrCode", value: data.paymentAmntCurrCode,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-22", type: "textfield", fl:"账户持有人（个人）姓名类型" ,name: "nameType", value: data.nameType,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-23", type: "textfield", fl:"账户持有人（个人）英文姓" ,name: "lastName", value: data.lastName,ab: true, editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-24", type: "textfield", fl:"账户持有人（个人）英文中间名" ,name: "middleName", value: data.middleName,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-25", type: "textfield", fl:"账户持有人（个人）英文名" ,name: "firstName", value: data.firstName,ab: true, editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-26", type: "textfield", fl:"账户持有人（个人）中文姓名" ,name: "nameCn", value: data.nameCn,ab: true, editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-27", type: "textfield", fl:"账户持有人（个人）姓名前缀" ,name: "precedingTitle", value: data.precedingTitle,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-28", type: "textfield", fl:"账户持有人（个人）称谓二" ,name: "title", value: data.title,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-29", type: "textfield", fl:"账户持有人（个人）称谓三" ,name: "namePrefix", value: data.namePrefix,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-30", type: "textfield", fl:"账户持有人（个人）称谓四" ,name: "generationIdentifier", value: data.generationIdentifier,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-31", type: "textfield", fl:"账户持有人（个人）称谓五" ,name: "suffix", value: data.suffix,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-32", type: "textfield", fl:"账户持有人（个人）称谓六" ,name: "generalSuffix", value: data.generalSuffix,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-33", type: "textfield", fl:"机构名称类型（账户持有人）" ,name: "orgNameType", value: data.orgNameType,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-34", type: "textfield", fl:"机构英文名（账户持有人）" ,name: "organisationNameEn", value: data.organisationNameEn,ab: true,editable: data.accountHolderType != 'CRS100'?true:false, readOnly: data.accountHolderType != 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-35", type: "textfield", fl:"机构中文名（账户持有人）" ,name: "organisationNameCn", value: data.organisationNameCn,ab: true,editable: data.accountHolderType != 'CRS100'?true:false, readOnly: data.accountHolderType != 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-36", type: "textfield", fl:"账户持有人（个人）性别" ,name: "gender", value: data.gender,ab: true, editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-37", type: "textfield", fl:"地址类型" ,name: "legaladdressType", value: data.legaladdressType,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-38", type: "textfield", fl:"现居住国家代码" ,name: "countryCode", value: data.countryCode,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-39", type: "textfield", fl:"自由模式填写的英文地址" ,name: "addressFreeEn", value: data.addressFreeEn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-40", type: "textfield", fl:"城市" ,name: "cityEn", value: data.cityEn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-41", type: "textfield", fl:"街道" ,name: "street", value: data.street,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-42", type: "textfield", fl:"楼号" ,name: "buildingIdentifier", value: data.buildingIdentifier,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-43", type: "textfield", fl:"房门号" ,name: "suiteIdentifier", value: data.suiteIdentifier,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-44", type: "textfield", fl:"楼层" ,name: "floorIdentifier", value: data.floorIdentifier,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-45", type: "textfield", fl:"区" ,name: "districtName", value: data.districtName,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-46", type: "textfield", fl:"邮箱" ,name: "pob", value: data.pob,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-47", type: "textfield", fl:"邮编" ,name: "postCode", value: data.postCode,ab: true, editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-48", type: "textfield", fl:"省市/国外行政区划" ,name: "countrySubentity", value: data.countrySubentity,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-49", type: "textfield", fl:"自由模式中文详细地址" ,name: "addressFreeCn", value: data.addressFreeCn,ab: true, editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-50", type: "textfield", fl:"省级行政区划代码" ,name: "province", value: data.province,ab: true, editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-51", type: "textfield", fl:"地市级行政区划代码" ,name: "cityCn", value: data.cityCn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-52", type: "textfield", fl:"县级行政区划代码" ,name: "districtNameCn", value: data.districtNameCn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-53", type: "textfield", fl:"邮编" ,name: "postCodeCn", value: data.postCodeCn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-54", type: "textfield", fl:"联系电话" ,name: "phoneNo", value: data.phoneNo,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-55", type: "textfield", fl:"身份证件类型（个人）" ,name: "idType", value: data.idType,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-56", type: "textfield", fl:"身份证件号码（个人）" ,name: "idNumber", value: data.idNumber,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-57", type: "textfield", fl:"税收居民国代码" ,name: "resCountryCode", value: data.resCountryCode,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-58", type: "textfield", fl:"发放识别号的国家代码" ,name: "issuedBy", value: data.issuedBy,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-59", type: "textfield", fl:"识别号类型 " ,name: "inType", value: data.inType,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-60", type: "textfield", fl:"纳税人识别号" ,name: "tin", value: data.tin,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-61", type: "textfield", fl:"不能提供纳税人识别号的理由" ,name: "explanation", value: data.explanation,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-62", type: "textfield", fl:"国籍代码（个人）） " ,name: "nationality", value: data.nationality,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-63", type: "datefield", fl:"出生日期（个人））", name: "birthDate", value:data.birthDate,ab: true,editable: true, readOnly: false,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-64", type: "textfield", fl:"出生城市（个人））" ,name: "city", value: data.city,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-65", type: "textfield", fl:"出生国代码（个人））" ,name: "birthCountryCode", value: data.birthCountryCode,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-66", type: "textfield", fl:"账户持有人出生国英文名称（出生国不在ISO3166定义范围内）" ,name: "formerCountryName", value: data.formerCountryName,ab: true,editable: data.accountHolderType == 'CRS100'?true:false, readOnly: data.accountHolderType == 'CRS100'?false:true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-67", type: "textfield", fl:"姓名类型（控制人）" ,name: "nameType2", value: data.nameType2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-68", type: "textfield", fl:"英文姓（控制人）" ,name: "lastName2", value: data.lastName2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-69", type: "textfield", fl:"英文中间名（控制人）" ,name: "middleName2", value: data.middleName2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-70", type: "textfield", fl:"英文名（控制人）" ,name: "firstName2", value: data.firstName2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-71", type: "textfield", fl:"中文姓名（控制人）" ,name: "nameCn2", value: data.nameCn2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-72", type: "textfield", fl:"称谓1（控制人）" ,name: "precedingTitle2", value: data.precedingTitle2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-73", type: "textfield", fl:"称谓2（控制人）" ,name: "title2", value: data.title2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-74", type: "textfield", fl:"前缀（控制人）" ,name: "namePrefix2", value: data.namePrefix2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-75", type: "textfield", fl:"称谓3（控制人）" ,name: "generationIdentifier2", value: data.generationIdentifier2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-76", type: "textfield", fl:"称谓4（控制人）" ,name: "suffix2", value: data.suffix2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-77", type: "textfield", fl:"称谓5（控制人）" ,name: "generalSuffix2", value: data.generalSuffix2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-78", type: "textfield", fl:"控制人类型" ,name: "ctrlgPersonType", value: data.ctrlgPersonType,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-79", type: "textfield", fl:"国籍（控制人）" ,name: "nationality2", value: data.nationality2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-80", type: "textfield", fl:"地址类型（控制人类型）" ,name: "legalAddressType2", value: data.legalAddressType2,ab: true, editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-81", type: "textfield", fl:"国家代码（控制人）" ,name: "countryCode2", value: data.countryCode2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-82", type: "textfield", fl:"自由模式英文地址（控制人）" ,name: "addressFreeEn2", value: data.addressFreeEn2,ab: true, editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-83", type: "textfield", fl:"城市（控制人）" ,name: "cityEn2", value: data.cityEn2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-84", type: "textfield", fl:"街道（控制人）" ,name: "street2", value: data.street2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-85", type: "textfield", fl:"楼号（控制人）" ,name: "buildingIdentifier2", value: data.buildingIdentifier2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-86", type: "textfield", fl:"房门号（控制人）" ,name: "suiteIdentifier2", value: data.suiteIdentifier2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-87", type: "textfield", fl:"楼层（控制人）" ,name: "floorIdentifier2", value: data.floorIdentifier2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-88", type: "textfield", fl:"区（控制人）" ,name: "districtName2", value: data.districtName2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-89", type: "textfield", fl:"邮箱（控制人）" ,name: "pob2", value: data.pob2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-90", type: "textfield", fl:"邮编（控制人）" ,name: "postCode2", value: data.postCode2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-91", type: "textfield", fl:"省市/国外行政区划（控制人） " ,name: "countrySubentity2", value: data.countrySubentity2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-92", type: "textfield", fl:"自由模式中文地址（控制人）" ,name: "addressFreeCn2", value: data.addressFreeCn2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-93", type: "textfield", fl:"省级行政区划代码（控制人）" ,name: "province2", value: data.province2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-94", type: "textfield", fl:"地市级行政区划代码（控制人）" ,name: "cityCn2", value: data.cityCn2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-95", type: "textfield", fl:"县级行政区划代码（控制人）" ,name: "districtName2Cn", value: data.districtName2Cn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-96", type: "textfield", fl:"邮编2（控制人）" ,name: "postCode2Cn", value: data.postCode2Cn,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-97", type: "textfield", fl:"出生国代码（控制人）" ,name: "birthCountryCode2", value: data.birthCountryCode2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-98", type: "datefield", fl:"出生日期（控制人）" ,name: "birthDate2",ab: true,editable: true, value: data.birthDate2,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-99", type: "textfield", fl:"出生城市（控制人)" ,name: "city2", value: data.city2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-100", type: "textfield", fl:"发放识别号国家代码（控制人）" ,name: "issuedBy2", value: data.issuedBy2,ab: true,editable: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-101", type: "textfield", fl:"识别号类型(控制人)" ,name: "inType2", value: data.inType2,ab: true,editable: false, readOnly: true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-102", type: "textfield", fl:"纳税人识别号（控制人)" ,name: "tin2", value: data.tin2,ab: true,editable:  true,columnWidth: .3, autoHeight: true},
		    				{id: "fundpool-dgq-103", type: "textfield", fl:"税收居民国代码（控制人）" ,name: "resCountryCode2", value: data.resCountryCode2,ab: true,editable: true,columnWidth: .3, autoHeight: true},		    				
		    				{id: "fundpool-dgq-104", type: "textfield", fl:"出生国英文名称（出生国不在ISO3166定义范围内)（控制人）" ,name: "formerCountryName2", value: data.formerCountryName2,ab: true,editable: true,columnWidth: .3, autoHeight: true},	
		    				{id: "fundpool-dgq-105", type: "textfield", fl:"不能提供纳税人识别号的理由（控制人）" ,name: "explanation2", value: data.explanation2,ab: true,editable: true,columnWidth: .3, autoHeight: true}
	    			];
	    			var fundPoolInfoform = createFormPanel("fundpool_formPanel1", comboboxConfiguration, [], {titleCollapse: false}, 'column');
	    			
	    			var popBox = createWin("数据明细", [fundPoolInfoform],1240, 360, "保存", function(){
	    				if(fundPoolInfoform.isValid()) {
		    				var formData = fundPoolInfoform.getForm().getFieldValues();
		    				formData["id"] = data.id;
		    				console.log(data)
		    				console.log(formData)
		    				var submitData = {logDataArr: objectValueCompare(data, formData), updateData: data};
		    				console.log(submitData)
		    				sendUpdateRequest(webRoot+"/crs/update", submitData, popBox);
	    				}
	    			}, true).show();
	    		}
	    	}
	    }
	  	function objectValueCompare(o1, o2){
	  		var arr = [];
	  		for(var p in o1) {
	  			if(!isEqual(o1[p],o2[p])){
	  				debugger;
	  				var o = {};
	  				o.editFiled = p;
	  				o.accountNum = o2.accountNumber;
	  				o.reportingPeriod = o2.reportingPeriod;
	  				o.editBeforeValue = o1[p];
	  				o.editAfterValue = o2[p];
					if(p.indexOf("birthDate") != -1){
						o.editBeforeValue = o1[p] == null ? null : dateFormat(o1[p])
						o.editAfterValue = o2[p] == null ?null : dateFormat(o2[p])
	  				}
	  				
	  				o1[p] = o2[p];
	  				arr.push(o)
	  			}
	  		}
	  		
	  		return arr;
	  	}
	  	function isEqual(v1, v2){
	  		if(v1 === "" || v1 === null || v1 === undefined){
	  			if(v2 === "" || v2 === null || v2 === undefined)return true;
	  		}
	  		
	  		if(v2 instanceof Date){
	  			console.log(v2.getTime());
	  			console.log(v1 === v2.getTime());
	  			return v1 === v2.getTime();
	  		}
	  		if(typeof v2 === 'number') {
	  			return Number(v1) === v2;
	  		}
	  		return v1 === v2;
	  	}
	</script>
</body>
</html>