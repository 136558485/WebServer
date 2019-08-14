package lau.server.context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpContext {

	/**
	 * 在服务器启动时加载http协议相关定义内容
	 */
	
	
	/**
	 * 回车符
	 */
	public static final int CR = 13;
	/**
	 * 换行符
	 */
	public static final int LF = 10;
	
	/**
	 * 介质类型映射
	 */
	private static final Map<String,String> MIME_TYPE_MAPPING = new HashMap<String,String>();
	
	/**
	 * 状态码以及状态码原因映射
	 */
	private static final Map<Integer,String> STATUS_CODE_REASON_MAPPING = new HashMap<Integer,String>();
	
	/**
	 * 服务启动时加载
	 */
	static {
		/**
		 * 初始化介质类型映射
		 */
		intMimeTypeMapping();
		
		/**
		 * 初始化状态码映射
		 */
		initStatusCodeReasonMapping();
		
	}

	private static void initStatusCodeReasonMapping() {
		STATUS_CODE_REASON_MAPPING.put(200, "OK");
		STATUS_CODE_REASON_MAPPING.put(302, "Move Temporarily");
		STATUS_CODE_REASON_MAPPING.put(404, "Not Found");
		STATUS_CODE_REASON_MAPPING.put(500, "Internal Server Error");
        //.....还有很多后续进行补充		
	}

	private static void intMimeTypeMapping() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/web.xml"));
			Element root = doc.getRootElement();
			List<Element> eleList = root.elements("mime-mapping");
			for(Element ele : eleList) {
				String key = ele.elementText("extension");
				String value = ele.elementText("mime-type");
				MIME_TYPE_MAPPING.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static int getCr() {
		return CR;
	}

	public static int getLf() {
		return LF;
	}

	public static String getMimeTypeMapping(String key) {
		return MIME_TYPE_MAPPING.get(key);
	}

	public static String getStatusCodeReasonMapping(Integer key) {
		return STATUS_CODE_REASON_MAPPING.get(key);
	}
	
	public static void main(String[] args) {
		HttpContext context = new HttpContext();
		String str = context.MIME_TYPE_MAPPING.get("ico");
		System.out.println(str);
	}
	
	
}
