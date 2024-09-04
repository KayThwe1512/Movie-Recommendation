<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@taglib uri="jakarta.tags.functions" prefix="fn"%>
<div class="poster-container">
<div id="imageCarousel" class="carousel-slide" data-bs-ride="carousel" data-bs-interval="2000">
	<div class="carousel-indicators">
		<button type="button" data-bs-target="#imageCarousel"
			data-bs-slide-to="0" class="active" aria-current="true"
			aria-label="Slide 1"></button>
		<button type="button" data-bs-target="#imageCarousel"
			data-bs-slide-to="1" aria-label="Slide 2"></button>
		<button type="button" data-bs-target="#imageCarousel"
			data-bs-slide-to="2" aria-label="Slide 3"></button>
	</div>
	<div class="carousel-inner">
		<c:set var="posterList" value="${fn:split(movie.poster, ',')}" />
		<c:forEach var="poster" items="${posterList}" varStatus="status">
			<div class="carousel-item${status.index == 0 ? ' active' : ''}">
				<img src="${poster.trim()}"
					class="details-card-poster d-block"
					alt="Poster ${status.index + 1}">
			</div>
		</c:forEach>

	</div>
	<button class="carousel-control-prev" type="button"
		data-bs-target="#imageCarousel" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button"
		data-bs-target="#imageCarousel" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="visually-hidden">Next</span>
	</button>
</div>
</div>