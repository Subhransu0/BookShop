package in.s.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/RegisterS")
public class RegisterServlet extends HttpServlet {
	private static final String query = "INSERT INTO bookdata(BOKNAME , BOOKEDITION , BOOKPRICE) VALUES(?,?,?)";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		resp.setContentType("text/html");
		String bookName1 = req.getParameter("bookName");
		String bookEdition1 = req.getParameter("bookEdition");
		float bookPrice1 = Float.parseFloat(req.getParameter("bookPrice"));

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbpractice", "root",
				"Subh123@"); PreparedStatement ps = connection.prepareStatement(query);) {
			ps.setString(1, bookName1);
			ps.setString(2, bookEdition1);
			ps.setFloat(3, bookPrice1);
			int i = ps.executeUpdate();
			if(i == 1) {
				out.println("<h2>Record Register Successfully</h2>");
			}else {
				out.println("<h2>Record is Not Registered</h2>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			out.println("<h2>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<h2>" + e.getMessage() + "</h2>");
		}
		out.println("<a href='booklistS'>Book List</a>");
		out.println("<br>");
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
