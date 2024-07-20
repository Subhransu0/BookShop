package in.s.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final String query = "SELECT  BOKNAME , BOOKEDITION , BOOKPRICE FROM bookdata where id =?";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		resp.setContentType("text/html");
		int id = Integer.parseInt(req.getParameter("id"));

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpractice", "root",
				"Subh123@"); PreparedStatement ps = connection.prepareStatement(query);) {
			ps.setInt(1, id);
			ResultSet rs1 = ps.executeQuery();
			rs1.next(); 
			out.println("<form action='editUrl?id="+id+"' method='Post'>");
			out.println("<table align='Center' border='2' style='border-collapse: collapse;'>");
			
			out.println("<tr>");
			out.println("<td>Book Name </td>");
			out.println("<td><input type='text' name ='bookName' value='"+rs1.getString(1)+"'> </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>Book Edition </td>");
			out.println("<td><input type='text' name ='bookEdition' value='"+rs1.getString(2)+"'> </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>Book Price </td>");
			out.println("<td><input type='text' name ='bookPrice' value='"+rs1.getFloat(3)+"'> </td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td><input type='submit' value='Edit'></td>");
			out.println("<td><input type='reeset' value='Cancel'></td>");
			out.println("</tr>");
			
			out.println("</table>");
			out.println("</form>");
			

		} catch (SQLException e) {
			e.printStackTrace();
			out.println("<h2>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<h2>" + e.getMessage() + "</h2>");
		}
		out.println("<a href='home.html'>Home</a>");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}
}
