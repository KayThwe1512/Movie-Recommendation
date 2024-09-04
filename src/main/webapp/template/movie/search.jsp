<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<div class="container">
		<div id="all-movies-carousel" class="carousel slide" data-bs-ride="carousel"
			data-bs-interval="3000">
			<div class="carousel-inner">
				<c:forEach var="movie" items="${movieList}" varStatus="status">
					<c:if test="${status.index % 6 == 0}">
						<div class="carousel-item ${status.index == 0 ? 'active' : ''}">
							<div class="d-flex">
					</c:if>
					<c:url var="detailslink" value="movie">
						<c:param name="mode" value="ADD_HISTORY" />
						<c:param name="movieId" value="${movie.id}" />
						<c:param name="userId" value="${user.id}" />
					</c:url>
					<div class="card list-card mb-3 mx-2">
						<div class="position-relative">
							<img src="${fn:split(movie.poster, ',')[0].trim()}"
								class="list-card-poster d-block w-100"
								alt="${movie.title} Poster">
							<div class="image-overlay">
								<div class="overlay-content">
									<h5 class="overlay-title">${movie.title}</h5>
									<p class="overlay-title">${movie.averageRating}</p>
								</div>
							</div>
						</div>
						<div class="card-body-home">
							<h5 class="card-title-home">${movie.title}</h5>
							<p class="card-text-home">${movie.genre}</p>
							<a href="${detailslink}" class="btn btn-primary-home float-end">Watch</a>
						</div>
					</div>
					<c:if
						test="${(status.index + 1) % 6 == 0 || status.index == movieList.size() - 1}">
			</div>
		</div>
		</c:if>
		</c:forEach>
	</div>
	<button class="carousel-control-prev" type="button"
		data-bs-target="#all-movies-carousel" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button"
		data-bs-target="#all-movies-carousel" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Next</span>
	</button>
	</div>
	

</body>
<c:import url="../common/footer.jsp" />
</html>