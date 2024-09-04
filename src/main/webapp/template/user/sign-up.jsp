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
			<div class="card mb-5 mt-5">
				
				<c:if test="${not empty signupOk }">
					<c:choose>
						<c:when test="${signupOk }">
							<div class="alert alert-success" role="alert">Successfully
								created Account!</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-danger" role="alert">Failed to
								create account!</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<!-- form -->
				<form action="user" method="post">
					<input type="hidden" name="mode" value="SIGNUP">
					<div class="card-body">
					<h4 class="mb-3">Fill all information...</h4>
						<div class="mb-3">
							<label for="firstname" class="form-label">Firstname</label> <input
								type="text" class="form-control" id="firstname"
								name="firstname" required="required"></input>
						</div>
						<div class="mb-3">
							<label for="lastname" class="form-label">Lastname</label> <input
								type="text" class="form-control" id="lastname"
								name="lastname" required="required"></input>
						</div>
						<div class="mb-3">
							<label for="username" class="form-label">Username</label> <input
								type="text" class="form-control" id="username"
								name="username" required="required"></input>
						</div>
						<div class="mb-3">
							<label for="email" class="form-label">Email</label> <input
								type="text" class="form-control" id="email"
								name="email" required="required"></input>
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">Password</label> <input
								type="password" class="form-control" id="password"
								name="password" required="required"></input>
						</div>
						<button type="submit" class="btn btn-detail float-end">
							Sign Up</button>
						<p style="clear: both; font-size: 0.9rem">
							Already have an account? Sign in <a href="login"
								class="text-decoration-none text-muted">here</a>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>


	<c:import url="../common/footer.jsp" />
</body>
</html>