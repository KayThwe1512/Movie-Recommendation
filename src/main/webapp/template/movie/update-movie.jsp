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
</style>
</head>
<body>
	<!-- Nav Bar Start -->
	<c:import url="../common/nav.jsp" />
	<!-- Nav Bar End -->


	<!--  container -->
	<div class="container-md">
		<div class="col-md-7 mx-auto">
			<div class="card mb-3">
				<div class="card-header">
					<h4>Update Movie Form</h4>
				</div>
				<div class="card-body">
					<c:if test="${not empty updateOk and not updateOk}">
						<div class="alert alert-danger" role="alert">Failed to
									update movie!</div>
					</c:if>
					<c:if test="${updateOk}">
						<div class="alert alert-success" role="alert">Success to
									update movie!</div>
					</c:if>
					<!-- form -->
					<form action="movie" method="post">
						<input type="hidden" name="mode" value="UPDATE">
						<input type="hidden" name="movieId" value="${movie.id }">
						<div class="mb-3">
							<label for="title" class="form-label">Title</label> <input
								type="text" class="form-control" id="title" name="title"
								value="${movie.title }" required="required">
						</div>
						<div class="mb-3">
							<label for="synopsis" class="form-label">Synopsis</label>
							<textarea rows="8" class="form-control" id="synopsis"
								name="synopsis" required="required">${movie.synopsis } </textarea>
						</div>
						<div class="mb-3">
							<label for="casting" class="form-label">Casting</label> <input
								type="text" class="form-control" id="casting" name="casting"
								value="${movie.casting }" required="required">
						</div>
						<div class="mb-3">
							<label for="director" class="form-label">Director</label> <input
								type="text" class="form-control" id="director" name="director"
								value="${movie.director }" required="required">
						</div>
						<div class="mb-3">
							<label for="genre" class="form-label">Genre</label> <input
								type="text" class="form-control" id="genre" name="genre"
								value="${movie.genre }" required="required">
						</div>
						<div class="mb-3">
							<label for="releaseDate" class="form-label">Release Date</label>
							<input type="date" class="form-control" id="releaseDate"
								name="releaseDate" value="${movie.releaseDate }" required="required">
						</div>
						<div class="mb-3">
							<label for="duration_min" class="form-label">Duration</label> <input
								type="number" class="form-control" id="duration_min"
								name="duration_min" value="${movie.duration_min }" required="required">
						</div>
						<div class="mb-3">
							<label for="poster" class="form-label">Poster</label> <input
								type="text" class="form-control" id="poster" name="poster"
								value="${movie.poster }" required="required">
						</div>
						<button type="submit" class="btn btn-detail float-end">Update</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<c:import url="../common/footer.jsp" />
</body>
</html>