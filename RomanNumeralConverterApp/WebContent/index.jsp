<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Roman Numeral Converter App</title>
</head>
<body>
	<div style="padding-left: 500px; padding-top: 200px">
		<div style="align-content: center; border-color: #333333; width: 60%; height: 235px; border-style: solid; border-width: 1px;">
			<h2 style="padding-left: 75px">Roman Numeral Converter App</h2>
			<form method="Post" action="index">
				<div>
						<label>Numeral</label>
						<input name="numeral" type="text">
						<label>From </label>
						<select name="from" >
							<option>Roman-to-Number</option>
							<option>Number-to-Roman</option>
						</select>
						<input type="submit" value="Convert"/>
					</div>
					<div>
						<h3 style="color:red">Result: ${result}</h3>
					</div>
			</form>
		</div>
	</div>
</body>
</html>