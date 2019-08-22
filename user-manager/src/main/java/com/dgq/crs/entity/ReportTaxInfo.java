package com.dgq.crs.entity;

import java.util.Date;


import javax.persistence.*;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/*
* @Description: 
* @author dgq 
* @date 2018年6月6日
*/
@Entity
@Table(name = "reg.REPORT_TAX_INFO")
public class ReportTaxInfo{
	
	@Id
	private Integer id;
	 /** Description : 系统用户账号 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
    private String reportingId;
    /** Description : 金融机构注册码 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String fiId;
    /** Description : 固定值：CRS */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
    private String reportingType;
    /** Description : 报告唯一编码，与报告文件名相同，不可重复，如果报告因存在错误而被拒收也不可重复 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String messageRefId;
    /** Description : 报送数据所属公立年度，每个报告应仅包含一个年度的数据 */
    @ColumnType(jdbcType=JdbcType.DATE)
    private Date reportingPeriod;
    /** Description : 报告类型：CRS701：新数据; CRS702：修改/删除;  CRS703：零申报(不可包含ReportingGroup)。 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String messageTypeIndic;
    /** Description : 报告生成时间戳 */
    @ColumnType(jdbcType=JdbcType.DATE)
    private Date tmstp;
    /** Description : 账户记录编号，不可重复；格式：CN+4位年份信息+14位金融机构注册码+9位数字序列号 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String docRefId;
    /** Description : 被修改会删除的账户记录编号， doc_type_indic为R1或T1时不得包含该字段 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String corrDocRefId;
    /** Description : 账户报告的类型：取值：R1-R3(新用户,修改,删除), T1-T3(test) */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String docTypeIndic;
    /** Description : 账号 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String accountNumber;
    /** Description : 账户是否注销; 0:false  1:true */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String closedAccount;
    /** Description : 账户类别，固定值：N：新开账户，P：存量账户 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String dueDiligenceInd;
    /** Description : 是否取得账户持有人的自证声明; 0:false  1:true */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String selfCertification;
    /** Description : 账户余额 取值>=0，小数点后两位 已注销账户按0报送 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String accountBalance;
    /** Description : 账户余额的货币代码；三位大写英文字母  取值范围见：ISO 4217 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String accountBalanceCurrCode;
    /** Description : 账户持有人类别 固定值：CRS100：非居民个人;CRS101: 有非居民控制人的消极非金融机构;CRS102：非居民机构，不包含消极非居民机构;CRS103：非居民消极非金融机构，但没有非居民控制人 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String accountHolderType;
    /** Description : 开户金融机构名称 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String openingFiname;
    /** Description : 收入类型   固定值：CRS501：股息; CRS502：利息; CRS503：销售或赎回金融资产总收入; CRS504:其他 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String paymentType;
    /** Description : 收入金额；小数点后两位 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String paymentAmnt;
    /** Description : 收入金额的货币代码；三位大写英文字母；取值范围见：ISO 4217 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String paymentAmntCurrCode;
    /** Description : 账户持有人（个人/机构）姓名类型 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String nameType;
    /** Description : 账户持有人（个人）英文姓，身份证只有中文的填写拼音 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String lastName;
    /** Description : 账户持有人（个人）英文中间名 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String middleName;
    /** Description : 账户持有人（个人）英文名，身份证只有中文的填写拼音，如果账户持有人的法定姓名是一个单名，此栏填写“NFN” */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String firstName;
    /** Description : 账户持有人（个人）中文姓名 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String nameCn;
    /** Description : 账户持有人（个人） */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String precedingTitle;
    /** Description : 账户持有人（个人） */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String title;
    /** Description : 账户持有人（个人） */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String namePrefix;
    /** Description : 账户持有人（个人） */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String generationIdentifier;
    /** Description : 账户持有人（个人） */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String suffix;
    /** Description : 账户持有人（个人） */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String generalSuffix;
    /** Description :  */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String orgNameType;
    /** Description : 账户持有人（机构）英文名 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String organisationNameEn;
    /** Description : 账户持有人（机构） 中文名 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String organisationNameCn;
    /** Description : 账户持有人（个人）性别 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String gender;
    /** Description : 地址类型  取值：OECD301-OECD305  可选；居住地址或办公地址，居住地址，办公地址，注册地址，其他 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String legaladdressType;
    /** Description : 国家代码，两位大写英文字母；取值：见ISO3166 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String countryCode;
    /** Description : 自由模式填写的英文详细地址 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String addressFreeEn;
    /** Description : 固定模式英文地址下：城市 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String cityEn;
    /** Description : 固定模式英文地址下：街道 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String street;
    /** Description : 固定模式英文地址下：楼号 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String buildingIdentifier;
    /** Description : 固定模式英文地址下：房门号 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String suiteIdentifier;
    /** Description : 固定模式英文地址下：楼层 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String floorIdentifier;
    /** Description : 固定模式英文/中文地址下：区 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String districtName;
    /** Description : 固定模式英文地址下：邮箱 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String pob;
    /** Description : 固定模式英文/中文地址下：邮编 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String postCode;
    /** Description : 固定模式英文地址下：中国境内填写省/自治区/直辖市的拼音，境外填写响应行政区划 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String countrySubentity;
    /** Description : 自由模式填写的中文详细地址 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String addressFreeCn;
    /** Description : 固定模式中文地址下：省级行政区划代码，与固定模式英文地址下COUNTRY_SUBENTITY对应 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String province;
    /** Description : 固定模式中文地址下：地市级行政区划代码 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String cityCn;
    /** Description : 固定模式中文地址下：县级行政区划代码 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String districtNameCn;
    /** Description : 固定模式中文地址下：邮编 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String postCodeCn;
    /** Description : 账户持有人（个人/机构）：联系电话 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String phoneNo;
    /** Description : 账户持有人（个人）：身份证件类型 固定值：ACC201--  */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String idType;
    /** Description : 账户持有人（个人）：身份证件号码 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String idNumber;
    /** Description : 账户持有人（个人/机构）: 税收居民国代码 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String resCountryCode;
    /** Description : 账户持有人（个人/机构）: 发放识别号的国家代码 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String issuedBy;
    /** Description : 账户持有人（个人/机构）: 识别号类型，固定值 （TIN）  */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String inType;
    /** Description : 账户持有人（个人/机构）: 纳税人识别号 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String tin;
    /** Description : 账户持有人（个人/机构): 不能提供纳税人识别号的理由；SelfCertification元素值为true且没有报送TIN的，必须报送本字段 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String explanation;
    /** Description : 账户持有人（个人): 国籍代码  */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String nationality;
    /** Description : 账户持有人（个人）：出生日期 */
    @ColumnType(jdbcType=JdbcType.DATE)
    private Date birthDate;
    /** Description : 账户持有人（个人）：出生城市；境内填写拼音， */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String city;
    /** Description : 账户持有人（个人）：出生国代码 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String birthCountryCode;
    /** Description : 账户持有人（个人）：出生国不在ISO3166定义范围内，无法填写代码的，在本字段中填写出生国英文名称 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String formerCountryName;
    /** Description : 控制人：具体含义参考当前字段去掉最好一个字符‘2’后的同名字段;  ACCOUNT_HOLDER_TYPE为‘CRS101’的必须填报，以外的不得填报 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String nameType2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String lastName2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String middleName2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String firstName2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String nameCn2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String precedingTitle2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String title2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String namePrefix2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String generationIdentifier2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String suffix2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String generalSuffix2;
    /** Description : 控制人类型；三位英文字母+三位阿拉伯数字 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String ctrlgPersonType;
    /** Description : 控制人：国籍 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String nationality2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String legalAddressType2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String countryCode2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String addressFreeEn2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String cityEn2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String street2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String buildingIdentifier2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String suiteIdentifier2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String floorIdentifier2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String districtName2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String pob2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String postCode2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String countrySubentity2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String addressFreeCn2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String province2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String cityCn2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String districtName2Cn;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String postCode2Cn;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String resCountryCode2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String issuedBy2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String inType2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String tin2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String explanation2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.DATE)
    private Date birthDate2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String city2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String birthCountryCode2;
    /** Description : 同上 */
    @ColumnType(jdbcType=JdbcType.VARCHAR)
	private String formerCountryName2;

