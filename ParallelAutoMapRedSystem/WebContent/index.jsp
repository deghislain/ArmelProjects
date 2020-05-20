<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Parallel Automated Map Reduce System</title>
</head>
<body>
	<div
		style="align-content: center; border-color: #333333; width: 70%; height: 635px; border-style: solid; border-width: 1px; padding-left: 400px;">
		<div style="padding-top: 80px"></div>
		<div style="align-content: center; border-color: #333333; width: 60%; height: 435px; border-style: solid; border-width: 5px; padding-top:10px;border-color: blue ">
			<H2 style="color:red">Web Application Performing Automated MapReduce</H2>
			<form action="index" method="Post" enctype="multipart/form-data">
				<div>
					<H3>Choose N, the number of parallel threads. N >= 1</H3>
					<label>Number of Threads</label>
					<input name="numThread" size="5">
				</div>
				<div style="padding-top: 5px">
					<H3>File Upload</H3>
					<label>Choose File</label> 
					<input type="file" name="fileUpload" id="fileUpload" />
					<input type="submit" value="Upload" />
				</div>
				<div style="padding-top: 100px; padding-left: 200px;"> 
					<input type = "submit" value="Perform MapReduce Computation" style="color:blue">
				</div>
				<div style="padding-top: 50px; padding-left: 250px" > 
					<input type = "submit" value="Display Results" style="color: green">
				</div>
			</form>
		</div>
	</div>
</body>
</html>