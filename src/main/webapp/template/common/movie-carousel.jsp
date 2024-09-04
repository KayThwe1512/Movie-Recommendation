<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@taglib uri="jakarta.tags.functions" prefix="fn"%>
<div class="col-xxs-6 col-xs-6 col-sm-6 col-md-3 col-lg-3 col-xl-2 col-xxl-2">
	<div class=" card list-card mb-3">
		<div id="imageCarousel_${movie.id}" class="carousel slide"
			data-bs-ride="carousel" data-bs-intrerval="1000">
			<div class="carousel-indicators">
				<c:set var="posterList" value="${fn:split(movie.poster, ',')}" />
				<c:forEach var="poster" items="${posterList}" varStatus="status">
					<button type="button" data-bs-target="#imageCarousel_${movie.id}"
						data-bs-slide-to="${status.index}"
						class="${status.index == 0 ? 'active' : ''}"
						aria-current="${status.index == 0 ? 'true' : 'false'}"
						aria-label="Slide ${status.index + 1}"></button>
				</c:forEach>
			</div>
			<div class="carousel-inner">
				<c:set var="posterList" value="${fn:split(movie.poster, ',')}" />
				<c:forEach var="poster" items="${posterList}" varStatus="status">
					<div class="carousel-item${status.index == 0 ? ' active' : ''}">
						<img src="${poster.trim()}" class="list-card-poster d-block w-100"
							alt="Poster ${status.index + 1}">
						<div class="image-overlay">
							<div class="overlay-content">
								<h5 class="overlay-title">${movie.title}</h5>
								<p class="overlay-title">${movie.averageRating }</p>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
			<button class="carousel-control-prev" type="button"
				data-bs-target="#imageCarousel" data-bs-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#imageCarousel" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>
		<div class="card-body-home">
			<h5 class="card-title-home">${movie.title }</h5>
			<p class="card-text-home">${movie.genre }</p>
			<a href="${detailslink }" class="btn btn-primary-home float-end">Watch</a>
		</div>
	</div>
</div>