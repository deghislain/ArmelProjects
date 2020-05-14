<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Small Services System</title>
	</head>
	<body>
		<div style="align-content: center; border-color: #333333; width: 95%; height: 635px; border-style: solid; border-width: 1px; padding-left: 65px">
			<h2 style="padding-left: 500px">Collection of Small Sevices</h2>
			<form method="Post" action="SmallServicesControler">
				<div>
					<h3>Temperature Converter</h3>
					<label>Temperature</label>
					<input name="temp" type="text">
					<input type="hidden" value="tempConv" name ="action"/>
					<label>From C/F</label>
					<select name="from" >
						<option>C</option>
						<option>F</option>
					</select>
					<input type="submit" value="Convert"/>
					<h4 style="color: red"><% String result = (String)request.getSession().getAttribute("newTemp");
					        if(result != null){ out.print(result);}%>
					</h4>
				</div>
			</form>
			<form action="SmallServicesControler" method="Post">
				<div style="padding-top: 5px">
				<h3>Numbers Sorter</h3>
					<label>Insert a string of numbers here e.g: "1,2,3.."</label>
					<input name="strNum" size="50">
					<input  type="submit" value="Sort" />
					<input type="hidden" value="sorter" name ="action"/>
					<h4 style="color: red"><% String sortedStr = (String)request.getSession().getAttribute("sortedStr");
					        if(sortedStr != null){ out.print(sortedStr);}%>
					</h4>
				</div>
			</form>
		</div>
	</body>
</html>