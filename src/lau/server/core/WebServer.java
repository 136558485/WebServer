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
			server = new ServerSocket(8080);
			threadPool = Executors.newFixedThreadPool(30);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		while(true) {
			try {
				System.out.println("等待客户端连接...");
				Socket socket = server.accept();
				ClientHandler client = new ClientHandler(socket);
				threadPool.execute(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		WebServer webServer = new WebServer();
		webServer.start();
	}

}
