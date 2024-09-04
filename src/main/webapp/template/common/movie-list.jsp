<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@taglib uri="jakarta.tags.functions" prefix="fn"%>

<style>
.home-poster{
	width: 90%;
	min-height: 500px;
	max-height: 500px;
	object-fit:contain;
	transition: opacity 0.3s ease;
}
</style>

<link rel="stylesheet" href="../static/css/style.css">
<c:url var="signinlink" value="login">
	<c:param name="mode" value="SIGNUP" />
	<c:param name="movieId" value="${movie.id}" />
</c:url>
<div class="section">
	<div id="home-carousel" class="carousel slide w-100" data-bs-ride="carousel" data-bs-interval="30000">
		<div class="carousel-inner">
			<c:forEach var="movie" items="${movieList}" varStatus="status">
				<div class="carousel-item ${status.index == 0 ? 'active' : ''}">
					
					<div class="row g-0 align-items-center">
						<div class="col-md-5 d-flex justify-content-end align-items-end me-40">
							<c:url var="detailslink" value="movie">
								<c:param name="mode" value="ADD_HISTORY" />
								<c:param name="movieId" value="${movie.id}" />
								<c:param name="userId" value="${user.id}" />
							</c:url>
							<a href="${detailslink}">
								<img src="${fn:split(movie.poster, ',')[0].trim()}" class="home-poster " alt="${movie.title} Poster">
							</a>
						</div>
						<div class="col-md-5 col-offset-2 overflow-hidden">
							<div class="d-flex flex-column justify-content-center align-items-center text-white">
								<h5>${movie.title}</h5>
								<div class="rating-user" style="font-size:1rem">
									<p>${movie.averageRating} <span class="star" style="color : #f5b301; font-size:1.2rem">★  </span></p>
											
								</div>
								<p>Genre: ${movie.genre}</p>
								<c:choose>
									<c:when test="${not empty user}">
										<c:choose>
											<c:when test="${user.role == 'user' }">
												<div class="col-2 d-flex justify-content-end">
													<c:url var="watchlateraddlink" value="movie">
														<c:param name="mode" value="ADD_WATCH_LATER" />
														<c:param name="userId" value="${user.id}" />
														<c:param name="movieId" value="${movie.id}" />
													</c:url>
													<a href="${watchlateraddlink}" class="btn watch-later-btn">
														<i class="fa-regular fa-bookmark"></i>
													</a>
												</div>
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" class="btn watch-later-btn "
											onclick="confirmSignIn('${signinlink}')"> <i
											class="fa-regular fa-bookmark" /></i>
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
<%-- 					<c:url var="detailslink" value="movie"> --%>
<%-- 						<c:param name="mode" value="ADD_HISTORY" /> --%>
<%-- 						<c:param name="movieId" value="${movie.id}" /> --%>
<%-- 						<c:param name="userId" value="${user.id}" /> --%>
<%-- 					</c:url> --%>
<%-- 					<a href="${detailslink}"> <img --%>
<%-- 						src="${fn:split(movie.poster, ',')[0].trim()}" class="home-poster" --%>
<%-- 						alt="${movie.title} Poster"> --%>
<!-- 					</a> -->
<!-- 					<div class="carousel-caption d-flex flex-column justify-content-center text-white"> -->
<%-- 						<h5>${movie.title}</h5> --%>
<%-- 						<p>Rating: ${movie.averageRating}</p> --%>
<%-- 						<p>Genre: ${movie.genre}</p> --%>
<%-- 						<c:url var="watchlateraddlink" value="movie"> --%>
<%-- 							<c:param name="mode" value="ADD_WATCH_LATER" /> --%>
<%-- 							<c:param name="userId" value="${user.id}" /> --%>
<%-- 							<c:param name="movieId" value="${movie.id}" /> --%>
<%-- 						</c:url> --%>
<%-- 						<a href="${watchlateraddlink}" class="btn watch-later-btn"> <i --%>
<!-- 							class="fa-regular fa-bookmark"></i> -->
<!-- 						</a> -->
<!-- 					</div> -->
				</div>
			</c:forEach>
		</div>
		<a class="carousel-control-prev" href="#home-carousel" role="button"
			data-bs-slide="prev"> <span class="carousel-control-prev-icon"
			aria-hidden="true"></span> <span class="visually-hidden">Previous</span>
		</a> <a class="carousel-control-next" href="#home-carousel" role="button"
			data-bs-slide="next"> <span class="carousel-control-next-icon"
			aria-hidden="true"></span> <span class="visually-hidden">Next</span>
		</a>
	</div>
