package lau.server.core;

import java.net.Socket;

import lau.server.http.HttpRequest;
import lau.server.http.HttpResponse;

public class ClientHandler extends Thread{

	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
		
	}
	public void run() {
		System.out.println("Handler处理...");
		HttpRequest request = new HttpRequest(socket);
		HttpResponse response = new HttpResponse(socket);
		
		
	}
}
