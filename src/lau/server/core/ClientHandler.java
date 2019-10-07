package lau.server.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import lau.server.context.HttpContext;
import lau.server.context.ServletContext;
import lau.server.http.HttpRequest;
import lau.server.http.HttpResponse;
import lau.server.servlet.HttpServlet;

public class ClientHandler extends Thread{

	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
		
	}
	public void run() {
		System.out.println("Handler处理...");
		HttpRequest request = new HttpRequest(socket);
		HttpResponse response = new HttpResponse(socket);
		
		String url = request.getRequestUrl();
		if(url == null) {
			return;
		}
		
		String servletName = ServletContext.getSERVLET_MAPPING(url);
		
		if(servletName != null) {
			try {
				Class cla = Class.forName(servletName);
				HttpServlet servlet = (HttpServlet) cla.newInstance();
				servlet.service(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}else {

			File file = new File("webapps"+url);
			if(file.exists()) {
				String fileType = url.substring(url.indexOf(".")+1);
				response.setHeader("Content-Type", HttpContext.getMimeTypeMapping(fileType));
				
				long length = file.length();
				response.setHeader("Content-Length", String.valueOf(length));
				
				response.setStatusCode(200);
				response.setEntity(file);
				response.flush();
				
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("没找到文件");
			}
		}		
	}
}
