package com.dgq.crs.xml.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.dgq.crs.constant.MessageHeaderConstant;
import com.dgq.crs.entity.ReportTaxInfo;
import com.dgq.crs.service.ICRSNonResidentTaxService;
import com.dgq.crs.xml.bean.AccountHolder;
import com.dgq.crs.xml.bean.AccountReport;
import com.dgq.crs.xml.bean.Address;
import com.dgq.crs.xml.bean.AddressCN;
import com.dgq.crs.xml.bean.AddressEN;
import com.dgq.crs.xml.bean.AddressFixCN;
import com.dgq.crs.xml.bean.AddressFixEN;
import com.dgq.crs.xml.bean.BirthInfo;
import com.dgq.crs.xml.bean.CheckResult;
import com.dgq.crs.xml.bean.CommonElement;
import com.dgq.crs.xml.bean.ControllingPerson;
import com.dgq.crs.xml.bean.CountryInfo;
import com.dgq.crs.xml.bean.DocSpec;
import com.dgq.crs.xml.bean.Individual;
import com.dgq.crs.xml.bean.MessageHeader;
import com.dgq.crs.xml.bean.NameOrganisation;
import com.dgq.crs.xml.bean.NamePerson;
import com.dgq.crs.xml.bean.Organisation;
import com.dgq.crs.xml.bean.Payment;
import com.dgq.utils.StringUtils;

/*
* @Description: CRS数据校验主类
* @author dgq 
* @date 2018年12月11日
*/
public class BuisnessChecker {
	
	// 待校验数据
	private List<ReportTaxInfo> taxInfos;
	//数据所属年限
	private String dataYear;
	//校验结果
	private List<CheckResult> checkResult;
	private MessageHeader mh;
	private Map<String, List<AccountReport>> accountReports;
	private String messageRefId;
	//是否已经校验
	private boolean isCheck = false; 
	//账户记录编号前缀：CN+4位报告年度+14位金融机构注册码
	private String docRefIdPrefix;
	//报告唯一编码前缀：RE+14位金融破机构注册码+报告年度
	private static AtomicInteger messageRefIdSerialNumStart = new AtomicInteger(0);
	private static AtomicInteger docRefIdSerialNumStart = new AtomicInteger(0);
	private static final DecimalFormat df = new DecimalFormat("000000000");
	private static final DecimalFormat messageRefIdDf = new DecimalFormat("00000000");
	public static final List<String> specialCharacter = Arrays.asList(new String[] {"&","<","/*","--","&#"});
	public ICRSNonResidentTaxService reportTaxInfoService;
	
	public BuisnessChecker(ICRSNonResidentTaxService reportTaxInfoService, List<ReportTaxInfo> taxInfos, String dataYear){
		this.reportTaxInfoService = reportTaxInfoService;
		this.dataYear = dataYear;
		this.taxInfos = taxInfos;
		this.checkResult = new ArrayList<CheckResult>();
		this.docRefIdPrefix = "CN"+dataYear.split("-")[0]+MessageHeaderConstant.fiId;
		this.messageRefId = null;
	}
	
	public BuisnessChecker check(Set<String> dataCheckFailOfAccountNumbers) {
		this.mh = assembleMessageHeader();
		this.accountReports = assembleAccountReport(dataCheckFailOfAccountNumbers);
		this.isCheck = true;
		return this;
	}
	
	private MessageHeader assembleMessageHeader() {
		//构建MessageHeader
		MessageHeader mh = new MessageHeader(MessageHeaderConstant.reportingId, MessageHeaderConstant.fiId, MessageHeaderConstant.reportingType,
				messageRefId, this.dataYear, MessageHeaderConstant.messageTypeIndic, null);
		return mh;
	}
	
