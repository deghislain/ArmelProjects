<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<header>
						<h3>
							<a href="#">Roman Numeral Converter</a>
						</h3>
					</header>
					
			<form action="doConvert" class="form-signin" method="get">
				<s:div class="internalDiv">
					<s:div class="row">
						<s:div class="col-md-6">
							<label>Number To Be Comverted</label>
						</s:div>
						<s:div class="col-md-4">
							<input name="numeral" class="form-control" placeholder="From"
								value="${sessionScope.conv.numeral}" />
						</s:div>
					</s:div>
					<c:choose>
						<c:when test="${!empty sessionScope.conv.errorMessage}">
							<s:div class="col-md-10">
								<h4 class="h4Style">${sessionScope.conv.errorMessage}</h4>
							</s:div>
						</c:when>
						<c:otherwise>
							<s:div class="row">
								<s:div class="col-md-6">
									<label>Result</label>
								</s:div>
								<s:div class="col-md-4">
									<input name="result" class="form-control" placeholder="To"
										value="${sessionScope.conv.result}" />
								</s:div>
							</s:div>
						</c:otherwise>
					</c:choose>
					<label></label>
					<s:div class="row">
						<s:div class="col-md-6">
							<select name="converterType">
								<option>Select The Convertion Type</option>
								<option value="Roman">Roman</option>
								<option value="Number">Number</option>
							</select>
						</s:div>
						<s:div class="col-md-4">
							<button class="btn btn-lg btn-primary btn-block" type="submit">Convert</button>
						</s:div>
					</s:div>
			</form>
		</s:div>
	</s:div>
	</s:div>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>