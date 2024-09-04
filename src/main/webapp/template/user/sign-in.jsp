<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="../common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<style type="text/css">
	body{
		background: linear-gradient(to right, #948e99, #2e1437 ) !important;
	}
</style>
</head>
<body>
<c:import url="../common/nav.jsp" />
	<div class="container-md">
		<div class="col-md-5 mx-auto">
			<div class="card mb-3 mb-3 mt-5">
				<div class="card-header">
					<h4>Login In here</h4>
				</div>
				<div class="card-body">
					<c:if test="${not empty loginOk and not loginOk}">
						<div class="alert alert-danger" role="alert">
						Username or password is incorrect!</div>
					</c:if>
					<!-- form -->
						<form action="login" method="post">
						<input type="hidden" name="mode" value="LOGIN">
						<div class="mb-3">
							<label for="username" class="form-label">Username or
								Email</label> <input type="text" class="form-control" id="username"
								name="username" required="required">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">Password</label> <input
								type="password" class="form-control" id="password"
								name="password" required="required"></input>
						</div>
						<button type="submit" class="btn btn-detail float-end">Login</button>
						<p style="clear: both; font-size: 0.9rem">
							Don't have an account? Sign up <a href="user"
								class="text-decoration-none text-muted">here</a>
						</p>
					</form>
					</div>
					
				</div>
			</div>
		</div>

	<c:import url="../common/footer.jsp" />
</body>
</html>