	public Map<String, List<AccountReport>> assembleAccountReport(Set<String> dataCheckFailOfAccountNumbers) {
		List<AccountReport> ars = new ArrayList<AccountReport>();
		int checkFailCount = 0;
		for(int i = 0; i < taxInfos.size(); i++) {
			ReportTaxInfo rt = taxInfos.get(i);
			//数据校验失败的数据不在进行业务校验
			if(dataCheckFailOfAccountNumbers.contains(rt.getAccountNumber())) {
				continue;
			}
			DocSpec docSpec = assembleDocSpec(rt);
			
			boolean closedAccount = Boolean.valueOf(checkArrayDataSame(rt.getClosedAccount(), "账户是否注销(ClosedAccount)", rt));
			boolean selfCertification = Boolean.valueOf(checkArrayDataSame(rt.getSelfCertification(), "是否取得账户持有人自证声明(SelfCertification)", rt));
			CommonElement commonElement = assembleCommonElement(rt, false, "holder", closedAccount)[0];
			String dueDiligenceInd = checkArrayDataSame(rt.getDueDiligenceInd(), "账户类别(DueDiligenceInd)", rt);
			String accountHolderType =  checkArrayDataSame(rt.getAccountHolderType(), "账户持有人类别(AccountHolderType)", rt);
			String accountNumber = checkArrayDataSame(rt.getAccountNumber(), "账号(AccountNumber)", rt);
			String openingFiname = checkArrayDataSame(rt.getOpeningFiname(), "开户金融机构名称(OpeningFiname)", rt);
			rt.setDueDiligenceInd(dueDiligenceInd);
			rt.setSelfCertification(String.valueOf(selfCertification));
			AccountHolder accountHolders = assembleAccountHolder(rt, accountHolderType);
			AccountReport accountReport = new AccountReport(docSpec, accountNumber, closedAccount, dueDiligenceInd,
									selfCertification, commonElement, accountHolderType, openingFiname, accountHolders);
			accountReport.setPayment(assemblePayment(rt));
			
			if("CRS101".equals(accountHolderType)){
				ControllingPerson[] controllingPerson = assembleControllingPerson(rt);
				accountReport.setControllingPerson(controllingPerson);
			}
			if(checkResult.size() == checkFailCount) {
				ars.add(accountReport);
			}
			checkFailCount = checkResult.size();
		}
		
		Map<String, List<AccountReport>> map = ars.stream().collect(Collectors.groupingBy(o->{
			return o.getDueDiligenceInd();
		}));
		return map;
	}
	