    @Transient
    private Long fileId;
    @Transient
	private String reportingPeriodStr;
    @Transient
	private String tmstpStr;
    @Transient
	private String birthDateStr;
    @Transient
	private String birthDate2Str;
    @Transient
    private String reportStatus;
    @Transient
    private String errCode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReportingId() {
		return reportingId;
	}
	public void setReportingId(String reportingId) {
		this.reportingId = reportingId;
	}
	public String getFiId() {
		return fiId;
	}
	public void setFiId(String fiId) {
		this.fiId = fiId;
	}
	public String getReportingType() {
		return reportingType;
	}
	public void setReportingType(String reportingType) {
		this.reportingType = reportingType;
	}
	public String getMessageRefId() {
		return messageRefId;
	}
	public void setMessageRefId(String messageRefId) {
		this.messageRefId = messageRefId;
	}
	public Date getReportingPeriod() {
		return reportingPeriod;
	}
	public void setReportingPeriod(Date reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}
	public String getMessageTypeIndic() {
		return messageTypeIndic;
	}
	public void setMessageTypeIndic(String messageTypeIndic) {
		this.messageTypeIndic = messageTypeIndic;
	}
	public Date getTmstp() {
		return tmstp;
	}
	public void setTmstp(Date tmstp) {
		this.tmstp = tmstp;
	}
	public String getDocRefId() {
		return docRefId;
	}
	public void setDocRefId(String docRefId) {
		this.docRefId = docRefId;
	}
	public String getCorrDocRefId() {
		return corrDocRefId;
	}
	public void setCorrDocRefId(String corrDocRefId) {
		this.corrDocRefId = corrDocRefId;
	}
	public String getDocTypeIndic() {
		return docTypeIndic;
	}
	public void setDocTypeIndic(String docTypeIndic) {
		this.docTypeIndic = docTypeIndic;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getClosedAccount() {
		return closedAccount;
	}
	public void setClosedAccount(String closedAccount) {
		this.closedAccount = closedAccount;
	}
	public String getDueDiligenceInd() {
		return dueDiligenceInd;
	}
	public void setDueDiligenceInd(String dueDiligenceInd) {
		this.dueDiligenceInd = dueDiligenceInd;
	}
	public String getSelfCertification() {
		return selfCertification;
	}
	public void setSelfCertification(String selfCertification) {
		this.selfCertification = selfCertification;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountBalanceCurrCode() {
		return accountBalanceCurrCode;
	}
	public void setAccountBalanceCurrCode(String accountBalanceCurrCode) {
		this.accountBalanceCurrCode = accountBalanceCurrCode;
	}
	public String getAccountHolderType() {
		return accountHolderType;
	}
	public void setAccountHolderType(String accountHolderType) {
		this.accountHolderType = accountHolderType;
	}
	public String getOpeningFiname() {
		return openingFiname;
	}
	public void setOpeningFiname(String openingFiname) {
		this.openingFiname = openingFiname;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentAmnt() {
		return paymentAmnt;
	}
	public void setPaymentAmnt(String paymentAmnt) {
		this.paymentAmnt = paymentAmnt;
	}
	public String getPaymentAmntCurrCode() {
		return paymentAmntCurrCode;
	}
	public void setPaymentAmntCurrCode(String paymentAmntCurrCode) {
		this.paymentAmntCurrCode = paymentAmntCurrCode;
	}
	public String getNameType() {
		return nameType;
	}
	public void setNameType(String nameType) {
		this.nameType = nameType;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public String getPrecedingTitle() {
		return precedingTitle;
	}
	public void setPrecedingTitle(String precedingTitle) {
		this.precedingTitle = precedingTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNamePrefix() {
		return namePrefix;
	}
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	public String getGenerationIdentifier() {
		return generationIdentifier;
	}
	public void setGenerationIdentifier(String generationIdentifier) {
		this.generationIdentifier = generationIdentifier;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getGeneralSuffix() {
		return generalSuffix;
	}
	public void setGeneralSuffix(String generalSuffix) {
		this.generalSuffix = generalSuffix;
	}
	public String getOrgNameType() {
		return orgNameType;
	}
	public void setOrgNameType(String orgNameType) {
		this.orgNameType = orgNameType;
	}
	public String getOrganisationNameEn() {
		return organisationNameEn;
	}
	public void setOrganisationNameEn(String organisationNameEn) {
		this.organisationNameEn = organisationNameEn;
	}
	public String getOrganisationNameCn() {
		return organisationNameCn;
	}
	public void setOrganisationNameCn(String organisationNameCn) {
		this.organisationNameCn = organisationNameCn;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLegaladdressType() {
		return legaladdressType;
	}
	public void setLegaladdressType(String legaladdressType) {
		this.legaladdressType = legaladdressType;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAddressFreeEn() {
		return addressFreeEn;
	}
	public void setAddressFreeEn(String addressFreeEn) {
		this.addressFreeEn = addressFreeEn;
	}
	public String getCityEn() {
		return cityEn;
	}
	public void setCityEn(String cityEn) {
		this.cityEn = cityEn;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBuildingIdentifier() {
		return buildingIdentifier;
	}
	public void setBuildingIdentifier(String buildingIdentifier) {
		this.buildingIdentifier = buildingIdentifier;
	}
	public String getSuiteIdentifier() {
		return suiteIdentifier;
	}
	public void setSuiteIdentifier(String suiteIdentifier) {
		this.suiteIdentifier = suiteIdentifier;
	}
	public String getFloorIdentifier() {
		return floorIdentifier;
	}
	public void setFloorIdentifier(String floorIdentifier) {
		this.floorIdentifier = floorIdentifier;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getPob() {
		return pob;
	}
	public void setPob(String pob) {
		this.pob = pob;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCountrySubentity() {
		return countrySubentity;
	}
	public void setCountrySubentity(String countrySubentity) {
		this.countrySubentity = countrySubentity;
	}
	public String getAddressFreeCn() {
		return addressFreeCn;
	}
	public void setAddressFreeCn(String addressFreeCn) {
		this.addressFreeCn = addressFreeCn;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCityCn() {
		return cityCn;
	}
	public void setCityCn(String cityCn) {
		this.cityCn = cityCn;
	}
	public String getDistrictNameCn() {
		return districtNameCn;
	}
	public void setDistrictNameCn(String districtNameCn) {
		this.districtNameCn = districtNameCn;
	}
	public String getPostCodeCn() {
		return postCodeCn;
	}
	public void setPostCodeCn(String postCodeCn) {
		this.postCodeCn = postCodeCn;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getResCountryCode() {
		return resCountryCode;
	}
	public void setResCountryCode(String resCountryCode) {
		this.resCountryCode = resCountryCode;
	}
	public String getIssuedBy() {
		return issuedBy;
	}
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	public String getInType() {
		return inType;
	}
	public void setInType(String inType) {
		this.inType = inType;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBirthCountryCode() {
		return birthCountryCode;
	}
	public void setBirthCountryCode(String birthCountryCode) {
		this.birthCountryCode = birthCountryCode;
	}
	public String getFormerCountryName() {
		return formerCountryName;
	}
	public void setFormerCountryName(String formerCountryName) {
		this.formerCountryName = formerCountryName;
	}
	public String getNameType2() {
		return nameType2;
	}
	public void setNameType2(String nameType2) {
		this.nameType2 = nameType2;
	}
	public String getLastName2() {
		return lastName2;
	}
	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}
	public String getMiddleName2() {
		return middleName2;
	}
	public void setMiddleName2(String middleName2) {
		this.middleName2 = middleName2;
	}
	public String getFirstName2() {
		return firstName2;
	}
	public void setFirstName2(String firstName2) {
		this.firstName2 = firstName2;
	}
	public String getNameCn2() {
		return nameCn2;
	}
	public void setNameCn2(String nameCn2) {
		this.nameCn2 = nameCn2;
	}
	public String getPrecedingTitle2() {
		return precedingTitle2;
	}
	public void setPrecedingTitle2(String precedingTitle2) {
		this.precedingTitle2 = precedingTitle2;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getNamePrefix2() {
		return namePrefix2;
	}
	public void setNamePrefix2(String namePrefix2) {
		this.namePrefix2 = namePrefix2;
	}
	public String getGenerationIdentifier2() {
		return generationIdentifier2;
	}
	public void setGenerationIdentifier2(String generationIdentifier2) {
		this.generationIdentifier2 = generationIdentifier2;
	}
	public String getSuffix2() {
		return suffix2;
	}
	public void setSuffix2(String suffix2) {
		this.suffix2 = suffix2;
	}
	public String getGeneralSuffix2() {
		return generalSuffix2;
	}
	public void setGeneralSuffix2(String generalSuffix2) {
		this.generalSuffix2 = generalSuffix2;
	}
	public String getCtrlgPersonType() {
		return ctrlgPersonType;
	}
	public void setCtrlgPersonType(String ctrlgPersonType) {
		this.ctrlgPersonType = ctrlgPersonType;
	}
	public String getNationality2() {
		return nationality2;
	}
	public void setNationality2(String nationality2) {
		this.nationality2 = nationality2;
	}
	public String getLegalAddressType2() {
		return legalAddressType2;
	}
	public void setLegalAddressType2(String legalAddressType2) {
		this.legalAddressType2 = legalAddressType2;
	}
	public String getCountryCode2() {
		return countryCode2;
	}
	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}
	public String getAddressFreeEn2() {
		return addressFreeEn2;
	}
	public void setAddressFreeEn2(String addressFreeEn2) {
		this.addressFreeEn2 = addressFreeEn2;
	}
	public String getCityEn2() {
		return cityEn2;
	}
	public void setCityEn2(String cityEn2) {
		this.cityEn2 = cityEn2;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getBuildingIdentifier2() {
		return buildingIdentifier2;
	}
	public void setBuildingIdentifier2(String buildingIdentifier2) {
		this.buildingIdentifier2 = buildingIdentifier2;
	}
	public String getSuiteIdentifier2() {
		return suiteIdentifier2;
	}
	public void setSuiteIdentifier2(String suiteIdentifier2) {
		this.suiteIdentifier2 = suiteIdentifier2;
	}
	public String getFloorIdentifier2() {
		return floorIdentifier2;
	}
	public void setFloorIdentifier2(String floorIdentifier2) {
		this.floorIdentifier2 = floorIdentifier2;
	}
	public String getDistrictName2() {
		return districtName2;
	}
	public void setDistrictName2(String districtName2) {
		this.districtName2 = districtName2;
	}
	public String getPob2() {
		return pob2;
	}
	public void setPob2(String pob2) {
		this.pob2 = pob2;
	}
	public String getPostCode2() {
		return postCode2;
	}
	public void setPostCode2(String postCode2) {
		this.postCode2 = postCode2;
	}
	public String getCountrySubentity2() {
		return countrySubentity2;
	}
	public void setCountrySubentity2(String countrySubentity2) {
		this.countrySubentity2 = countrySubentity2;
	}
	public String getAddressFreeCn2() {
		return addressFreeCn2;
	}
	public void setAddressFreeCn2(String addressFreeCn2) {
		this.addressFreeCn2 = addressFreeCn2;
	}
	public String getProvince2() {
		return province2;
	}
	public void setProvince2(String province2) {
		this.province2 = province2;
	}
	public String getCityCn2() {
		return cityCn2;
	}
	public void setCityCn2(String cityCn2) {
		this.cityCn2 = cityCn2;
	}
	public String getDistrictName2Cn() {
		return districtName2Cn;
	}
	public void setDistrictName2Cn(String districtName2Cn) {
		this.districtName2Cn = districtName2Cn;
	}
	public String getPostCode2Cn() {
		return postCode2Cn;
	}
	public void setPostCode2Cn(String postCode2Cn) {
		this.postCode2Cn = postCode2Cn;
	}
	public String getResCountryCode2() {
		return resCountryCode2;
	}
	public void setResCountryCode2(String resCountryCode2) {
		this.resCountryCode2 = resCountryCode2;
	}
	public String getIssuedBy2() {
		return issuedBy2;
	}
	public void setIssuedBy2(String issuedBy2) {
		this.issuedBy2 = issuedBy2;
	}
	public String getInType2() {
		return inType2;
	}
	public void setInType2(String inType2) {
		this.inType2 = inType2;
	}
	public String getTin2() {
		return tin2;
	}
	public void setTin2(String tin2) {
		this.tin2 = tin2;
	}
	public String getExplanation2() {
		return explanation2;
	}
	public void setExplanation2(String explanation2) {
		this.explanation2 = explanation2;
	}
	public Date getBirthDate2() {
		return birthDate2;
	}
	public void setBirthDate2(Date birthDate2) {
		this.birthDate2 = birthDate2;
	}
	public String getCity2() {
		return city2;
	}
	public void setCity2(String city2) {
		this.city2 = city2;
	}
	public String getBirthCountryCode2() {
		return birthCountryCode2;
	}
	public void setBirthCountryCode2(String birthCountryCode2) {
		this.birthCountryCode2 = birthCountryCode2;
	}
	public String getFormerCountryName2() {
		return formerCountryName2;
	}
	public void setFormerCountryName2(String formerCountryName2) {
		this.formerCountryName2 = formerCountryName2;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getReportingPeriodStr() {
		return reportingPeriodStr;
	}
	public void setReportingPeriodStr(String reportingPeriodStr) {
		this.reportingPeriodStr = reportingPeriodStr;
	}
	public String getTmstpStr() {
		return tmstpStr;
	}
	public void setTmstpStr(String tmstpStr) {
		this.tmstpStr = tmstpStr;
	}
	public String getBirthDateStr() {
		return birthDateStr;
	}
	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}
	public String getBirthDate2Str() {
		return birthDate2Str;
	}
	public void setBirthDate2Str(String birthDate2Str) {
		this.birthDate2Str = birthDate2Str;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
