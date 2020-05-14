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
					<h4>Temperature Converter</h4>
					<label>Temperature</label>
					<input name="temp" type="text">
					<label>From C/F</label>
					<select name="from" >
						<option>C</option>
						<option>F</option>
					</select>
					<input type="submit" value="Convert"/>
					<h3 style="color: red"><% String result = (String)request.getSession().getAttribute("newTemp");
					        if(result != null){ out.print(result);}%></h3>
				</div>
			</form>
		</div>
	</body>
</html>