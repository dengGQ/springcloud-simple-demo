/**
 * 把校验编码转换成对于的中文含义
 * @param code 编码
 */
function getValidateStatus(code) {
	var value = "未知";
	 if(code==1){
		 value = "未校验";
	 }else if(code==2){
		 value = "校验通过";
	 }else if(code==3){
		 value = "校验未通过";
	 }
	 return value;
}

/**
 * 数据来源
 * @param code 编码
 * @returns
 */
function getDataSrc(code){
	var value = "未知";
	
	 if(code==1){
		 value = "信贷";
	 }else {
		 value = "CSV";
	 }
	 return value;
}

/**
 * 检查目标类型
 * @param type 类型
 * @returns
 */
function getCheckType(type){
	var value = "未知";
	if(type==1){
		value = "交易信息";
	}else if(value==2){
		value = "客户信息";
	}else if(value==3){
		value = "特殊交易信息";
	}
	return value;
}

/**
 * 客户类型
 * @param type
 * @returns
 */
function getCustType(type){
	var value = "未知";
	if(type=="01"){
		value = "农户";
	}else if(type=="02"){
		value = "工薪";
	}else if(type=="03"){
		value = "个体工商户";
	}else if(type=="04"){
		value = "学生";
	}else if(type=="99"){
		value = "其他";
	}
	return value;
}

/**
 * 身份证类型
 * @param type
 * @returns
 */
function getCredType(type){
	var value = "未知";
	if(type==0){
		value = "身份证";
	}else if(type==1){
		value = "户口簿";
	}else if(type==2){
		value = "护照";
	}else if(type==3){
		value = "军官证";
	}else if(type==4){
		value = "士兵证";
	}else if(type==5){
		value = "港澳居民来往内地通行证";
	}else if(type==6){
		value = "台湾同胞来往内地通行证";
	}else if(type==7){
		value = "临时身份证";
	}else if(type==8){
		value = "外国人居留证";
	}else if(type==9){
		value = "警官证";
	}else if(type=="X"){
		value = "其他证件";
	}
	return value;
}

/**
 * 婚姻状态
 * @param type
 * @returns
 */
function getMarriage(type){
	var value = "未说明的婚姻状况";
	if(type==10){
		value = "未婚";
	}else if(value==20){
		value = "已婚";
	}else if(value==21){
		value = "初婚";
	}else if(value==22){
		value = "再婚";
	}else if(value==23){
		value = "复婚";
	}else if(value==30){
		value = "丧偶";
	}else if(value==40){
		value = "离婚";
	}
	return value;
}

/**
 * 性别
 * @param type
 * @returns
 */
function getSex(type){
	var value="未说明性别";
	if(type==0){
		value="未知的性别";
	}else if(type==1){
		value="男性";
	}else if(type==2){
		value="女性";
	}
	return value;
}
 
/**
 * 最高学历
 * @param type
 * @returns
 */
function getHiestEdu(type){
	var value="未知";
	if(type==10){
		value="研究生";
	}else if(type==20){
		value="大学";
	}else if(type==30){
		value="大专";
	}else if(type==40){
		value="中等专业学校或中等技术学校";
	}else if(type==50){
		value="技术学校";
	}else if(type==60){
		value="高中";
	}else if(type==70){
		value="初中";
	}else if(type==80){
		value="小学";
	}else if(type==90){
		value="文盲或半文盲";
	}
	return value;
}

/**
 * 最高学位
 * @param type
 * @returns
 */
function getHighestDegre(type){
	var value = "未知";
	if(type==0){
		value="其他";
	}else if(type==1){
		value="名誉博士";
	}else if(type==2){
		value="博士";
	}else if(type==3){
		value="硕士";
	}else if(type==4){
		value="学士";
	}
	return value;
}

/**
 * 居住状况
 * @param type
 * @returns
 */
function getLivStatus(type){
	var value="未知";
	if(type==1){
		value="自置";
	}else if(type==2){
		value="按揭";
	}else if(type==3){
		value="亲属楼宇";
	}else if(type==4){
		value="集体宿舍";
	}else if(type==5){
		value="租房";
	}else if(type==6){
		value="共有住宅";
	}else if(type==7){
		value="其他";
	}
	return value;
}

/**
 * 职业
 * @param type
 * @returns
 */
function getProfes(type){
	var value="未知";
	if(type==0){
		value="国家机关、党群组织、企业、事业单位负责人";
	}else if(type==1){
		value="专业技术人员";
	}else if(type==3){
		value="办公人士和有关人员";
	}else if(type==4){
		value="商业、服务业人员";
	}else if(type==5){
		value="农、林、牧、渔、水利业生产人员";
	}else if(type==6){
		value="生产、运输设备操作人员及有关人员";
	}else if(type=="X"){
		value="军人";
	}else if(type=="Y"){
		value="不便分类的其他从业人员";
	}
	return value;
}
/**
 * 职务
 * @param type
 * @returns
 */
