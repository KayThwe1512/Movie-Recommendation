<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@taglib uri="jakarta.tags.functions" prefix="fn"%>
<style>
body {
	background: linear-gradient(to right, #948e99, #2e1437) !important;
}

.swiper {
	width: 950px;
}

.swiper-pagination {
	position: absolute;
	top: 450px;
}

.swiper-pagination-bullet {
	height: 7px;
	width: 26px;
	border-radius: 25px;
	background: white;
}

.swiper-button-next, .swiper-button-prev {
	opacity: 0.7;
	color: white;
	transition: all 0.3s ease;
}

.swiper-button-next:hover, .swiper-button-prev:hover {
	opacity: 1;
	color: white;
}
</style>
<section>
	<div class="swiper mySwiper container">
		<div class="swiper-wrapper content">
			<c:forEach var="movie" items="${movieList}">
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
			</c:forEach>
		</div>
	</div>
	<div class="swiper-button-next"></div>
	<div class="swiper-button-prev"></div>
	<div class="swiper-pagination"></div>
</section>
<script type="text/javascript">
	var swiper = new Swiper(".mySwiper", {
		slidesPerView : 3,
		spaceBetween : 30,
		slidesPerGroup : 3,
		loop : true,
		loopFillGroupWithBlank : true,
		pagination : {
			el : ".swiper-pagination",
			clickable : true,
		},
		navigation : {
			nextEl : ".swiper-button-next",
			prevEl : ".swiper-button-prev",
		},
	});
</script>
<c:import url="../common/footer.jsp" />
