package lau.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class HttpRequest {
	
	private String url;
	private String method;
	private String protocal;
	private String requestUrl;

	private Map<String,String> parameters;
	private Map<String,String> headers;
	

	private Socket socket;
	private InputStream in;
	
	public HttpRequest(Socket socket) {
		
		try {
			this.socket = socket;
			in = socket.getInputStream();
			/**
			 * 解析请求头
			 */
			parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parse() {
		System.out.println("开始解析请求！");		
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
	
	
}
