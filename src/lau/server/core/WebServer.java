package lau.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

	private ServerSocket server;
	private ExecutorService threadPool;
	
	public WebServer() {
		try {
			//初始化服务器端口
			server = new ServerSocket(8080);
			//初始化线程池
			threadPool = Executors.newFixedThreadPool(30);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		System.out.println("容器已启动！等待客户端连接...");
		//设置一个死循环，使其无限循环接收客户端请求
		while(true) {
			try {
				//使用套接字接收客户端传过来的消息
				Socket socket = server.accept();
				//将套接字传入到处理类，对请求进行处理
				ClientHandler client = new ClientHandler(socket);
				//将处理类放入到线程池进行处理
				threadPool.execute(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 服务器启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		WebServer webServer = new WebServer();
		webServer.start();
	}

}
