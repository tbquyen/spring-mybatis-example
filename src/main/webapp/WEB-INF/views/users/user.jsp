<%@page import="com.github.tbquyen.master.users.UsersConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<f:form cssClass="modal-body" modelAttribute="<%=UsersConst.F_NAME%>" onsubmit="return false;">
	<h5 class="modal-title col-12 text-center">USER<span class="spinner-grow text-danger float-end invisible" role="status" id="form-spinner"></span></h5>
	<hr>
	<div class="mb-3 row">
		<label for="username" class="col-sm-2 col-form-label">Email</label>
		<div class="col-sm-10">
			<f:input path="username" autofocus="autofocus" cssClass="form-control form-control-sm"
				cssErrorClass="form-control form-control-sm is-invalid" />
			<f:errors path="username" cssClass="invalid-feedback" />
		</div>
	</div>
	<div class="mb-3 row">
		<label for="password" class="col-sm-2 col-form-label">Password</label>
		<div class="col-sm-10">
			<f:password path="password" cssClass="form-control form-control-sm" cssErrorClass="form-control form-control-sm is-invalid"/>
			<f:errors path="password" cssClass="invalid-feedback" />
		</div>
	</div>
	<div class="mb-3 row">
		<label for="role" class="col-sm-2 col-form-label">Role</label>
		<div class="col-sm-10">
			<f:select path="role" cssClass="form-select form-select-sm" cssErrorClass="form-select form-select-sm is-invalid">
				<f:option value=""></f:option>
				<f:option value="ROLE_ADMIN">Quản lý</f:option>
				<f:option value="ROLE_MEMBER">Nhân viên</f:option>
			</f:select>
			<f:errors path="role" cssClass="invalid-feedback" />
		</div>
	</div>
	<f:errors />
	<hr>
	<button type="submit" class="btn btn-primary btn-sm float-end"><i class="bi bi-person-check-fill"></i> Update</button>
	<button type="button" class="btn btn-secondary btn-sm float-end me-1" data-bs-dismiss="modal">Close</button>
	<f:hidden path="id"/>
	<f:hidden path="status"/>
</f:form>
