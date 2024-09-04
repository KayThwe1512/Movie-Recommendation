<style type="text/css">
	body{
		background: linear-gradient(to right, #948e99, #2e1437 ) !important;
	}
</style>
<div class="modal fade" id="signUpModal" tabindex="-1"
	aria-labelledby="signUpModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="signUpModalLabel">Sign Up</h5>
				<button type="button" class="btn-close btn-danger" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
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
				<!-- Sign Up Form -->
				<form action="user" method="post">
					<input type="hidden" name="mode" value="SIGNUP">
					<div class="mb-3">
						<label for="firstname" class="form-label">Firstname</label> <input
							type="text" class="form-control" id="firstname" name="firstname"
							required>
					</div>
					<div class="mb-3">
						<label for="lastname" class="form-label">Lastname</label> <input
							type="text" class="form-control" id="lastname" name="lastname"
							required>
					</div>
					<div class="mb-3">
						<label for="username" class="form-label">Username</label> <input
							type="text" class="form-control" id="username" name="username"
							required>
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> <input
							type="text" class="form-control" id="email" name="email" required>
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">Password</label> <input
							type="password" class="form-control" id="password"
							name="password" required>
					</div>
					<button type="submit" class="btn btn-primary">Sign Up</button>
					<p class="mt-3 mb-0">
						Already have an account? Sign in <a href="login"
							class="text-decoration-none" data-bs-toggle="modal"
							data-bs-target="#signInModal">here</a>
					</p>
				</form>
			</div>
		</div>
	</div>
</div>