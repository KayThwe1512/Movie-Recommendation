<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="../common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<style type="text/css">
body {
	background: linear-gradient(to right, #948e99, #2e1437) !important;
}

.review-card {
	background-color: transparent;
	border: 2px;
	box-shadow: 0px 4px 8px rgba(106, 15, 106, 0.5);
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 25px;
}

.review-card-body {
	display: flex;
	flex-direction: column;
}

.btn-details {
	color: white;
	text-decoration: none;
}

.btn-details:hover {
	background-color: transparent;
	color: rgb(106, 15, 106);
	text-decoration: none;
}
</style>
</head>

<body>
	<!-- Nav Bar Start -->
	<c:import url="../common/nav.jsp" />

	<section class="review-page">
		<div class="container w-80 mt-4">
			<h1 class="page-title mb-0 ms-5">Movie Reviews</h1>

			<c:choose>
				<c:when test="${not empty reviewList}">
					<div class="review-list">
						<c:url var="detailslink" value="movie">
							<c:param name="mode" value="SINGLE" />
							<c:param name="movieId" value="${movieId}" />
						</c:url>
						<c:url var="addreviewlink" value="review">
							<c:param name="mode" value="REVIEW_FORM" />
							<c:param name="movieId" value="${movieId}" />
						</c:url>
						<a class="btn-details ms-5" href="${detailslink}">Movie :
							${movie.title}</a> <a class="btn-details " href="${addreviewlink}">Review
							this title</a>
						<c:forEach var="reviews" items="${reviewList}">
							<div class="review-card mb-3 col-md-8 ms-5 mt-4 mb-5">
								<div class="review-card-body">
									
										<div class="rating-user me-0">
											<span class="star" style="color: #f5b301; font-size: 1.3rem">★
												${reviews.rating}/10</span>
										</div>
										<h2>${reviews.title}</h2>
										<div class="align-items-start" style="margin:0">
<%-- 											<c:choose> --%>
<%-- 												<c:when --%>
<%-- 													test="${user.profile_pic != null && !user.profile_pic.isEmpty()}"> --%>
<!-- 													<img alt="Profile Picture" -->
<%-- 														src="template/image/${user.profile_pic}" --%>
<!-- 														class="img-fluid rounded-circle" -->
<!-- 														style="width: 40px; height: 40px;"> -->
<%-- 												</c:when> --%>
<%-- 												<c:otherwise> --%>
<!-- 													<img alt="Profile Picture" -->
<%-- 														src="https://ui-avatars.com/api/?name=<c:out value='${user.username != null && !user.username.isEmpty() ? user.username.substring(0, 1) : "U"}'/>&length=1&background=007bff&color=fff&size=40" --%>
<!-- 														class="img-fluid rounded-circle" -->
<!-- 														style="width: 40px; height: 40px;"> -->
<%-- 												</c:otherwise> --%>
<%-- 											</c:choose> --%>
											<span>${reviews.username}</span> <span>${reviews.createdAt}</span>
										</div>
									<p>${reviews.reviewtext}</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:when>
				<c:otherwise>
				<c:url var="detailslink" value="movie">
							<c:param name="mode" value="SINGLE" />
							<c:param name="movieId" value="${movie.id}" />
						</c:url>
						<c:url var="addreviewlink" value="review">
							<c:param name="mode" value="REVIEW_FORM" />
							<c:param name="movieId" value="${movie.id}" />
						</c:url>
				<a class="btn-details ms-5" href="${detailslink}">Movie :
							${movie.title}</a> <a class="btn-details " href="${addreviewlink}">Review
							this title</a>
					<div class="review-card mb-3 col-md-8">
						<i class="fa-solid fa-box-open"
							style="font-size: 150px; justify-content: center"></i>
						<div class="card-body">
							<p class="card-title">Any Review does not exist</p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</section>

	<c:import url="../common/footer.jsp" />

</body>
</html>
