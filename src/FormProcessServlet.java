import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class CookieExample
 */
@WebServlet("/FormProcessServlet")
public class FormProcessServlet extends HttpServlet {
	private static Logger logger = LoggerFactory.getLogger(FormProcessServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		logger.debug("init() 方法运行");
		super.init();

	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("-------------------");
		logger.debug("service() 方法运行");

		String requestMethod = request.getMethod();
		String requestURL = request.getRequestURL().toString();
		String requestProtocol = request.getProtocol();

		logger.debug("收到了用户对以下资源:");
		logger.debug(requestURL);
		logger.debug("发出了基于[" + requestProtocol + "] 的  " + requestMethod + "  的请求");
		
		//1. 获得所有的用户请求包含的参数名称列表，存入一个ArrayList中
		Enumeration<String> eu = request.getParameterNames();
		ArrayList<String> al = Collections.list(eu);
		Collections.sort(al);//给参数名称排序
		
		
		if (!al.isEmpty()) {
			logger.debug("并且在请求中，附加了如下的参数");
			for (String name : al) {
				if (name.equals("selectMultiple")) {
					logger.debug("有一个多选下拉列表");
					
					//2. 获得多选下拉列表框的所有内容
					String[] values = request.getParameterValues("selectMultiple");
					for (String v : values) {
						String newV=new String(v.getBytes("ISO-8859-1"), "UTF-8");//解码
						logger.debug(newV);
					}
					continue;
				}
				String value=new String(request.getParameter(name).getBytes("ISO-8859-1"), "UTF-8");
				String result = String.format("参数名称: %-10s 参数值: %-10s ", name,value);
				logger.debug(result);
			}
		}
		this.getServletContext().getRequestDispatcher("/result.html").forward(request, response);
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
