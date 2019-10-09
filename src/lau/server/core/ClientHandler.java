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
		//将socket传入request对请求进行解析
		HttpRequest request = new HttpRequest(socket);
		//将socket出入response对请求进行响应
		HttpResponse response = new HttpResponse(socket);
		
		String url = request.getRequestUrl();
		System.out.println("处理请求： "+url);
		if(url == null) {
			return;
		}
		
		String servletName = ServletContext.getSERVLET_MAPPING(url);
		
		try {
			//判断是否为servlet请求，是则调用相应servlet类处理请求，否则当作静态请求处理
			if(servletName != null) {
				try {
					Class cla = Class.forName(servletName);
					HttpServlet servlet = (HttpServlet) cla.newInstance();
					servlet.service(request, response);
				} catch (Exception e) {
					e.printStackTrace();
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
					
				}else {
					System.out.println("没找到文件");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
				
	}
}
