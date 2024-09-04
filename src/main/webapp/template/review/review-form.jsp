<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- head -->
<head>
<c:import url="../common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<style type="text/css">
	body{
		background: linear-gradient(to right, #948e99, #2e1437 ) !important;
	}
	.btn-review{
		color: white;
		text-decoration: none;
		margin:2px;
	}
	.btn-review:hover{
		background-color: transparent;
	color: rgb(106, 15, 106);
	text-decoration: none;
	}
	
</style>
<script>
	function confirmSignIn(signinlink) {
		if (confirm("You need to sign in first to add this movie to your watch later list. Click OK to sign in.")) {
			window.location.href = signinlink;
		}
	}
</script>
</head>
<body>
	<!-- Nav Bar Start -->
	<c:import url="../common/nav.jsp" />
	<!-- Nav Bar End -->

	<c:url var="signinlink" value="login">
		<c:param name="mode" value="SIGNUP" />
		<c:param name="movieId" value="${movie.id}" />
	</c:url>

	<!--  container -->
	<div class="container-md">
		<div class="col-md-7 mx-auto">
			<div class="card mb-3">
				<div class="card-header">
					<h4 class="card-text">New Review Form</h4>
				</div>
				<div class="card-body">
					<c:if test="${not empty insertOk }">
						<c:choose>
							<c:when test="${insertOk }">
								<div class="alert alert-success" role="alert">
									Successfully created review!</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-danger" role="alert">Failed to
									create review!</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<!-- form -->
					<form action="review" method="post">
						<input type="hidden" name="mode" value="ADD_REVIEW">  <input
							type="hidden" name="userId" value="${user.id }">
							<input
							type="hidden" name="movieId" value="${movie.id }">
						<div class="mb-3">
							<label for="title" class="form-label">Title</label> <input
								type="text" class="form-control" id="title" name="title"
								required="required">
						</div>
						<div class="mb-3">
							<label for="reviewtext" class="form-label">Review Text</label>
							<textarea rows="8" class="form-control" id="reviewtext"
								name="reviewtext" required="required"></textarea>
						</div>

						<div class="mb-3">
							<label>Rate this movie:</label>
        					<div class="rating">
  								<input type="radio" name="rating" value="10" id="5"><label for="5">☆</label>
  								<input type="radio" name="rating" value="8" id="4"><label for="4">☆</label>
  								<input type="radio" name="rating" value="6" id="3"><label for="3">☆</label>
  								<input type="radio" name="rating" value="4" id="2"><label for="2">☆</label>
  								<input type="radio" name="rating" value="2" id="1"><label for="1">☆</label>
							</div>
						</div>

						<c:choose>
							<c:when test="${user == null}">
								<a href="javascript:void(0);" class="btn-review "
									onclick="confirmSignIn('${signinlink}')">Submit</a>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn-review float-end">Submit</button>
							</c:otherwise>
						</c:choose>
						<c:url var="reviewlink" value="review">
							<c:param name="mode" value="REVIEW" />
							<c:param name="movieId" value="${movie.id }" />
							<c:param name="userId" value="${user.id }" />
						</c:url>
						<a href="${reviewlink}" class="btn mx-2">Review</a>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	document.querySelectorAll('.rating input').forEach((radio) => {
		  radio.addEventListener('change', function() {
		    const rating = this.value;
		    const movieId = getMovieId(); 

		    fetch('/rate-movie', {
		      method: 'POST',
		      headers: { 'Content-Type': 'application/json' },
		      body: JSON.stringify({ movie_id: movieId, rating: rating })
		    })
		    .then(response => response.json())
		    .then(data => {
		      // Handle success or error feedback
		    });
		  });
		});
	</script>
	<!-- footer -->
	<c:import url="../common/footer.jsp" />
</body>
</html>