	/**
	 * 组装账户持有人节点
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private AccountHolder assembleAccountHolder(ReportTaxInfo rt, String accountHolderType){
		Address address = assembleAddress(rt, "holder");
		String[] resCountryCode = checkArrayDataSameForArray(rt.getResCountryCode(), "税收居民国代码(ResCountryCode)", rt);
		//个人
		if("CRS100".equals(accountHolderType)){
			NamePerson name = assembleNamePerson(rt, "holder");
			String gender = checkArrayDataSame(rt.getGender(), "账户持有人性别(Gender)", rt);
			//强制项
			Individual individual = new Individual(name, gender, address, resCountryCode);
			//非强制项
			if(!StringUtils.isEmpty(rt.getPhoneNo()))
				individual.setPhoneNo(checkArrayDataSame(rt.getPhoneNo(), "账户持有人手机(PhoneNo)", rt));
			if(!StringUtils.isEmpty(rt.getIdType()))
				individual.setIdType(checkArrayDataSame(rt.getIdType(), "身份证件类型(IDType)", rt));
			if(!StringUtils.isEmpty(rt.getIdNumber())) 
				individual.setIdNumber(checkArrayDataSame(rt.getIdNumber(), "身份证件号码(IDNumber)", rt));
			boolean isReportTin = false;
			if(!StringUtils.isEmpty(rt.getTin())){
				CommonElement[] commonElement = assembleCommonElement(rt, true, "holder", null);
				individual.setTin(commonElement);
				isReportTin = true;
			}
			if(!StringUtils.isEmpty(rt.getExplanation())){
				String explanation = checkArrayDataSame(rt.getExplanation(), "不能提供纳税人识别号的理由(Explanation)", rt);
				individual.setExplanation(explanation);
			}else if(!isReportTin && Boolean.valueOf(rt.getSelfCertification())){
				checkResult.add(new CheckResult("账户持有人的Explanation: TIN未报送，且取得了自证声明,Explanation必须报送！", rt.getAccountNumber()));
			}
			
			if(!StringUtils.isEmpty(rt.getNationality()))
				individual.setNationality(checkArrayDataSame(rt.getNationality(), "账户持有人国籍(Nationality)", rt));
			
			//新开账户必须报送出生信息
			if(null != rt.getBirthDateStr()){
				String birthDate = checkArrayDataSame(rt.getBirthDateStr(), "账户持有人出生日期(BirthDate)", rt);
				BirthInfo birthInfo = new BirthInfo(birthDate, assembleCountryInfo(rt, "holder"));
				if(!StringUtils.isEmpty(rt.getCity())) birthInfo.setCity(checkArrayDataSame(rt.getCity(), "账户持有人出生城市(birthCity)", rt));
				individual.setBirthInfo(birthInfo);
			}else if("N".equals(checkArrayDataSame(rt.getDueDiligenceInd(), "账户类别(DueDiligenceInd)", rt))){
				checkResult.add(new CheckResult("账户持有人出生日期(BirthDate),新用户出生信息必须必报！", rt.getAccountNumber()));
			}
			return new AccountHolder(individual);
		}else{//机构
			NameOrganisation name = assembleNameOrg(rt);
			//强制项
			Organisation organisation = new Organisation(name, address, resCountryCode);
			
			//非强制项
			if(!StringUtils.isEmpty(rt.getPhoneNo()))
				organisation.setPhoneNo(checkArrayDataSame(rt.getPhoneNo(), "账户持有人手机(PhoneNo)", rt));
			
			boolean isReportTin = false;
			if(!StringUtils.isEmpty(rt.getTin())){
				CommonElement[] commonElement = assembleCommonElement(rt, true, "holder", null);
				organisation.setTin(commonElement);
				isReportTin = true;
			}
			if(!StringUtils.isEmpty(rt.getExplanation())){
				String explanation = checkArrayDataSame(rt.getExplanation(), "不能提供纳税人识别号的理由(Explanation)", rt);
				organisation.setExplanation(explanation);
			}else if(!isReportTin && Boolean.valueOf(rt.getSelfCertification())){
				checkResult.add(new CheckResult("账户持有人的Explanation: TIN未报送，且取得了自证声明,Explanation必须报送！", rt.getAccountNumber()));
			}
			return new AccountHolder(organisation);
		}
	}
	
	/**
	 * 组装控制人节点
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private ControllingPerson[] assembleControllingPerson(ReportTaxInfo rt) {
		String accountNumber = rt.getAccountNumber();
		List<ControllingPerson> list = new ArrayList<>();
		List<ReportTaxInfo> controllingPersons = reportTaxInfoService.queryControllingPersonByAccountNumber(accountNumber);
		for (ReportTaxInfo cp : controllingPersons) {
			cp.setAccountNumber(accountNumber);
			Address address = assembleAddress(cp, "ctrl");
			String[] resCountryCode = checkArrayDataSameForArray(cp.getResCountryCode2(), "控制人的税收居民国代码(ResCountryCode)", cp);
			NamePerson name = assembleNamePerson(cp, "ctrl");
			
			ControllingPerson ctlperson = new ControllingPerson(name, address, resCountryCode);
			String ctlpersonType = cp.getCtrlgPersonType();
			if (!StringUtils.isEmpty(ctlpersonType)) {
				ctlpersonType = checkArrayDataSame(ctlpersonType, "控制人类型不唯一(CtrlgPersonType)", rt);
				ctlperson.setCtrlgPersonType(ctlpersonType);
			}
			boolean isReportTin = false;
			if(!StringUtils.isEmpty(cp.getTin2())){
				CommonElement[] commonElement = assembleCommonElement(cp, true, "ctrl", null);
				ctlperson.setTin(commonElement);
				isReportTin = true;
			}
			if(!StringUtils.isEmpty(cp.getExplanation2())){
				String explanation = checkArrayDataSame(cp.getExplanation(), "控制人的不能提供纳税人识别号的理由(Explanation)", cp);
				ctlperson.setExplanation(explanation);
			}else if(!isReportTin && !StringUtils.isEmpty(rt.getDueDiligenceInd()) && "N".equals(rt.getDueDiligenceInd()) ? true : false){
				checkResult.add(new CheckResult("新用户：控制人的TIN未报送，Explanation必须报送！", cp.getAccountNumber()));
			}
			
			if(null != cp.getBirthDate2Str()){
				String birthDate = checkArrayDataSame(cp.getBirthDate2Str(), "控制人的出生日期(BirthDate)", cp);
				BirthInfo birthInfo = new BirthInfo(birthDate, assembleCountryInfo(cp, "ctrl"));
				if(!StringUtils.isEmpty(cp.getCity2())){
					birthInfo.setCity(checkArrayDataSame(cp.getCity2(), "控制人的出生城市(birthCity)", cp));
				}
				ctlperson.setBirthInfo(birthInfo);
			}else if("R1".equals(checkArrayDataSame(cp.getDocTypeIndic(), "账户报告的类型(DocTypeIndic)", cp)) ? true : false) {
				checkResult.add(new CheckResult("新用户：控制人的出生信息是必填项！", cp.getAccountNumber()));
			}
			
			list.add(ctlperson);
		}
		return list.toArray(new ControllingPerson[] {});
	}
	
	/**
	 * 组装国籍信息
	 * @param rt
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private CountryInfo assembleCountryInfo(ReportTaxInfo rt, String flag) {
		CountryInfo countryInfo = new CountryInfo();
		
		String birthCountryCode = flag.equals("holder") ? rt.getBirthCountryCode() : rt.getBirthCountryCode2();
		String formerCountryName = flag.equals("holder") ? rt.getFormerCountryName() : rt.getFormerCountryName2();
		if(StringUtils.isEmpty(birthCountryCode) && StringUtils.isEmpty(rt.getFormerCountryName())) {
			checkResult.add(new CheckResult("出生国代码不在ISO3166中的出生国英文名称(FormerCountryName)必填", rt.getAccountNumber()));
		}else if(!StringUtils.isEmpty(birthCountryCode) && !StringUtils.isEmpty(rt.getFormerCountryName())) {
			checkResult.add(new CheckResult("出生国代码不在ISO3166中无法填写代码的才需要填写出生国英文名称(FormerCountryName)", rt.getAccountNumber()));
		}else if(!StringUtils.isEmpty(birthCountryCode)) {
			countryInfo.setCountryCode(checkArrayDataSame(birthCountryCode, (flag.equals("holder") ? "账户持有人" : "控制人") + "的出生国代码(countryCode)", rt));
		}else {
			countryInfo.setFormerCountryName(checkArrayDataSame(formerCountryName, (flag.equals("holder") ? "账户持有人" : "控制人") + "的出生国英文名称(FormerCountryName)", rt));
		}
		
		return countryInfo;
	}
	
	/**
	 * 组装个人Name节点
	 * @param rt
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private NamePerson assembleNamePerson(ReportTaxInfo rt, String flag) {
		String firstName = checkArrayDataSame(flag.equals("holder") ? rt.getFirstName() : rt.getFirstName2(), (flag.equals("holder") ? "账户持有人" : "控制人") + "的英文名(FirstName)", rt); 
		String lastName = checkArrayDataSame(flag.equals("holder") ? rt.getLastName() : rt.getLastName2(), (flag.equals("holder") ? "账户持有人" : "控制人") + "的英文姓(LastName)", rt); 
		if(StringUtils.isChinese(firstName) || StringUtils.isChinese(lastName)) {
			checkResult.add(new CheckResult((flag.equals("holder") ? "账户持有人" : "控制人") + "的证件法定姓名只有中文的填写拼音", rt.getAccountNumber()));
		}
		NamePerson name = new NamePerson(firstName, lastName);
		
		String nametype = flag.equals("holder") ? rt.getNameType() : rt.getNameType2();
		String middleName = flag.equals("holder") ? rt.getMiddleName() : rt.getMiddleName2();
		String nameCn = flag.equals("holder") ? rt.getNameCn() : rt.getNameCn2();
		String precedingTitle = flag.equals("holder") ? rt.getPrecedingTitle() : rt.getPrecedingTitle2();
		String title = flag.equals("holder") ? rt.getTitle() : rt.getTitle2();
		String namePrefix = flag.equals("holder") ? rt.getNamePrefix() : rt.getNamePrefix2();
		String generationIdentifier = flag.equals("holder") ? rt.getGenerationIdentifier() : rt.getGenerationIdentifier2();
		String suffix = flag.equals("holder") ? rt.getSuffix() : rt.getSuffix2();
		String generalSuffix = flag.equals("holder") ? rt.getGeneralSuffix() : rt.getGeneralSuffix2();
		if (!StringUtils.isEmpty(nametype)) name.setNameType(checkArrayDataSame(nametype, (flag.equals("holder") ? "账户持有人" : "控制人") + "的名称类型(NameType)", rt));
		if (!StringUtils.isEmpty(middleName)) name.setMiddleName(checkArrayDataSame(middleName, (flag.equals("holder") ? "账户持有人" : "控制人") + "的中间名MiddleName", rt));
		if (!StringUtils.isEmpty(nameCn)) name.setNameCN(checkArrayDataSame(nameCn, (flag.equals("holder") ? "账户持有人" : "控制人") + "的NameCN", rt));
		if (!StringUtils.isEmpty(precedingTitle)) name.setPrecedingTitle(checkArrayDataSame(precedingTitle, (flag.equals("holder") ? "账户持有人" : "控制人") + "的曾用名(PrecedingTitle)", rt));
		if (!StringUtils.isEmpty(title)) name.setTitle(checkArrayDataSameForArray(title, (flag.equals("holder") ? "账户持有人" : "控制人") + "的曾用名(Title)", rt));
		if (!StringUtils.isEmpty(namePrefix)) name.setNamePrefix(checkArrayDataSame(namePrefix, (flag.equals("holder") ? "账户持有人" : "控制人") + "的名称前缀(NamePrefix)", rt));
		if (!StringUtils.isEmpty(generationIdentifier)) name.setGenerationIdentifier(checkArrayDataSame(generationIdentifier, (flag.equals("holder") ? "账户持有人" : "控制人") + "的称谓(GenerationIdentifier)", rt));
		if (!StringUtils.isEmpty(suffix)) name.setSuffix(checkArrayDataSameForArray(suffix, (flag.equals("holder") ? "账户持有人" : "控制人") + "的称谓(Suffix)", rt));
		if (!StringUtils.isEmpty(generalSuffix)) name.setGeneralSuffix(checkArrayDataSame(generalSuffix, (flag.equals("holder") ? "账户持有人" : "控制人") + "的称谓(GeneralSuffix)", rt));
		return name;
	}
	
	/**
	 * 组装组织机构的Name节点
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private NameOrganisation assembleNameOrg(ReportTaxInfo rt) {
		String orgNameEn = checkArrayDataSame(rt.getOrganisationNameEn(), "账户持有人为机构的机构英文名称(OrganisationNameEn)", rt);
		if(StringUtils.isChinese(orgNameEn)){
			checkResult.add(new CheckResult("账户持有人为机构的只有中文名，填写拼音(OrganisationNameEn)", rt.getAccountNumber()));
		}
		NameOrganisation name = new NameOrganisation(orgNameEn);
		if (!StringUtils.isEmpty(rt.getOrgNameType())) name.setOrgNameType(checkArrayDataSame(rt.getOrgNameType(), "账户持有人为机构的机构名称类型(OrgNameType)", rt));
		if (!StringUtils.isEmpty(rt.getOrganisationNameCn())) name.setOrganisationNameCN(checkArrayDataSame(rt.getOrganisationNameCn(), "账户持有人为机构的机构中文名称(OrganisationNameCn)", rt));
		return name;
	}
	/**
	 * 组装地址节点
	 * @param rt
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private Address assembleAddress(ReportTaxInfo rt, String flag) {
		String countryCode = flag.equals("holder") ? rt.getCountryCode() : rt.getCountryCode2();
		String addressFreeEn = flag.equals("holder") ? rt.getAddressFreeEn() : rt.getAddressFreeEn2();
		String cityEn = flag.equals("holder") ? rt.getCityEn() : rt.getCityEn2();
		String legaladdressType = flag.equals("holder") ? rt.getLegaladdressType() : rt.getLegalAddressType2();
		
		countryCode = checkArrayDataSame(countryCode, (flag.equals("holder") ? "账户持有人现居地址的国家代码(CountryCode)" : "控制人地址的国家代码(CountryCode)"), rt);
		addressFreeEn = checkArrayDataSame(addressFreeEn, (flag.equals("holder") ? "账户持有人现居地址自由模式下的英文地址(AddressFreeEn)" : "控制人地址自由模式下的英文地址(AddressFreeEn)"), rt);
		if(StringUtils.isChinese(addressFreeEn)) {
			checkResult.add(new CheckResult((flag.equals("holder") ? "账户持有人" : "控制人")+"英文地址只能填写英文(addressFreeEn)", rt.getAccountNumber()));
		}
		if(StringUtils.isChinese(cityEn)) {
			checkResult.add(new CheckResult((flag.equals("holder") ? "账户持有人" : "控制人")+"城市只能填写英文(cityEn)", rt.getAccountNumber()));
		}
		AddressEN addressEN = new AddressEN(addressFreeEn);
		
		if("N".equals(rt.getDueDiligenceInd()) 
				&& StringUtils.isEmpty(cityEn)){
			checkResult.add(new CheckResult("新开账户固定模式英文地址的城市为必填项(CityEn)", rt.getAccountNumber()));
		}
		if(!StringUtils.isEmpty(cityEn)){
			AddressFixEN addressFixEN = assembleAddressFixEN(rt, flag);
			addressEN = new AddressEN(addressFixEN, addressFreeEn);
		}
		Address address = new Address(countryCode, addressEN);
		
		if(!StringUtils.isEmpty(rt.getLegaladdressType())) address.setLegalAddressType(checkArrayDataSame(legaladdressType, (flag.equals("holder") ? "账户持有人" : "控制人") + "的地址类型(LegalAddressType)", rt));

		String province = flag.equals("holder") ? rt.getProvince() : rt.getProvince2();
		String cityCn = flag.equals("holder") ? rt.getCityCn() : rt.getCityCn2();
		String postCodeCn = flag.equals("holder") ? rt.getPostCodeCn() : rt.getPostCode2Cn();
		String districtNameCn = flag.equals("holder") ? rt.getDistrictNameCn() : rt.getDistrictName2Cn();
		AddressFixCN addressFixCN = null;
		if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(cityCn)){
			addressFixCN = new AddressFixCN(checkArrayDataSame(province, (flag.equals("holder") ? "账户持有人现居中文地址固定模式下的省级行政区划(Province)" : "控制人中文地址固定模式下的省级行政区划(Province)"), rt), checkArrayDataSame(cityCn, (flag.equals("holder") ? "账户持有人现居中文地址固定模式下的地市级行政区划(CityCn)" : "控制人中文地址固定模式下的地市级行政区划(CityCn)"), rt));
			if(!StringUtils.isEmpty(postCodeCn)) addressFixCN.setPostCode(checkArrayDataSame(postCodeCn, (flag.equals("holder") ? "账户持有人现居中文地址固定模式下的邮编(PostCodeCn)" : "控制人中文地址固定模式下的邮编(PostCodeCn)"), rt));
			if(!StringUtils.isEmpty(districtNameCn)) addressFixCN.setDistrictName(checkArrayDataSame(districtNameCn, (flag.equals("holder") ? "账户持有人现居中文地址固定模式下的县级行政区划(DistrictNameCn)" : "控制人中文地址固定模式下的县级行政区划(DistrictNameCn)"), rt));
		}else{
			if(((!StringUtils.isEmpty(province) || !StringUtils.isEmpty(postCodeCn) || !StringUtils.isEmpty(districtNameCn)) && StringUtils.isEmpty(cityCn))
					|| ((!StringUtils.isEmpty(cityCn) || !StringUtils.isEmpty(postCodeCn) || !StringUtils.isEmpty(districtNameCn)) && StringUtils.isEmpty(province))) {
				checkResult.add(new CheckResult((flag.equals("holder") ? "账户持有人现居地址中文固定模式地址的省级行政区划(province)和地市级行政区划(cityCN)为必填项" : "控制人地址中文固定模式地址的省级行政区划(province)和地市级行政区划(cityCN)为必填项"), rt.getAccountNumber()));
			}
		}
		String addressFreeCn = flag.equals("holder") ? rt.getAddressFreeCn() : rt.getAddressFreeCn2();
		if(!StringUtils.isEmpty(addressFreeCn)) {
			AddressCN addressCN = new AddressCN(checkArrayDataSame(addressFreeCn, (flag.equals("holder") ? "账户持有人现居地址自由模式下的中文地址(AddressFreeCn)" : "控制人地址自由模式下的中文地址(AddressFreeCn)"), rt), addressFixCN);
			address.setAddressCN(addressCN);
		}
		
		return address;
	}
	/**
	 * 组装固定模式下的英文地址
	 * @param rt
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private AddressFixEN assembleAddressFixEN(ReportTaxInfo rt, String flag) {
		String cityEn = checkArrayDataSame(flag.equals("holder") ? rt.getCityEn() : rt.getCityEn2(), (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的城市英文名称(CityEn)", rt);
		if(StringUtils.isChinese(cityEn)) {
			checkResult.add(new CheckResult((flag.equals("holder") ? "账户持有人" : "控制人")+"城市只能填写英文(CityEn)", rt.getAccountNumber()));
		}
		String street = flag.equals("holder") ? rt.getStreet() : rt.getStreet2();
		String buildingIdentifier = flag.equals("holder") ? rt.getBuildingIdentifier() : rt.getBuildingIdentifier2();
		String suiteIdentifier = flag.equals("holder") ? rt.getSuiteIdentifier() : rt.getSuiteIdentifier2();
		String floorIdentifier = flag.equals("holder") ? rt.getFloorIdentifier() : rt.getFloorIdentifier2();
		String districtName = flag.equals("holder") ? rt.getDistrictName() : rt.getDistrictName2();
		String pob = flag.equals("holder") ? rt.getPob() : rt.getPob2();
		String postCode = flag.equals("holder") ? rt.getPostCode() : rt.getPostCode2();
		String countrySubentity = flag.equals("holder") ? rt.getCountrySubentity() : rt.getCountrySubentity2();
		
		
		AddressFixEN addressFixEN = new AddressFixEN(cityEn);
		if(!StringUtils.isEmpty(street)) addressFixEN.setStreet(checkArrayDataSame(street, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的街道(Street)", rt));
		if(!StringUtils.isEmpty(buildingIdentifier)) addressFixEN.setBuildingIdentifier(checkArrayDataSame(buildingIdentifier, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的楼号(buildingIdentifier)", rt));
		if(!StringUtils.isEmpty(suiteIdentifier)) addressFixEN.setSuiteIdentifier(checkArrayDataSame(suiteIdentifier, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的房门号(suiteIdentifier)", rt));
		if(!StringUtils.isEmpty(floorIdentifier)) addressFixEN.setFloorIdentifier(checkArrayDataSame(floorIdentifier, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的楼层号(floorIdentifier)", rt));
		if(!StringUtils.isEmpty(districtName)) addressFixEN.setDistrictName(checkArrayDataSame(districtName, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的区(districtName)", rt));
		if(!StringUtils.isEmpty(pob)) addressFixEN.setPob(checkArrayDataSame(pob, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的邮箱(pob)", rt));
		if(!StringUtils.isEmpty(postCode)) addressFixEN.setPostCode(checkArrayDataSame(postCode,(flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的邮箱(postCode)", rt));
		if(!StringUtils.isEmpty(countrySubentity)) addressFixEN.setCountrySubentity(checkArrayDataSame(countrySubentity, (flag.equals("holder") ? "账户持有人" : "控制人") + "固定模式英文地址中的省/自治区/直辖市/行政区划，境内填写拼音(countrySubentity) ", rt));
		return addressFixEN;
	}
	/**
	 * 组装收入节点
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private List<Payment> assemblePayment(ReportTaxInfo rt) {
		String[] paymentTypes = new String[0];
		String[] paymentAmtCurrCodes = new String[0];
		String[] paymentAmts = new String[0];
		if(hasTextForPayment(rt)) {
			if(StringUtils.isEmpty(rt.getPaymentType())) {
				checkResult.add(new CheckResult("收入类型缺失(PaymentType)", rt.getAccountNumber()));
			}else {
				//同一种收入类型只允许出现一个
				paymentTypes = checkArrayDataSameForArray(rt.getPaymentType(), "收入类型(paymentType)", rt);
			}
			if(StringUtils.isEmpty(rt.getPaymentAmntCurrCode())) {
				checkResult.add(new CheckResult("收入金额的货币代码缺失(PaymentAmntCurrCode)", rt.getAccountNumber()));
			}else {
				paymentAmtCurrCodes = rt.getPaymentAmntCurrCode().split("~");
			}
			if(StringUtils.isEmpty(rt.getPaymentAmnt())) {
				checkResult.add(new CheckResult("收入金额缺失(PaymentAmnt)", rt.getAccountNumber()));
			}else {
				paymentAmts = rt.getPaymentAmnt().split("~");
			}
		}else {
			return null;
		}
		
		List<Payment> payments = new ArrayList<Payment>();
		if(paymentTypes.length == paymentAmtCurrCodes.length && paymentAmtCurrCodes.length == paymentAmts.length
				&&paymentTypes.length!=0&&paymentAmtCurrCodes.length!=0&&paymentAmts.length!=0){
			for (int i = 0; i < paymentTypes.length; i++) {
				CommonElement commonElement = new CommonElement(String.format("%.2f", Double.valueOf(paymentAmts[i].replace(",", ""))), paymentAmtCurrCodes[i]);
				//收入为0，Payment字段可不报送
				if(!"0.00".equals(commonElement.getValue())) {
					Payment payment = new Payment(paymentTypes[i], commonElement);
					payments.add(payment);
				}
			}
		}else{
			checkResult.add(new CheckResult("收入相关字段的值缺失(paymentType,paymentAmtCurrCode,paymentAmt)", rt.getAccountNumber()));
		}
		
		return payments.size() > 0 ? payments : null;
	}
	
	/**
	 * 校验收入数据字段的完整性
	 * @return
	 */
	private Boolean hasTextForPayment(ReportTaxInfo rt) {
		return !StringUtils.isEmpty(rt.getPaymentType()) || !StringUtils.isEmpty(rt.getPaymentAmntCurrCode())
				|| !StringUtils.isEmpty(rt.getPaymentAmnt());
	}
	
