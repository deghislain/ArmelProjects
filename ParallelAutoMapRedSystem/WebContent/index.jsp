<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%-- <%@ taglib prefix="sf" uri = "http://www.springframework.org/tags/form"%> --%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<html>
<head>

<meta charset="ISO-8859-1">
<title>Parallel Automated Map Reduce System</title>
</head>
<body>

	<div style="align-content: center; border-color: #333333; width: 70%; height: 635px; border-style: solid; border-width: 1px; padding-left: 400px;">
		<div style="padding-top: 20px"></div>
		<div
			style="align-content: center; border-color: #333333; width: 60%; height: 435px; border-style: solid; border-width: 5px; padding-top: 10px; border-color: blue">
			<H2 style="color: red">Web Application Performing Automated MapReduce</H2>
			<form method="Post" enctype="multipart/form-data" action="paraMapRedCont/upload">
					<div style="padding-top: 5px">
						<h4 style="color:maroon;">Status: ${status}</h4>
						<H3>File Upload</H3>
						<label>Choose File</label> 
						<input type="file" name="fileUpload"/> 
						<input type="submit" value="Upload" name="fUpload"/>
					</div>
			</form>
			<form method="post" action="paraMapRedCont/partition">
					<div>
						<H3>Choose N, the number of parallel threads. N >= 1</H3>
						<label>Number of Threads</label> 
						<input name="numThread" size="5"/>
					</div>
					<div style="padding-top: 70px; padding-left: 200px;">
						<input type="submit" value="Perform MapReduce Computation" style="color: blue">
					</div>
			</form>
			<form method="post" action="paraMapRedCont/display">
					<div style="padding-top: 40px; padding-left: 250px">
						<input type="submit" value="Display Results" style="color: green">
					</div>
			</form>
			<div>
			</div>
		</div>
		<div class="row">
    <div style="padding-top:20px;" >
        <div style="align-content: center; border-color: #333333; width: 60%; height: 120px; border-style: solid; border-width: 1px; overflow:scroll">
            <h3 style="color:red; padding-left: 250px">Results: ${message}</h3>
            <table style="align-content: center; border-color: #333333; width: 50%">
            	<tr>
            		<th style="border-style: solid;">Word</th>
            		<th style="border-style: solid;">Frequency</th>
            	</tr>
            	<c:forEach var="item" items="${words}">
            		<tr><td style="border-style: solid;"><c:out value="${item.key}"/></td> <td style="border-style: solid;"><c:out value="${item.value}"/> </td></tr>
            	</c:forEach>
        	</table>
        </div>

        </div>
    </div>
	</div>
</body>
</html>