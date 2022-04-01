<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light mb-1" style="min-height: 56px">
	<div class="container-fluid">
		<ul class="nav">
			<sec:authorize url="/">
				<li class="nav-item"><a class="nav-link" href="./"><i class="bi bi-house-door-fill"></i> NAV</a></li>
			</sec:authorize>
			<sec:authorize url="/users">
				<li class="nav-item"><a class="nav-link" href="./users"><i class="bi bi-person-lines-fill"></i> User</a></li>
			</sec:authorize>
		</ul>

		<ul class="nav">
			<sec:authorize access="isAuthenticated()">
				<li class="nav-item dropstart"><a class="nav-link" href="#" id="user-logo" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i
						class="bi bi-person-badge-fill"></i> <sec:authentication property="principal.username" /></a>
					<ul class="dropdown-menu dropdown-menu-lg-end" aria-labelledby="navbarDarkDropdownMenuLink">
						<sec:authorize url="/changePassword">
							<li><a class="dropdown-item" href="./changePassword"><i class="bi bi-shield-lock"></i> Đổi mật khẩu</a></li>
						</sec:authorize>
						<li><a class="dropdown-item" href="./logout"><i class="bi bi-box-arrow-in-right"></i> Thoát</a></li>
					</ul></li>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<li class="nav-item dropstart"><a class="nav-link" href="./login"><i class="bi bi-box-arrow-in-left"></i> Login</a></li>
			</sec:authorize>
		</ul>
	</div>
</nav>
<div aria-live="polite" aria-atomic="true" class="sticky-top">
	<div id="toast-container" class="toast-container position-absolute top-0 end-0"></div>
</div>