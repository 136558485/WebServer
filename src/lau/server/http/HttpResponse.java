package lau.server.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class HttpResponse {
	
	private int statusCode;
	private Map<String,String> header;
	private Byte[] data;
	private File entity;

	private Socket socket;
	private OutputStream out;
	
	public HttpResponse(Socket socket) {
		
		try {
			this.socket = socket;
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将处理请求后的响应结果发送给客户端
	 */
	public void flush() {
		//发送状态行
		sendStatusLine();
		//发送响应头
		sendHeader();
		//发送响应正文
		sendContent();
	}

	

	private void sendStatusLine() {
		System.out.println("发送响应信息到客户端！");
		
	}

	private void sendHeader() {
		
		
	}

	private void sendContent() {
		
		
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setHeader(String key,String value) {
		this.header.put(key, value);
	}

	public void setData(Byte[] data) {
		this.data = data;
	}

	public void setEntity(File entity) {
		this.entity = entity;
	}
	
	
}
