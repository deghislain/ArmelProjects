<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<title>Roman Numeral Converter</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/myCss.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/bootstrap.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/bootstrap.mini.css" type="text/css"
	media="screen">
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
	<s:div id="wrapper" class="wrapper">
		<s:div id="container" class="container1">
			<form  class="form-signin">
			<s:div class="internalDiv">
					<s:div class="row">
					<s:div class="col-md-4"><label></label></s:div>
							<s:div class="col-md-6"><label>Invalid Number</label></s:div>
					</s:div>
					<s:div class="row">
							<s:div class="col-md-4"><label></label></s:div>
							<s:div class="col-md-6"><a href="index.jsp">Try Again</a></s:div>
					</s:div>
				</s:div>
			</form>
		</s:div>
	</s:div>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>