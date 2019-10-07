package lau.server.context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServletContext {
	
	private static Map<String,String> SERVLET_MAPPING = new HashMap<String,String>();
	
	static {
		initServletMapping();
	}
	
	private static void initServletMapping() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("webapps/WEB-INF/web.xml"));
			Element root = doc.getRootElement();
			List<Element> list = root.elements("servlet");
			for(Element en : list) {
				String key = en.elementText("servlet-name");
				String value = en.elementText("servlet-mapping");
				SERVLET_MAPPING.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static String getSERVLET_MAPPING(String key) {
		return SERVLET_MAPPING.get(key);
	}

//	public static void main(String[] args) {
//		initServletMapping();
//	}
}











