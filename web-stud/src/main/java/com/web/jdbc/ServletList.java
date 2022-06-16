package com.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class ServletList
 */
@WebServlet("/ServletList")
public class ServletList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/web-stud")
	private DataSource dataSource;
	private DataBaseConn conex;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//for my debug
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		conex = new DataBaseConn(dataSource);

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
				listStudents(request, response);
			}
			
			if (theCommand.equals("Load")) {
				loadStudent(request, response);

			}
			
			if (theCommand.equals("DELETE")) {
				deleteStudent(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// route to the appropriate method
			switch (theCommand) {

			case "ADD":
				addStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);

			default:
				listStudents(request, response);
			}

		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String theStudentId = request.getParameter("studentId");
		//System.out.println(theStudentId);
		
		// get student from database (db util)
		conex.deleteStudent(theStudentId);
		
		listStudents(request, response);
		
		
	}
	
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

			// read student id from form data
			String theStudentId = request.getParameter("studentId");
			//System.out.println(theStudentId);
			
			// get student from database (db util)
			Student theStudent = conex.getStudent(theStudentId);
			
			// place student in the request attribute
			request.setAttribute("THE_STUDENT", theStudent);
			
			// send to jsp page: update-stud
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("/update-stud.jsp");
			dispatcher.forward(request, response);		
		}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new student object
		Student theStudent = new Student(id, firstName, lastName, email);
		
		// perform update on database
		conex.updateStudent(theStudent);
		
		// send them back to the "list students" page
		listStudents(request, response);
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create a new student object
		Student theStudent = new Student(firstName, lastName, email);

		// add the student to the database
		conex.addStudent(theStudent);

		// send back to main page (the student list)
		listStudents(request, response);
		
		response.sendRedirect(request.getContextPath() + "/ServletList?command=LIST");

	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get students from db util
		List<Student> students = conex.getStudents();

		// add students to the request
		request.setAttribute("STUDENT_LIST", students);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
