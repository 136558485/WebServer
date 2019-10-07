package lau.server.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lau.server.context.HttpContext;

public class HttpResponse {
	
	private int statusCode;
	private Map<String,String> header = new HashMap<String,String>();
	private byte[] data;
	private File entity;

	private Socket socket;
	private OutputStream out;
	
	public HttpResponse(Socket socket) {
		
		try {
			this.socket = socket;
			out = this.socket.getOutputStream();
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
		System.out.println("发送状态行！");
		String statusLine = "HTTP/1.1 "+statusCode+" "+HttpContext.getStatusCodeReasonMapping(statusCode);
		sendLine(statusLine);
		
	}

	private void sendHeader() {
		System.out.println("发送响应头！");
		for(Entry<String,String> en : header.entrySet()) {
			sendLine(en.getKey()+":"+en.getValue());
		}
		sendLine("");
	}

	private void sendContent() {
		System.out.println("发送响应正文！");
		try {
			if(this.data != null) {
				out.write(data);
			}else if(this.entity != null) {
				byte[] data = new byte[1024*10];
				int len = -1;
				FileInputStream fis = new FileInputStream(entity);
				while((len = fis.read(data)) != -1) {
					out.write(data,0,len);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendLine(String line) {
		try {
			out.write(line.getBytes("ISO8859-1"));
			out.write(HttpContext.CR);
			out.write(HttpContext.LF);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setHeader(String key,String value) {
		this.header.put(key, value);
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setEntity(File entity) {
		this.entity = entity;
	}
	
	
}
