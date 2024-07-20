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
@WebServlet("/booklistS")
public class BookListServlet extends HttpServlet {
	private static final String query = "SELECT ID, BOKNAME , BOOKEDITION , BOOKPRICE FROM bookdata";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		resp.setContentType("text/html");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpractice", "root",
				"Subh123@"); PreparedStatement ps = connection.prepareStatement(query);) {
			ResultSet rs1 = ps.executeQuery();
			out.println("<table border='1' align = 'center' border='2' style='border-collapse: collapse;' >");
			out.println("<tr>");
			out.println("<th>Book Id</th>");
			out.println("<th>Book Name</th>");
			out.println("<th>Book Edition</th>");
			out.println("<th>Book Price</th>");
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");
			while (rs1.next()) {
				out.println("<tr>");
				out.println("<td>" + rs1.getInt(1) + "</td>");
				out.println("<td>" + rs1.getString(2) + "</td>");
				out.println("<td>" + rs1.getString(3) + "</td>");
				out.println("<td>" + rs1.getFloat(4) + "</td>");
				out.println("<td><a href='editScreen?id="+rs1.getInt(1)+"'>Edit</a></td>");
				
				out.println("<td><a href='deleteurl?id="+rs1.getInt(1)+"'>Delete</a></td>");
				out.println("</tr>");
			}
			out.println("</table>");

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}
}