function getDuty(type){
	var value="未知";
	if(type==1){
		value="高级领导";
	}else if(type==2){
		value="中级领导";
	}else if(type==3){
		value="一般员工";
	}else if(type==4){
		value="其他";
	}
	return value;
}

/**
 * 职称
 * @param type
 * @returns
 */
function getTitle(type){
	var value="未知";
	if(type==0){
		value="无";
	}else if(type==1){
		value="高级";
	}else if(type==2){
		value="中级";
	}else if(type==3){
		value="初级";
	}
	return value;
}

/**
 * 单位所属行业
 * @param type
 * @returns
 */
function getWorkUnitIndOwned(type){
	var value="未知";
	if(type=="A"){
		value = "农、林、牧、渔业";
	}else if(type=="B"){
		value="采掘业";
	}else if(type=="C"){
		value="制造业";
	}else if(type=="D"){
		value="电力、燃气及水的生产和供应";
	}else if(type=="E"){
		value="建筑业";
	}else if(type=="F"){
		value="交通运输、仓储和邮政业";
	}else if(type=="G"){
		value="信息传输、计算机服务和软件业";
	}else if(type=="H"){
		value="批发和零售业";
	}else if(type=="I"){
		value="住宿和餐饮业";
	}else if(type=="J"){
		value="金融业";
	}else if(type=="K"){
		value="房地产业";
	}else if(type=="L"){
		value="租赁和商务服务业";
	}else if(type=="M"){
		value="科学研究、技术服务和地质勘察业";
	}else if(type=="N"){
		value="水利、环境和公共设施管理业";
	}else if(type=="O"){
		value="居民服务和其他服务业";
	}else if(type=="P"){
		value="教育";
	}else if(type=="Q"){
		value="卫生、社会保障和社会福利业";
	}else if(type=="R"){
		value="文化、体育和娱乐业";
	}else if(type=="S"){
		value="公共管理和社会组织";
	}else if(type=="T"){
		value="国际组织";
	}
	return value;
}

/**
 * 担保方式
 * @param type
 * @returns
 */
function getGuaranteeway(type){
	var value = "其他";
	if(type==1){
		value = "质押(含保证金)";
	}else if(type==2){
		value = "抵押";
	}else if(type==3){
		value = "自然人保证";
	}else if(type==4){
		value = "信用/免担保";
	}else if(type==5){
		value = "组合(含自然人保证)";
	}else if(type==6){
		value = "组合(不含自然人保证)";
	}else if(type==7){
		value = "农户联保";
	}
	return value;
}
 
/**
 * 还款频度
 * @param type
 * @returns
 */
function getTermsfreq(type){
	var value = "其他";
	if(type=='01'){
		value = "日";
	}else if(type=='02'){
		value = "周";
	}else if(type=='03'){
		value = "月";
	}else if(type=='04'){
		value = "季";
	}else if(type=='05'){
		value = "半年";
	}else if(type=='06'){
		value = "年";
	}else if(type=='07'){
		value = "一次性";
	}else if(type=='08'){
		value = "不定期";
	}
	return value;
}

/**
 * 特殊交易类型
 * @param type
 * @returns
 */
function getSpectype(type){
	var value = "其他";
	if(type==1){
		value = "展期(延期)";
	}else if(type==2){
		value = "担保人代还";
	}else if(type==3){
		value = "以资抵债";
	}else if(type==4){
		value = "提前还款(部分)";
	}else if(type==5){
		value = "提前还款(全部)";
	}
	return value;
}


/**
 * 五级分类
 * @param type
 * @returns
 */
function getFiveClass(type){
	var value = "未知";
	if(type==1){
		value = "正常";
	}else if(type==2){
		value = "关注";
	}else if(type==3){
		value = "次级";
	}else if(type==4){
		value = "可以";
	}else if(type==5){
		value = "损失";
	}
	return value;
}

/**
 * 贷款状态
 * @param type
 * @returns
 */
function getLoadState(type){
	var value = "未知";
	if(type==01){
		value = "正常";
	}else if(type==02){
		value = "拖欠";
	}else if(type==03){
		value = "逾期";
	}else if(type==04){
		value = "结清";
	}else if(type==05){
		value = "已回购";
	}else if(type==06){
		value = "合同取消";
	}else if(type==07){
		value = "待回购";
	}else if(type==08){
		value = "回购中";
	}
	return value;
}

