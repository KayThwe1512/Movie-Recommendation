<%@taglib uri="jakarta.tags.core" prefix="c"%>
<style type="text/css">
	body{
		background: linear-gradient(to right, #948e99, #2e1437 ) !important;
	}
</style>
<div class="row">
	<div class="col-lg-8 col-md-8 col-xs-12 m-b">
		<div class="front-section">
			<div class="box-header">
				<div class="box-tool">
					<a class="more" href="${reviewlink }">more</a>
				</div>
				<h2 class="header m-b-0">Latest Reviews</h2>
			</div>
			<div class="grid-reviews grid-slider">
				<div id="slide-reviews" class="swiper-container swiper-container-initialized swiper-container-horizontal">
					<div class="swiper-wrapper" style="transform:translate3d(0px, 0px, 0px);">
						 <c:forEach var="review" items="${latestReviews}">
                            <div class="swiper-slide">
                                <div class="review-item">
                                    <img src="${review.moviePoster}" alt="${review.movieName}" class="img-responsive">
                                    <h3>${review.movieName}</h3>
                                    <p>${review.reviewText}</p>
                                </div>
                            </div>
                        </c:forEach>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<c:import url="../common/footer.jsp" />