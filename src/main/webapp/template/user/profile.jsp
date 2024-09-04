<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>User Profile</title>
<c:import url="../common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<style type="text/css">
	body{
		background: linear-gradient(to right, #948e99, #2e1437 ) !important;
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
.content {
	margin-top: 5px;
	transition: margin-left 0.3s;
}
.img-profile {
    border:2px;
    border-color:white;
    padding:20px;
}

</style>
</head>
<body>
	<!-- nav bar -->
	<c:import url="../common/nav.jsp" />
	<!-- nav bar -->

	<div class="container-fluid">

		<div class="row">
			<div id="sidebar" class="sidebar col-md-4 mt-5">
				<ul class="nav flex-column profile-ul">
					<li class="nav-item"><c:url var="profilelink" value="user">
							<c:param name="mode" value="PROFILE" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${profilelink}">My
							Account</a></li>
					<li class="nav-item"><c:url var="favoritelink" value="user">
							<c:param name="mode" value="FAVORITE" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${favoritelink}">Favorite</a>
					</li>
					<li class="nav-item"><c:url var="historylink" value="user">
							<c:param name="mode" value="HISTORY" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${historylink}">History</a>
					</li>
					<li class="nav-item"><c:url var="watchlaterlink" value="user">
							<c:param name="mode" value="WATCH_LATER" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${watchlaterlink}">Watch
							Later</a></li>
				</ul>
			</div>

			<div id="main-content" class="content  offset-md-2 col-md-10 mt-5">
				<c:choose>
					<c:when test="${mode == 'PROFILE' || mode == null}">
						<div id="account-section" class="section">
						<div class="row">
							<div class="col-md-3">
								 <c:choose>
								<c:when
									test="${user.profile_pic != null && !user.profile_pic.isEmpty()}">
									<img alt="Profile Picture"
										src="template/image/${user.profile_pic}"
										class="img-profile "
										style="width: 200px; height: 150px;">
								</c:when>
								<c:otherwise>
									<img alt="Profile Picture"
										src="https://ui-avatars.com/api/?name=<c:out value='${user.username != null && !user.username.isEmpty() ? user.username.substring(0, 1) : "U"}'/>&length=1&background=007bff&color=fff&size=40"
										class="img-profile "
										style="width: 200px; height: 150px;">
								</c:otherwise>
							</c:choose>
							</div>
							<div class="col-md-6">
							<div class="profile-header">
								<h1>Welcome, ${user.username}</h1>
								<p>Manage your profile and preferences here.</p>
							</div>
							<div class="profile-info">
								<h2>Profile Details</h2>
								<p>
									<strong>Username:</strong> ${user.username}
								</p>
								<p>
									<strong>Email:</strong> ${user.email}
								</p>
								<p>
									<strong>Joined:</strong> ${user.createdAt}
								</p>
							</div>
							</div>
						</div>
						</div>
					</c:when>
					<c:when test="${mode == 'FAVORITE'}">
						<div id="favorite-section" class="section">
							<h2>Favorite Movies</h2>
							<div class="movie-list">
								<c:if test="${not empty favoriteList}">
									<c:forEach var="favorite" items="${favoriteList}">
										<c:forEach var="movie" items="${movieDetailsList}">
											<c:if test="${movie.id == favorite.movieId}">
												<div class="card profile-card mb-3"
													style="max-width: 540px;">
													<div class="row g-0">
														<div class="col-md-4">
															<a href="${detailslink}"> <img
																src="${fn:split(movie.poster, ',')[0].trim()}"
																class="favorite-poster w-100 h-100" alt="${movie.title} Poster">
															</a>
														</div>
														<div class="col-md-8">
															<div class="profile-card-body">
																<h5 class="card-title">${movie.title }</h5>
																<h6 class="card-text">Genre</h6>
																<ul class="horizontal-list">
																	<c:set var="genreList"
																		value="${fn:split(movie.genre, ',')}" />
																	<c:forEach var="genre" items="${genreList}">
																		<li>${genre.trim()}</li>
																	</c:forEach>
																</ul>
																<p class="card-text">
																	<small class="text-body-secondary">Last updated
																		3 mins ago</small>
																</p>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:forEach>
								</c:if>
								<c:if test="${empty favoriteList}">
									<p>No favorite movies found.</p>
								</c:if>
							</div>
						</div>
					</c:when>
					<c:when test="${mode == 'HISTORY'}">
						<div id="history-section" class="section">
							<h2>Your Movie History</h2>
							<div class="movie-list">
								<c:if test="${not empty histories}">
									<c:forEach var="history" items="${histories}">
										<c:forEach var="movie" items="${movieDetailsList}">
											<c:if test="${movie.id == history.movieId}">
												<div class="card profile-card mb-3"
													style="max-width: 540px;">
													<div class="row g-0">
														<div class="col-md-4">
															<a href="${detailslink}"> <img
																src="${fn:split(movie.poster, ',')[0].trim()}"
																class="favorite-poster w-100 h-100" alt="${movie.title} Poster">
															</a>
														</div>
														<div class="col-md-8">
															<div class="profile-card-body">
																<h5 class="card-title">${movie.title }</h5>
																<h6 class="card-text">Genre</h6>
																<ul class="horizontal-list">
																	<c:set var="genreList"
																		value="${fn:split(movie.genre, ',')}" />
																	<c:forEach var="genre" items="${genreList}">
																		<li>${genre.trim()}</li>
																	</c:forEach>
																</ul>
																<p class="card-text">
																	<small class="text-body-secondary">${history.statusTime }</small>
																</p>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:forEach>
								</c:if>
								<c:if test="${empty histories}">
									<p>No history is found.</p>
								</c:if>
							</div>
						</div>
					</c:when>
					<c:when test="${mode == 'WATCH_LATER'}">
						<div id="watchlater-section" class="section">
							<h2>Watch Later</h2>
							<div class="movie-list">
								<c:if test="${not empty watchlaterList}">
									<c:forEach var="watchlater" items="${watchlaterList}">
										<c:forEach var="movie" items="${movieDetailsList}">
											<c:if test="${movie.id == watchlater.movieId}">
												<div class="card profile-card mb-3"
													style="max-width: 540px;">
													<div class="row g-0">
														<div class="col-md-4">
															<a href="${detailslink}"> <img
																src="${fn:split(movie.poster, ',')[0].trim()}"
																class="favorite-poster w-100 h-100" alt="${movie.title} Poster">
															</a>
														</div>
														<div class="col-md-8">
															<div class="profile-card-body">
																<h5 class="card-title">${movie.title }</h5>
																<h6 class="card-text">Genre</h6>
																<ul class="horizontal-list">
																	<c:set var="genreList"
																		value="${fn:split(movie.genre, ',')}" />
																	<c:forEach var="genre" items="${genreList}">
																		<li>${genre.trim()}</li>
																	</c:forEach>
																</ul>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:forEach>
								</c:if>
								<c:if test="${empty watchlaterList}">
									<p>No watch later list is found.</p>
								</c:if>
							</div>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>
	<c:import url="../common/footer.jsp" />
</body>
</html>
