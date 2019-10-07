package lau.server.servlet;

import lau.server.http.HttpRequest;
import lau.server.http.HttpResponse;

public abstract class HttpServlet {

	public abstract void service(HttpRequest request,HttpResponse response) ;
	
}
