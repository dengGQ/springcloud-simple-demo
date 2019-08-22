package com.dgq.crs.xml.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/*
* @Description: file工具类
* @author dgq 
* @date 2018年12月21日
*/
public class FileUtil {

	/**
	 * 压缩文件并写入输出流
	 * @param files
	 * @param zos
	 * @throws IOException
	 */
	public static void outputZipFile(List<File> files, ZipOutputStream zos) {
		for (File file : files) {
			outputZipFile(file, zos);
		}
	}
	
	public static void outputZipFile(File file, ZipOutputStream zos) {
		FileInputStream fis = null;
		try {
			zos.putNextEntry(new ZipEntry(file.getName()));
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int r = 0;
			while ((r = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, r);
			}
			zos.closeEntry();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(fis != null){
				try {
					fis.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
     * xml文件配置转换为对象
     * @param xmlPath  xml文件路径
     * @param load    java对象.Class
     * @return    java对象
     * @throws JAXBException    
     * @throws IOException
     */
    public static Object xmlToBean(String xmlPath, Class<?> load) throws JAXBException, IOException{
        return xmlToBean(new File(xmlPath), load);
    }
    
    /**
     * xml文件数据转换为对象
     * @param xmlPath  xml文件路径
     * @param load    java对象.Class
     * @return    java对象
     * @throws JAXBException    
     * @throws IOException
     */
    public static <T> T xmlToBean(File file, Class<T> load) throws JAXBException, IOException{
        JAXBContext context = JAXBContext.newInstance(load);  
        Unmarshaller unmarshaller = context.createUnmarshaller(); 
        @SuppressWarnings("unchecked")
		T t = (T)unmarshaller.unmarshal(file);
        return t;
    }
    
    /**
     * xml文件数据转换为对象
     * @param <T>
     * @param inputStream
     * @param load
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public static <T> T xmlToBean(InputStream inputStream, Class<T> load) throws JAXBException, IOException{
        JAXBContext context = JAXBContext.newInstance(load);  
        Unmarshaller unmarshaller = context.createUnmarshaller(); 
        @SuppressWarnings("unchecked")
		T t = (T)unmarshaller.unmarshal(inputStream);
        return t;
    }
    
    /**
     * java对象转换为xml文件
     * @param xmlPath  xml文件路径
     * @param load    java对象.Class
     * @return    xml文件的String
     * @throws JAXBException    
     * @throws IOException 
     */
    public static <T> String beanToXml(T t, String xmlPath) throws Exception{
    	Marshaller marshaller = JAXBContext.newInstance(t.getClass())
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
		marshaller.marshal(t, writer);
		File file = new File(xmlPath);
		BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
		bfw.write(writer.toString());
		bfw.close();
        return writer.toString();
    }
}