	/**
	 * 组装公用元素
	 * @param rt  数据
	 * @param istin  区分组装accountBancle还是tin
	 * @param flag  区分是控制人还是持有人
	 * @param closeAccountNumber  账户是否已注销
	 * @return
	 * @throws Exception
	 */
	private CommonElement[] assembleCommonElement(ReportTaxInfo rt, boolean istin, String flag, Boolean closeAccountNumber) {
		List<CommonElement> commonElements = new ArrayList<>();
		String tin = flag.equals("holder") ? rt.getTin() : rt.getTin2();
//		String inType = flag.equals("holder") ? rt.getInType() : rt.getInType2();
		String issuedBy = flag.equals("holder") ? rt.getIssuedBy() : rt.getIssuedBy2();
		if(istin){
			String[] tins = checkArrayDataSameForArray(tin, (flag.equals("holder") ? "账户持有人" : "控制人") + "的纳税人识别号(TIN)", rt);
			//固定值不校验了
			//String[] inTypes = checkArrayDataSameForArray(inType,(flag.equals("holder") ? "账户持有人" : "控制人") + "的纳税人识别号类型(inType)", rt);
			String[] issuedBys = checkArrayDataSameForArray(issuedBy, (flag.equals("holder") ? "账户持有人" : "控制人") + "的纳税人识别号的发放国家代码(issuedBy)", rt);
			if(tins.length == issuedBys.length) {
				for(int i = 0; i < tins.length; i++){
					CommonElement commonElement = new CommonElement(tins[i], "TIN", issuedBys[i]);
					commonElements.add(commonElement);
				};
			}
		}else{
			String ab = checkArrayDataSame(rt.getAccountBalance(), "账户余额(AccountBalance)", rt);
			String accountBalance = Objects.isNull(ab) ? "0.00" : String.format("%.2f", Double.valueOf(ab));
			if(Objects.nonNull(closeAccountNumber) 
					&& closeAccountNumber && Double.valueOf(ab) > 0) {
				checkResult.add(new CheckResult("账户已注销,账户余额不为零(AccountBalance)", rt.getAccountNumber()));
			}
			CommonElement commonElement = new CommonElement(accountBalance.replace(",", ""), checkArrayDataSame(rt.getAccountBalanceCurrCode(), "账户余额的货币代码(AccountBalanceCurrCode)", rt));
			commonElements.add(commonElement);
		}
		return commonElements.toArray(new CommonElement[] {});
	}
	
