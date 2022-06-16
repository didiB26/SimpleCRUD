package com.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.web.jdbc.Student;

/**
 * Servlet implementation class TestServletList
 */
@WebServlet("/TestServletList")
public class TestServletList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/web-stud")
	private DataSource dataSource;
	private List<Student> students = new ArrayList<>();
	private DataBaseConn conex;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		conex = new DataBaseConn(dataSource);
		/*
		 * Connection conn = null; Statement stm = null; ResultSet res = null;
		 * 
		 * try {
		 * 
		 * conn = dataSource.getConnection(); String sql = "select * from student"; stm
		 * = conn.createStatement(); res = stm.executeQuery(sql);
		 * 
		 * while (res.next()) { int id = res.getInt("id"); String firstName =
		 * res.getString("first_name"); String lastName = res.getString("last_name");
		 * String email = res.getString("email");
		 * 
		 * // create new student object Student tempStudent = new Student(id, firstName,
		 * lastName, email);
		 * 
		 * // add it to the list of students students.add(tempStudent);
		 * 
		 * }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		/*
		 * pt debugging try { students = conex.getStudents(); } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * 
		 * out.print(students);
		 * 
		 */

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			listStudents(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get students from db util
		List<Student> students = conex.getStudents();

		// add students to the request
		request.setAttribute("STUDENT_LIST", students);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
