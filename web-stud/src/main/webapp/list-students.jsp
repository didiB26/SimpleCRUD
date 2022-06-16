<%@ page import="java.util.*, com.web.jdbc.*"%>
<!DOCTYPE html>
<html>

<head>
<title>Student Tracker App</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<%
// get the students from the request object (sent by servlet)
List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");
%>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>University</h2>
		</div>
	</div>

	<div id="container">

		<div id="content">

			<!-- put new button: Add Student

			<input type="button" value="Add Student"
				onclick="window.location.href='add-student.jsp'; return false;"
				class="add-student-button" /> 
				
				 -->
				 
				 <a href='index.html'>Index</a>
				 <hr/>
				<a href='add-student.jsp'>Adauga stud</a>
				<br />
			<br />
			<table border=1>

				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Update</th>
				</tr>

				<%
				for (Student tempStudent : theStudents) {
				%>

				<tr>
					<td><%=tempStudent.getFirstName()%></td>
					<td><%=tempStudent.getLastName()%></td>
					<td><%=tempStudent.getEmail()%></td>
					<td><a href='ServletList?command=Load&studentId=<%=tempStudent.getId()%>'>Update</a>
					/
					<a href='ServletList?command=DELETE&studentId=<%=tempStudent.getId()%>'>Delete</a>					
					</td>
				</tr>

				<%
				}
				%>

			</table>

		</div>

	</div>
</body>

</html>








