package com.shourav.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher rd = null;
        
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/login_registration?useSSL=false","root", "s1234M");
            PreparedStatement ps = con.prepareStatement("select * from users where uemail=? and upwd=?");
            ps.setString(1, uemail);
            ps.setString(2, upwd);
            ResultSet res = ps.executeQuery();
            
            if(res.next()) {
            	session.setAttribute("name", res.getString("uname"));
            	rd = request.getRequestDispatcher("index.jsp");
            }
            else {
            	request.setAttribute("status", "failed");
            	rd = request.getRequestDispatcher("login.jsp");
            }
            rd.forward(request, response);

        }catch (Exception e){
            e.printStackTrace();
        }

	}

}
