package com.dgq.crs.xml.bean;
/*
* @Description: public class ReportingGroup{ }
* @author dgq 
* @date 2018年6月4日
*/

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.dgq.crs.constant.NameSpaces;
/**
 * 账户的具体信息
 * @author Administrator
 *
 */
@XmlType(propOrder = {"docSpec", "accountNumber", "closedAccount", "dueDiligenceInd",
				"selfCertification", "accountBalance", "accountHolderType", "openingFIName",
				"payment", "accountHolder", "controllingPerson"})
@XmlAccessorType(value=XmlAccessType.FIELD)
public class AccountReport {
	
	@XmlElement(name="DocSpec", namespace=NameSpaces.cncrs)
	private DocSpec docSpec; //输入类型   强制
	
	@XmlElement(name="AccountNumber", namespace=NameSpaces.cncrs)
	private String accountNumber;//账号  强制
	
	@XmlElement(name="ClosedAccount", namespace=NameSpaces.cncrs)
	private boolean closedAccount;//是否注销  强制
	
	@XmlElement(name="DueDiligenceInd", namespace=NameSpaces.cncrs)
	private String dueDiligenceInd;// 账户类别 ；N：新开户  P：存量户  强制
	
	@XmlElement(name="SelfCertification", namespace=NameSpaces.cncrs)
	private boolean selfCertification;//是否取得账户持有人的自证声明    强制
	
	@XmlElement(name="AccountBalance", namespace=NameSpaces.cncrs)
	private CommonElement accountBalance; //账户余额   小数点后两位   强制 
	
	@XmlElement(name="AccountHolderType", namespace=NameSpaces.cncrs)
	private String accountHolderType; //账户持有人类别  强制
	
	@XmlElement(name="OpeningFIName", namespace=NameSpaces.cncrs)
	private String openingFIName; // 开户金融机构名称
	
	@XmlElement(name="Payment", namespace=NameSpaces.cncrs)
	private List<Payment> payment; //账户收入 >=0 可选
	
	@XmlElement(name="AccountHolder", namespace=NameSpaces.cncrs)
	private AccountHolder accountHolder; //账户持有人  强制
	
	@XmlElement(name="ControllingPerson", namespace=NameSpaces.cncrs)
	private ControllingPerson[] controllingPerson; //AccountHolderType为CRS101： 强制   否则：不得填报

	public AccountReport() {
		super();
	}

	public AccountReport(DocSpec docSpec, String accountNumber, boolean closedAccount, String dueDiligenceInd,
			boolean selfCertification, CommonElement accountBalance, String accountHolderType, String openingFIName,
			AccountHolder accountHolder) {
		super();
		this.docSpec = docSpec;
		this.accountNumber = accountNumber;
		this.closedAccount = closedAccount;
		this.dueDiligenceInd = dueDiligenceInd;
		this.selfCertification = selfCertification;
		this.accountBalance = accountBalance;
		this.accountHolderType = accountHolderType;
		this.openingFIName = openingFIName;
		this.accountHolder = accountHolder;
	}

	public DocSpec getDocSpec() {
		return docSpec;
	}

	public void setDocSpec(DocSpec docSpec) {
		this.docSpec = docSpec;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public boolean isClosedAccount() {
		return closedAccount;
	}

	public void setClosedAccount(boolean closedAccount) {
		this.closedAccount = closedAccount;
	}

	public String getDueDiligenceInd() {
		return dueDiligenceInd;
	}

	public void setDueDiligenceInd(String dueDiligenceInd) {
		this.dueDiligenceInd = dueDiligenceInd;
	}

	public boolean isSelfCertification() {
		return selfCertification;
	}

	public void setSelfCertification(boolean selfCertification) {
		this.selfCertification = selfCertification;
	}

	public CommonElement getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(CommonElement accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountHolderType() {
		return accountHolderType;
	}

	public void setAccountHolderType(String accountHolderType) {
		this.accountHolderType = accountHolderType;
	}

	public String getOpeningFIName() {
		return openingFIName;
	}

	public void setOpeningFIName(String openingFIName) {
		this.openingFIName = openingFIName;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

	public ControllingPerson[] getControllingPerson() {
		return controllingPerson;
	}

	public void setControllingPerson(ControllingPerson[] controllingPerson) {
		this.controllingPerson = controllingPerson;
	}
}
