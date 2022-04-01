<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>spring-mybatis-example</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
<link rel="stylesheet" href="resources/app/css/app.css">
</head>
<body>
	<jsp:include page="../app/navbar.jsp" />

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-8 d-flex">
				<button type="button" id="btn-new" class="btn btn-primary btn-sm">
					<i class="bi bi-person-plus-fill"></i>
				</button>
			</div>
			<div class="col-sm-4 d-flex">
				<div class="input-group ms-5">
					<input id="txt-search" type="text" class="form-control form-control-sm" placeholder="Tài khoản">
					<select class="form-select form-select-sm" id="txt-role">
						<option selected value="">Chức vụ</option>
						<option value="ROLE_ADMIN">Quản lý</option>
						<option value="ROLE_MEMBER">Nhân viên</option>
					</select>
					<button id="btn-search" class="btn btn-outline-secondary btn-sm">
						<i class="bi bi-search"></i>
					</button>
				</div>
			</div>
		</div>
		<div class="row">
			<table id="usersTable" class="table table-sm table-bordered table-striped">
				<thead>
					<tr>
						<th class="text-center">#ID</th>
						<th class="text-center">Tài khoản</th>
						<th class="text-center">Mật khẩu</th>
						<th class="text-center">Chức vụ</th>
						<th class="text-center">Trạng thái</th>
						<th class="text-center"></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- Modal -->
	<div id="modal-area" class="modal fade" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
	<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
	<script src="resources/app/js/app.js"></script>
	<script src="resources/app/js/users.js"></script>
</body>
</html>