	/**
	 * 组装AccountReport的输入类型：stc:DocSpec_Type
	 * @param rt
	 * @return
	 * @throws Exception 
	 */
	private DocSpec assembleDocSpec(ReportTaxInfo rt) {
		String docTypeIndic = checkArrayDataSame(rt.getDocTypeIndic(), "账户报告的类型(DocTypeIndic)", rt);
		String corrDocRefId = rt.getCorrDocRefId();
		//新数据不得填报CorrDocRefId，修改或新增数据必须填报，
		String docRefId = docRefIdPrefix + df.format(docRefIdSerialNumStart.incrementAndGet());
		if(StringUtils.isEmpty(corrDocRefId)){
			return new DocSpec(docRefId, docTypeIndic);
		}else{
			if(rt.getMessageTypeIndic().equals("CRS701")){
				checkResult.add(new CheckResult("被修改或删除的账户记录编号(CorrDocRefId): 新账户记录不得包含此字段", rt.getAccountNumber()));
			}
			corrDocRefId = checkArrayDataSame(corrDocRefId, "被修改或删除的账户记录编号(CorrDocRefId)", rt);
			return new DocSpec(docRefId, docTypeIndic, corrDocRefId);
		}
					
	}
	
	/* 
	 *  强制且唯一的标签不允许有不一样的数据
	 * @param value
	 * @param column
	 * @param ReportTaxInfo rt
	 * @param required
	 * @return
	 */
	private String checkArrayDataSame(String value, String column, ReportTaxInfo rt){
		Set<String> set = new HashSet<String>();
		if(StringUtils.isEmpty(value)) {
			checkResult.add(new CheckResult(column+": 必填", rt.getAccountNumber()));
			return null;
		}
		for (String str : value.split("~")) {
			set.add(str);
			if(set.size() > 1){
				checkResult.add(new CheckResult(column+": 不唯一", rt.getAccountNumber()));
				return str;
			}
			if(checkSpecialCharater(str)) {
				checkResult.add(new CheckResult(column+"包含不允许出现的特殊字符(&,<,/*,--,&#)", rt.getAccountNumber()));
				return str;
			}
		}
		Iterator<String> iterator = set.iterator();
		
		return iterator.hasNext() ? iterator.next() : null;
	}
	
