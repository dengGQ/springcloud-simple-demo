package com.dgq.crs.xml.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.dgq.crs.xml.bean.AccountReport;
import com.dgq.crs.xml.bean.Cncrs;
import com.dgq.crs.xml.bean.MessageHeader;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class CreateXmlUitl {
	/**
	 * 利用jaxb工具由机构化的java对象生成xml文件
	 * @param messageHeader
	 * @param accountReports
	 * @param xmlPath
	 * @return
	 * @throws Exception
	 */
	public static List<File> createXmlFile(List<MessageHeader> mhs, List<List<AccountReport>> accountReports, String xmlPath) throws Exception{
		List<File> files = new ArrayList<>();
		for (int i = 0; i < mhs.size(); i++) {
			
			File file = createXmlFile(mhs.get(i), accountReports.get(i), xmlPath);
			
			files.add(file);
		}
		return files;
	}
	
	public static File createXmlFile(MessageHeader mh, List<AccountReport> accountReports, String xmlPath) throws Exception{
		Cncrs cncrs = new Cncrs(mh, accountReports);
		
		Marshaller marshaller = JAXBContext.newInstance(Cncrs.class)
				.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
			@Override
			public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
				if("http://aeoi.chinatax.gov.cn/crs/stctypes/v1".equals(namespaceUri)){
					return "stc";
				}else if("http://aeoi.chinatax.gov.cn/crs/cncrs/v1".equals(namespaceUri)){
					return "cncrs";
				}else{
					return "";
				}
			}
		});
		StringWriter writer = new StringWriter();
		marshaller.marshal(cncrs,writer);
		File file = new File(xmlPath);
		BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
		bfw.write(writer.toString());
		bfw.close();
		
		return file;
	}
}
