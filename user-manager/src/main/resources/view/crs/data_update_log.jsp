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
	    	Ext.define('datEditLog', {
			    extend: 'Ext.data.Model',
			    fields: ['userid', 'operatorname', 'accountNum', 'editFiled', 'editBeforeValue', 'editAfterValue', 'updateDate']
			});
	    	var fields = ['id', 'userid', 'operatorname', 'accountNum', 'editFiled', 'editBeforeValue', 'editAfterValue', 'updateDate']

	    	store = getStore({type: "remote", url: "/crs/dataEditLogList", fields: fields}, true);

	    	var columnTitleAndField = [
				['操作人', 'operatorname', {"type": 'textfield', "editor": false}],
		        ['账号', 'accountNum', {"type": 'textfield', "editor": false}],
		        ['修改字段', 'editFiled',{"type": 'textfield', "editor": false, renderer:function(val){return getChineseValue(val)}}],
		        ['修改前的值', 'editBeforeValue',{"type": 'textfield', "editor": false}],
		        ['修改后的值', 'editAfterValue',{"type": 'textfield', "editor": false}],
		        ['修改时间', 'updateDate',{"type": 'datefield', "editor": false, format: 'Y-m-d h:i:s', width: 180}]
		    ];
	    	
	    	 //表单
		    var gridConfig = {store: store, columns: columnTitleAndField, renderTo: "center-container", height:'100%'}
	
		    grid = createGridTablePanel(gridConfig);
		    parent.maingrid = grid;
		    parent.editLogGrid = grid;
	    });
	    
	    function getChineseValue(key){
	    	return {"reportingId":"系统用户账号",
	    			"fiId":"金融机构注册码",
	    			"reportingType":"报告类型",
	    			"messageRefId":"报告唯一编码",
	    			"reportingPeriod":"数据所属公立年度",
	    			"messageTypeIndic":"报告类型",
	    			"tmstp":"报告生成时间戳",
	    			"docRefId":"账户记录编号",
	    			"corrDocRefId":"被修改会删除的账户记录编号",
	    			"docTypeIndic":"账户报告的类型",
	    			"accountNumber":"账号",
	    			"closedAccount":"账户是否注销",
	    			"dueDiligenceInd":"账户类别",
	    			"selfCertification":"是否取得自证声明",
	    			"accountBalance":"账户余额",
	    			"accountBalanceCurrCode":"账户余额的货币代码",
	    			"accountHolderType":"账户持有人类别",
	    			"openingFiname":"开户金融机构名称",
	    			"paymentType":"收入类型",
	    			"paymentAmnt":"收入金额",
	    			"paymentAmntCurrCode":"收入金额的货币代码",
	    			"nameType":"账户持有人（个人）姓名类型",
	    			"lastName":"账户持有人（个人）英文姓",
	    			"middleName":"账户持有人（个人）英文中间名",
	    			"firstName":"账户持有人（个人）英文名",
	    			"nameCn":"账户持有人（个人）中文姓名",
	    			"precedingTitle":"账户持有人（个人）姓名前缀",
	    			"title":"账户持有人（个人）称谓二",
	    			"namePrefix":"账户持有人（个人）称谓三",
	    			"generationIdentifier":"账户持有人（个人）称谓四",
	    			"suffix":"账户持有人（个人）称谓五",
	    			"generalSuffix":"账户持有人（个人）称谓六",
	    			"orgNameType":"机构名称类型（账户持有人）",
	    			"organisationNameEn":"机构英文名（账户持有人）",
	    			"organisationNameCn":"机构中文名（账户持有人）",
	    			"gender":"账户持有人（个人）性别",
	    			"legaladdressType":"地址类型",
	    			"countryCode":"现居住国家代码",
	    			"addressFreeEn":"自由模式填写的英文地址",
	    			"cityEn":"城市",
	    			"street":"街道",
	    			"buildingIdentifier":"楼号",
	    			"suiteIdentifier":"房门号",
	    			"floorIdentifier":"楼层",
	    			"districtName":"区",
	    			"pob":"邮箱",
	    			"postCode":"邮编",
	    			"countrySubentity":"省市/国外行政区划",
	    			"addressFreeCn":"自由模式中文详细地址",
	    			"province":"省级行政区划代码",
	    			"cityCn":"地市级行政区划代码",
	    			"districtNameCn":"县级行政区划代码",
	    			"postCodeCn":"邮编",
	    			"phoneNo":"联系电话",
	    			"idType":"身份证件类型（个人）",
	    			"idNumber":"身份证件号码（个人）",
	    			"resCountryCode":"税收居民国代码",
	    			"issuedBy":"发放识别号的国家代码",
	    			"inType":"识别号类型",
	    			"tin":"纳税人识别号",
	    			"explanation":"不能提供纳税人识别号的理由",
	    			"nationality":"国籍代码（个人））",
	    			"birthDate":"出生日期（个人））",
	    			"city":"出生城市（个人））",
	    			"birthCountryCode":"出生国代码（个人））",
	    			"formerCountryName":"账户持有人出生国英文名称（出生国不在ISO3166定义范围内）",
	    			"nameType2":"姓名类型（控制人）",
	    			"lastName2":"英文姓（控制人）",
	    			"middleName2":"英文中间名（控制人）",
	    			"firstName2":"英文名（控制人）",
	    			"nameCn2":"中文姓名（控制人）",
	    			"precedingTitle2":"称谓1（控制人）",
	    			"title2":"称谓2（控制人）",
	    			"namePrefix2":"前缀（控制人）",
	    			"generationIdentifier2":"称谓3（控制人）",
	    			"suffix2":"称谓4（控制人）",
	    			"generalSuffix2":"称谓5（控制人）",
	    			"ctrlgPersonType":"控制人类型",
	    			"nationality2":"国籍（控制人）",
	    			"legalAddressType2":"地址类型（控制人类型）",
	    			"countryCode2":"国家代码（控制人）",
	    			"addressFreeEn2":"自由模式英文地址（控制人）",
	    			"cityEn2":"城市（控制人）",
	    			"street2":"街道（控制人）",
	    			"buildingIdentifier2":"楼号（控制人）",
	    			"suiteIdentifier2":"房门号（控制人）",
	    			"floorIdentifier2":"楼层（控制人）",
	    			"districtName2":"区（控制人）",
	    			"pob2":"邮箱（控制人）",
	    			"postCode2":"邮编（控制人）",
	    			"countrySubentity2":"省市/国外行政区划（控制人）",
	    			"addressFreeCn2":"自由模式中文地址（控制人）",
	    			"province2":"省级行政区划代码（控制人）",
	    			"cityCn2":"地市级行政区划代码（控制人）",
	    			"districtName2Cn":"县级行政区划代码（控制人）",
	    			"postCode2Cn":"邮编2（控制人）",
	    			"birthCountryCode2":"出生国代码（控制人）",
	    			"birthDate2":"出生日期（控制人）",
	    			"city2":"出生城市（控制人)",
	    			"issuedBy2":"发放识别号国家代码（控制人）",
	    			"inType2":"识别号类型(控制人)",
	    			"tin2":"纳税人识别号（控制人)",
	    			"resCountryCode2":"税收居民国代码（控制人）",
	    			"formerCountryName2":"出生国英文名称（出生国不在ISO3166定义范围内)（控制人）",
	    			"explanation2":"不能提供纳税人识别号的理由（控制人）"}[key]
	    }
	</script>
</body>
</html>