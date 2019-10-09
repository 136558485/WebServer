package lau.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lau.server.context.HttpContext;

public class HttpRequest {
	
	private String url;           
	private String method;  //请求方式
	private String protocal;  //http协议版本
	private String requestUrl;  //请求url

	private Map<String,String> parameters = new HashMap<String,String>();   //请求参数
	private Map<String,String> headers = new HashMap<String,String>();   //请求头
	
	private byte[] data;  //请求消息正文
	

	@SuppressWarnings("unused")
	private Socket socket;
	private InputStream in;
	
	public HttpRequest(Socket socket) {
		
		try {
			this.socket = socket;
			in = socket.getInputStream();
			//解析状态行
			parseStatLine();	
			//解析请求头
			parseHeaders();
			//解析消息正文
			parseContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析状态行
	 */
	private void parseStatLine() {
		String[] statArr = readLine().split("\\s");
		if(statArr.length<3) {
			return;
		}
		method = statArr[0];
		url = statArr[1];
		protocal = statArr[2];
		
		if(url.indexOf("?") != -1) {
			String[] urlSplit = url.split("\\?");
			requestUrl = urlSplit[0];
			if(urlSplit[1].isEmpty()) {
				return;
			}
			for(String param : urlSplit[1].split("&")){
				parameters.put(param.substring(0,param.indexOf("=")),param.substring(param.indexOf("=")+1));
			}
		}
		requestUrl = url;
		
	}
	
	/**
	 * 解析请求头
	 */
	private void parseHeaders() {
		String headLine = null;
		while(!(headLine = readLine()).isEmpty()) {
			String[] headSplit = headLine.split(":\\s");
			headers.put(headSplit[0], headSplit[1]);
		}
	}
	
	/**
	 * 解析消息正文
	 */
	private void parseContent(){
		if(headers.containsKey("Content-Length")) {
			int length = Integer.parseInt(headers.get("Content-Length"));
			byte[] data = new byte[length];
			try {
				in.read(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if("application/x-www-form-urlencoded".equals(headers.get("Content-type"))) {
				String params = new String(data);
				for(String param : params.split("&")){
					parameters.put(param.substring(0,param.indexOf("=")),param.substring(param.indexOf("=")+1));
				}
			}else {
				this.data = data;
			}
		}
	}
	
	/**
	 * 从请求消息头中解析读取一行数据
	 * @return
	 */
	private String readLine() {
		StringBuilder sb = new StringBuilder("");
		int b = 0;
		int a = 0;
		
		try {
			while((b = in.read())!=-1) {				
				sb.append((char)b);
				if(a == HttpContext.CR && b == HttpContext.LF) {					
					break;
				}
				a = b;				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return sb.toString().trim();
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public String getProtocal() {
		return protocal;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}

	public String getParameters(String key) {
		return parameters.get(key);
	}

	public String getHeaders(String key) {
		return headers.get(key);
	}
	
	public byte[] getData() {
		return data;
	}
	
	
}
