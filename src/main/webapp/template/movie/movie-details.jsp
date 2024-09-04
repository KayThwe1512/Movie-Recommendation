<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- head -->
<head>
<c:import url="../common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<link href="static/fontawesome/css/all.min.css" rel="stylesheet">
<style type="text/css">
body {
	background: linear-gradient(to right, #948e99, #2e1437) !important;
}

.details-card {
	border: 1px;
	border-color: rgb(106, 15, 106);
	box-shadow: 0px 4px 8px rgba(106, 15, 106, 0.5);
	/*box-shadow: 2px 2px 6px rgb(181, 26, 181);*/
	border-radius: 8px;
	overflow: hidden;
	display: flex;
	margin-bottom: 20px;
}
.details-card-body {
    flex: 2;
    padding: 20px;
}

.details-info {
	margin-left: 20px;
    margin-top: 20px;
    margin-bottom: 30px;
}
.card-text{
	color:white;
}
.horizontal-list {
	background-color:transparent;
    list-style: none;
    padding: 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: left;
}

.horizontal-list li {
	background-color:transparent;
	color:white;
    border: 1px;
	border-color: rgb(106, 15, 106);
	box-shadow: 0px 4px 8px rgba(106, 15, 106, 0.5);
    padding: 5px 10px;
    margin: 5px;
    border-radius: 4px;
}
</style>
</head>

<body>
	<c:import url="../common/nav.jsp" />

	<!-- movie details card -->
	<div class="details-container">
		<div class="details-row">
			<div class="col-10 mx-auto">
				<div class="details-card d-flex mb-3">

					<div class="details-poster">
						<!-- Movie Poster Carousel -->
						<c:import url="../common/detail-poster-carousel.jsp" />
						<!-- Movie Poster Carousel End -->

						<div class="details-info">
							<h5 class="card-title text-center">${movie.title}</h5>
							<p class="card-text">Rating: ${movie.averageRating}/10</p>
							<p class="card-text">Duration: ${movie.duration_min} min</p>
							<p class="card-text">Release date: ${movie.releaseDate}</p>
						</div>
					</div>

					<div class="details-card-body">
						<h6 class="card-text">Synopsis</h6>
						<p class="card-text">${movie.synopsis}</p>
						<hr>

						<h6 class="card-text">Genre</h6>
						<ul class="horizontal-list">
							<c:set var="genreList" value="${fn:split(movie.genre, ',')}" />
							<c:forEach var="genre" items="${genreList}">
								<li>${genre.trim()}</li>
							</c:forEach>
						</ul>

						<h6 class="card-text">Casting</h6>
						<ul class="horizontal-list">
							<c:set var="castingList" value="${fn:split(movie.casting, ',')}" />
							<c:forEach var="cast" items="${castingList}">
								<li class="list-item">${cast.trim()}</li>
							</c:forEach>
						</ul>
						<hr>

						<h6 class="card-text">Director</h6>
						<ul class="horizontal-list">
							<c:set var="directorList"
								value="${fn:split(movie.director, ',')}" />
							<c:forEach var="director" items="${directorList}">
								<li>${director.trim()}</li>
							</c:forEach>
						</ul>
						<hr>

						<c:url var="reviewlink" value="review">
							<c:param name="mode" value="REVIEW" />
							<c:param name="movieId" value="${movie.id}" />
						</c:url>

						<c:url var="signinlink" value="login">
							<c:param name="mode" value="SIGNUP" />
							<c:param name="movieId" value="${movie.id}" />
						</c:url>

						<c:choose>
							<c:when test="${not empty user}">
								<c:choose>
									<c:when test="${user.role == 'admin'}">
										<c:url var="updatelink" value="movie">
											<c:param name="mode" value="LOAD" />
											<c:param name="movieId" value="${movie.id}" />
										</c:url>
										<a href="${updatelink}" class="btn btn-detail float-end mx-2"><i
											class="fas fa-edit"></i> Update</a>

										<c:url var="deletelink" value="movie">
											<c:param name="mode" value="DELETE" />
											<c:param name="movieId" value="${movie.id}" />
										</c:url>
										<a href="${deletelink}" class="btn btn-detail float-end mx-2"><i
											class="fas fa-trash-alt"></i> Delete</a>
									</c:when>
									<c:when test="${user.role != 'admin'}">
										<c:url var="favoriteaddlink" value="movie">
											<c:param name="mode" value="ADD_FAVORITE" />
											<c:param name="movieId" value="${movie.id}" />
											<c:param name="userId" value="${user.id}" />
										</c:url>
										<a href="${favoriteaddlink}"
											class="btn btn-detail float-end mx-2"><i
											class="fas fa-heart"></i> Add Favorite</a>
										<a href="${reviewlink}" class="btn btn-detail float-end mx-2"><i
											class="fas fa-star"></i> Review</a>
									</c:when>
								</c:choose>
							</c:when>
							<c:otherwise>
								<!-- Not Logged In -->
								<a href="javascript:void(0);"
									class="btn btn-detail float-end mx-2"
									onclick="confirmSignIn('${signinlink}')"><i
									class="fas fa-heart"></i> Add Favorite</a>
								<a href="${reviewlink}" class="btn btn-detail float-end mx-2"><i
									class="fas fa-star"></i> Review</a>
							</c:otherwise>
						</c:choose>


					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="toast"></div>
	<script>
		function confirmSignIn(signinlink) {
			if (confirm("You need to sign in first to add this movie to your watch later list. Click OK to sign in.")) {
				window.location.href = signinlink;
			}
		}
	</script>
	<script>
		window.onload = function() {
			var notification = '<c:out value="${sessionScope.notification}" />';
			if (notification) {
				var toast = document.getElementById("toast");
				toast.innerHTML = notification;
				toast.className = "show";
				setTimeout(function() {
					toast.className = toast.className.replace("show", "");
				}, 1000);
	<%request.getSession().removeAttribute("notification");%>
		}
		}
	</script>

	<c:import url="../common/footer.jsp" />
</body>
</html>
