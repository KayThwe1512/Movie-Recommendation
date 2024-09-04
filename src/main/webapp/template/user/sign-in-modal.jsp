<link href="static/fontawesome/css/all.min.css" rel="stylesheet">
<div class="modal fade" id="signInModal" tabindex="-1"
	aria-labelledby="signInModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="signInModalLabel">Sign In</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                    <i class="fa-solid fa-xmark"></i> <!-- Updated Font Awesome icon for close -->
                </button>
			</div>
			<div class="modal-body">
				<!-- Sign In Form -->
				<form action="login" method="post">
					<input type="hidden" name="mode" value="LOGIN">
					<div class="mb-3">
						<label for="username" class="form-label">Username or Email</label>
						<input type="text" class="form-control" id="username"
							name="username" required>
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">Password</label> <input
							type="password" class="form-control" id="password"
							name="password" required>
					</div>
					<button type="submit" class="btn btn-primary">Login</button>
					<p class="mt-3 mb-0">
						Don't have an account? Sign up <a href="movie"
							class="text-decoration-none" data-bs-toggle="modal"
							data-bs-target="#signUpModal">here</a>
					</p>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
    // Ensure the modal is shown when the page is loaded if there's an error
    
</script>