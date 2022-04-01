<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>spring-mybatis-example</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
	<div class="container-fluid">
		<div class="modal show" tabindex="-1" style="display: block;">
			<div class="modal-dialog">
				<div class="modal-content">
					<f:form cssClass="modal-body" action="login" modelAttribute="LoginForm">
						<h5 class="modal-title col-12 text-center">LOGIN PAGE</h5>
						<hr>
						<div class="mb-3 row">
							<label for="username" class="col-sm-2 col-form-label">Email</label>
							<div class="col-sm-10">
								<f:input path="username" autofocus="autofocus" cssClass="form-control form-control-sm" cssErrorClass="form-control form-control-sm is-invalid" />
								<f:errors path="username" cssClass="invalid-feedback" />
							</div>
						</div>
						<div class="mb-3 row">
							<label for="password" class="col-sm-2 col-form-label">Password</label>
							<div class="col-sm-10">
								<f:password path="password" cssClass="form-control form-control-sm" cssErrorClass="form-control form-control-sm is-invalid" />
								<f:errors path="password" cssClass="invalid-feedback" />
							</div>
						</div>
						<div class="mb-3 row">
							<span class="col-sm-2 col-form-label"></span>
							<div class="col-sm-10">
								<label for="rememberme" class="form-check-label"><input class="form-check-input" type="checkbox" name="rememberme" id="rememberme">
									Remember me</label>
							</div>
						</div>
						<f:errors />
						<hr>
						<input type="submit" class="btn btn-primary btn-sm float-end" value="LOGIN">
					</f:form>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>