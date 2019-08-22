package com.dgq.crs.xml.util;

import java.util.ArrayList;
import java.util.List;

import com.dgq.crs.xml.bean.AccountReport;
import com.dgq.crs.xml.bean.Cncrs;
import com.dgq.crs.xml.bean.CncrsRes;
import com.dgq.crs.xml.bean.CommonElement;
import com.dgq.crs.xml.bean.DocSpec;
import com.dgq.crs.xml.bean.Payment;

/*
* @Description: public class XmlToBean{ }
* @author dgq 
* @date 2019年5月23日
*/
public class XmlToBean {
    
    public static void main(String[] args) throws Exception {
    	String xmlPath = "C:/Users/Administrator/Desktop/crs/ACD10008110000142018P00000002.xml";
    	CncrsRes cncrs = (CncrsRes)FileUtil.xmlToBean(xmlPath, CncrsRes.class);
    	
    	System.out.println(cncrs);
    }
    
    public static void main1(String[] args) throws Exception {
    	String start = "CN2018D100081100001400000";
    	
    	int i = 9764;
    	String xmlPath = "C:/Users/Administrator/Desktop/report/RED10008110000142019N00000006.xml";
        Cncrs cncrs = (Cncrs)FileUtil.xmlToBean(xmlPath,Cncrs.class);
        cncrs.getMh().setMessageRefId("RED10008110000142019N00000007");
        List<AccountReport> list = new ArrayList<>();
        for (AccountReport o : cncrs.getAccountReport()) {
        	DocSpec docSpec = o.getDocSpec();
        	docSpec.setDocRefId(start+(i++));
        	o.setDocSpec(docSpec);
        	List<Payment> payment = o.getPayment();
        	CommonElement paymentAmnt = payment.get(0).getPaymentAmnt();
        	String value = paymentAmnt.getValue();
        	if("0.00".equals(value)) {
        		o.setPayment(null);
        		list.add(o);
        	}
        };
        
        cncrs.setAccountReport(list);
        String new_xmlPath = "C:/Users/Administrator/Desktop/report/RED10008110000142019N00000007.xml";
        FileUtil.beanToXml(cncrs, new_xmlPath);
        
        String xmlPath1 = "C:/Users/Administrator/Desktop/report/RED10008110000142019P00000006.xml";
        Cncrs cncrs1 =  (Cncrs)FileUtil.xmlToBean(xmlPath1,Cncrs.class);
        cncrs1.getMh().setMessageRefId("RED10008110000142019P00000007");
        List<AccountReport> list1 = new ArrayList<>();
        for (AccountReport o : cncrs1.getAccountReport()) {
        	DocSpec docSpec = o.getDocSpec();
        	docSpec.setDocRefId(start+(i++));
        	o.setDocSpec(docSpec);
        	List<Payment> payment = o.getPayment();
        	CommonElement paymentAmnt = payment.get(0).getPaymentAmnt();
        	String value = paymentAmnt.getValue();
        	if("0.00".equals(value)) {
        		o.setPayment(null);
        		list1.add(o);
        	}
		}
        cncrs1.setAccountReport(list1);
        String new_xmlPath1 = "C:/Users/Administrator/Desktop/report/RED10008110000142019P00000007.xml";
        FileUtil.beanToXml(cncrs1, new_xmlPath1);
        System.out.println("----------------");

    }
}
