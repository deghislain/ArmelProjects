<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>


<html>
<head>
<link href="css/msgCSS.css" rel="stylesheet">
<%@page isELIgnored="false"%>
<meta charset="ISO-8859-1">
<title>Message Buffer Service</title>
</head>
<body>
	<div id="extDiv">
		<div style="padding-top: 100px;"></div>
		<div id="intLeftDiv">
			<div class="msgDiv">
				<h3 style="padding-left: 100px">Send Messages</h3>
				<table>
					<tr>
						<td><label>From</label></td>
						<td><input /></td>
					</tr>
					<tr>
						<td><label>To</label></td>
						<td><input /></td>
					</tr>
					<tr>
						<td><label>Message</label></td>
						<td><textarea rows="6" cols="40"></textarea></td>
					</tr>
				</table>
				<div style="padding-top: 40px; padding-left: 150px">
					<input type="submit" value="Send" style="color: green">
				</div>
			</div>
		</div>
		<div id="intRightDiv">
			<div class="msgDiv">
				<h3 style="padding-left: 100px">Receive Messages</h3>
				<table>
					<tr>
						<td><label>ReceiverID</label></td>
						<td><input /><input type="submit" value="Receive Now" style="color: green;"></td>
						<td></td>
					</tr>
					<tr>
						<td><label>Purge</label></td>
						<td><input type="checkbox"/> </td>
						<td></td>
					</tr>
					<tr>
						<td><label>Messages</label></td>
						<td><textarea rows="12" cols="50" style="overflow:scroll;"></textarea></td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>

	</div>

</body>
</html>