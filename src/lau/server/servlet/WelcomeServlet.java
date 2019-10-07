package lau.server.servlet;

import lau.server.http.HttpRequest;
import lau.server.http.HttpResponse;

public class WelcomeServlet extends HttpServlet{

	@Override
	public void service(HttpRequest request, HttpResponse response) {

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<title>");
		sb.append("欢迎你！客户端！");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("欢迎使用本web容器！");
		sb.append("</h1>");
		sb.append("</body>");
		sb.append("</html>");
		try {
			byte[] data = sb.toString().getBytes();
			response.setStatusCode(200);
			response.setHeader("Content-Type", "text/html");
			response.setHeader("Content-Length", data.length+"");
			response.setData(data);
			response.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