	/**
	 * 处理那些允许有多个重复节点的数据，数据如一样只显示一个，所以这里使用无需不可重复的Set
	 * @param s
	 * @param column
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	private String[] checkArrayDataSameForArray(String s, String column, ReportTaxInfo rt) {
		Set<String> set = new HashSet<String>();
		if(StringUtils.isEmpty(s)) {
			checkResult.add(new CheckResult(column+": 必填", rt.getAccountNumber()));
			return new String[] {};
		}
		String[] s1 = s.split("~");
		for (String str : s1) {
			if(checkSpecialCharater(str)) {
				checkResult.add(new CheckResult(column+"包含不允许出现的特殊字符(&,<,/*,--,&#)", rt.getAccountNumber()));
				return new String[]{str};
			}
			set.add(str);
		}
		return set.toArray(new String[]{});
	}
	
	private boolean checkSpecialCharater(String str) {
		for (String s : specialCharacter) {
			if(str.contains(s)) {
				return true;
			};
		}
		return false;
	}
	
	public boolean isCheckStatus() {
		return isCheck && checkResult.size() == 0;
	}
	public List<CheckResult> getCheckResult() {
		return checkResult;
	}
	public MessageHeader getMh() {
		return mh;
	}
	public Map<String, List<AccountReport>> getAccountReports() {
		return accountReports;
	}
	public String getMessageRefId() {
		return messageRefId;
	}
	
	public String getDataYear() {
		return dataYear;
	}
}
