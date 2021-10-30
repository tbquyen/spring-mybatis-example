<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>LOGIN</title>
</head>
<body>
	<f:form action="login" modelAttribute="LoginForm">
		<h3>LOGIN-PAGE</h3>
		<f:input path="username" autofocus="autofocus" />
		<f:errors path="username" />
		<br>
		<f:password path="password" />
		<f:errors path="password" />
		<br>
		<input type="checkbox" name="rememberme">Remember me
		<f:errors />
		<br>
		<input type="submit" value="LOGIN">
	</f:form>
</body>
</html>