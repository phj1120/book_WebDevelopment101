package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 파라미터 해석
		String name = request.getParameter("name");
		System.out.println("hi");
//		비즈니스 로직 실행
		process(name);
		
//		response 구축
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<html>");
		out.print(name);
		out.print("</html>");
	}

	private void process(String name) {
		System.out.println("name");
	}
}
