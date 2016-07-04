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
			<form action="doConvert" class="form-signin">
				<s:div class="internalDiv">
					<s:div class="row">
						<s:div class="col-md-6"><label>Result</label></s:div>
<%-- 						<s:div class="col-md-4"><s:textfield  name="result" class="form-control" placeholder="To" value="%{converter.result}"/></s:div> --%>
					<s:div class="col-md-4"><s:textfield key=""/></s:div>
					</s:div>
					<label></label>
					<s:div class="row">
						<s:div class="col-md-6">
							<select name="converterType">
								<option value="">Select The Convertion Type</option>
  								<option value="Roman">Roman</option>
  								<option value="Number">Number</option>
							</select>
						</s:div>
						<s:div class="col-md-4"><button class="btn btn-lg btn-primary btn-block" type="submit">Convert</button></s:div>
					</s:div>
			</form>
		</s:div>
		</s:div>
	</s:div>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>