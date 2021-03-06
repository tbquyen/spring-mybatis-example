<%@page pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="id" dir="ltr">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>spring-mybatis-example</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
	
	<jsp:include page="navbar.jsp" />
	<div class="container-fluid">
		<div>
			<h1 class="text-danger">${errorCode}</h1>
		</div>
		<div>
			<h4>OPPSSS!!!! Sorry...!</h4>
			<p>${msg}</p>
			<hr>
			<p class="mb-0">Whenever you need to, be sure to use margin
				utilities to keep things nice and tidy.</p>
			<a class="btn btn-danger" href=".">Go Back</a>
		</div>
		<c:set var="exception" value="${exception}" />

		<c:if test="${exception != null}">
			<p style="color: red;">
				<c:out value="${exception.message}" />
			</p>
			<div
				style="height: 35vh; width: 100%; overflow: auto; border: 1px solid red;">
				<c:forEach items="${exception.cause.stackTrace}" var="element">
					<p>
						<c:out value="${element}" />
					</p>
				</c:forEach>
			</div>
		</c:if>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>