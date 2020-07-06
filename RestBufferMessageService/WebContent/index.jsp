<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>


<html>
<head>
<link href="msgCSS.css" rel="stylesheet">
<%@page isELIgnored="false"%>
<meta charset="ISO-8859-1">
<title>Message Buffer Service</title>
</head>
<body>
	<div id="extDiv">
		<div style="padding-top: 100px;"></div>
			<div id="intLeftDiv">
				<div id="sendMsgDiv">
				<h3 style="padding-left: 100px">Send Messages</h3>
					<table>
						<tr>
							<td><label>From</label></td>
							<td> <input /></td>
						</tr>
						<tr>
							<td><label>To</label></td>
							<td> <input /></td>
						</tr>
						<tr>
							<td><label>Message</label></td>
							<td> <textarea rows="6" cols="40"></textarea></td>
						</tr>
					</table>
					<div style="padding-top: 40px; padding-left: 150px">
						<input type="submit" value="Send" style="color: green">
				</div>
				</div>
			</div>
			<div id="intRightDiv"></div>
		
	</div>

</body>
</html>