</div>
<div class="section">
	<div class="carousel-container">
		<h2 >All Movies</h2>
		<div id="all-movies-carousel" class="carousel slide"
			data-bs-ride="carousel" data-bs-interval="3000">
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
									<div class="rating-user">
										<span class="star overlay-title"
											style="color: #f5b301; font-size: 1rem">
											 <p>${movie.averageRating}★</p> </span>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body-home">
							<div class="row">
								<div class="col-10">
									<h5 class="card-title-home">${movie.title}</h5>
								</div>
								<c:choose>
									<c:when test="${not empty user}">
										<c:choose>
											<c:when test="${user.role == 'user' }">
												<div class="col-2 d-flex justify-content-end">

													<c:url var="watchlateraddlink" value="movie">
														<c:param name="mode" value="ADD_WATCH_LATER" />
														<c:param name="userId" value="${user.id}" />
														<c:param name="movieId" value="${movie.id}" />
													</c:url>
													<a href="${watchlateraddlink}"
														class="btn watch-later-btn"> <i
														class="fa-regular fa-bookmark"></i> 
													</a>
												</div>
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" class="btn watch-later-btn "
											onclick="confirmSignIn('${signinlink}')"> <i
											class="fa-regular fa-bookmark" /></i>
										</a>
									</c:otherwise>
								</c:choose>

							</div>
							<div class="row">
								<div class="col-12">
									<p class="card-text-home">${movie.genre}</p>
								</div>
							</div>
							<a href="${detailslink}"
								class="btn btn-primary-home float-end mt-auto">Watch</a>
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

<div class="section">
	<div class="carousel-container">
		<h2>Trending Movies This week</h2>
		<div id="trending-movies-carousel" class="carousel slide"
			data-bs-ride="carousel" data-bs-interval="3000">
			<div class="carousel-inner">
				<c:forEach var="movie" items="${trendingMovies}" varStatus="status">
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
									<div class="rating-user">
										<span class="star overlay-title"
											style="color: #f5b301; font-size: 1rem">
											 <p>${movie.averageRating}★</p> </span>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body-home">
							<div class="row">
								<div class="col-10">
									<h5 class="card-title-home">${movie.title}</h5>
								</div>
								<c:choose>
									<c:when test="${not empty user}">
										<c:choose>
											<c:when test="${user.role == 'user' }">
												<div class="col-2 d-flex justify-content-end">

													<c:url var="watchlateraddlink" value="movie">
														<c:param name="mode" value="ADD_WATCH_LATER" />
														<c:param name="userId" value="${user.id}" />
														<c:param name="movieId" value="${movie.id}" />
													</c:url>
													<a href="${watchlateraddlink}"
														class="btn watch-later-btn"> <i
														class="fa-regular fa-bookmark"></i> 
													</a>
												</div>
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" class="btn watch-later-btn "
											onclick="confirmSignIn('${signinlink}')"> <i
											class="fa-regular fa-bookmark" /></i>
										</a>
									</c:otherwise>
								</c:choose>

							</div>
							<div class="row">
								<div class="col-12">
									<p class="card-text-home">${movie.genre}</p>
								</div>
							</div>
							<a href="${detailslink}"
								class="btn btn-primary-home float-end mt-auto">Watch</a>
						</div>
					</div>
					<c:if
						test="${(status.index + 1) % 6 == 0 || status.index == trendingMovies.size() - 1}">
			</div>
		</div>
		</c:if>
		</c:forEach>
	</div>
	<button class="carousel-control-prev" type="button"
		data-bs-target="#trending-movies-carousel" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button"
		data-bs-target="#trending-movies-carousel" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Next</span>
	</button>
</div>

<div class="section">
	<div class="carousel-container">
		<h2>Editor's Choice</h2>
		<div id="editors-choice-carousel" class="carousel slide"
			data-bs-ride="carousel" data-bs-interval="3000">
			<div class="carousel-inner">
				<c:forEach var="movie" items="${editorChoiceMovies}"
					varStatus="status">
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
									<div class="rating-user">
										<span class="star overlay-title"
											style="color: #f5b301; font-size: 1rem">
											 <p>${movie.averageRating}★</p> </span>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body-home">
							<div class="row">
								<div class="col-10">
									<h5 class="card-title-home">${movie.title}</h5>
								</div>
								<c:choose>
									<c:when test="${not empty user}">
										<c:choose>
											<c:when test="${user.role == 'user' }">
												<div class="col-2 d-flex justify-content-end">

													<c:url var="watchlateraddlink" value="movie">
														<c:param name="mode" value="ADD_WATCH_LATER" />
														<c:param name="userId" value="${user.id}" />
														<c:param name="movieId" value="${movie.id}" />
													</c:url>
													<a href="${watchlateraddlink}"
														class="btn watch-later-btn"> <i
														class="fa-regular fa-bookmark"></i> 
													</a>
												</div>
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" class="btn watch-later-btn "
											onclick="confirmSignIn('${signinlink}')"> <i
											class="fa-regular fa-bookmark" /></i>
										</a>
									</c:otherwise>
								</c:choose>

							</div>
							<div class="row">
								<div class="col-12">
									<p class="card-text-home">${movie.genre}</p>
								</div>
							</div>
							<a href="${detailslink}"
								class="btn btn-primary-home float-end mt-auto">Watch</a>
						</div>
					</div>
					<c:if
						test="${(status.index + 1) % 6 == 0 || status.index == editorChoiceMovies.size() - 1}">
			</div>
		</div>
		</c:if>
		</c:forEach>
	</div>
	<button class="carousel-control-prev" type="button"
		data-bs-target="#editors-choice-carousel" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button"
		data-bs-target="#editors-choice-carousel" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Next</span>
	</button>
</div>
<div id="toast"></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

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