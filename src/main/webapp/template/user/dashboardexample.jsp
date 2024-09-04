<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<c:import url="../common/header.jsp" />
<link
	href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css"
	rel="stylesheet">
<link
	href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css"
	rel="stylesheet">
<script
	src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
</head>
<body>
	<c:import url="../common/nav.jsp" />

	<table id="example" class="table table-striped" style="width: 100%">
		<thead>
			<tr>
				<th>ID</th>
				<th>Firstname</th>
				<th>Lastname</th>
				<th>Username</th>
				<th>Email</th>
				<th>CreatedAt</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${userList }">
				<tr>
					<td>${user.id }</td>
					<td>${user.firstname }</td>
					<td>${user.lastname }</td>
					<td>${user.username }</td>
					<td>${user.email }</td>
					<td>${user.createdAt }</td>
					<td><c:choose>
							<c:when test="${user.enable }">
								<c:url var="disablelink" value="admin">
									<c:param name="mode" value="DISABLE" />
									<c:param name="userId" value="${user.id }" />
								</c:url>
								<a class="btn btn-danger" href=${disablelink }>Disable</a>
							</c:when>
							<c:otherwise>
								<c:url var="enablelink" value="admin">
									<c:param name="mode" value="ENABLE" />
									<c:param name="userId" value="${user.id }" />
								</c:url>

								<a class="btn btn-success" href="${enablelink }">Enable</a>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>

		</tbody>
		<tfoot>
			<tr>
				<th>ID</th>
				<th>FirstName</th>
				<th>LastName</th>
				<th>Username</th>
				<th>Email</th>
				<th>CreatedAt</th>
				<th>Action</th>
			</tr>
		</tfoot>
	</table>


	<div class="content-wrapper">
		<div class="row">
			<div class="col-lg-6 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Review Table</h4>
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th>Username</th>
										<th>Movie Title</th>
										<th>Created Date</th>
										<th>Rating</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty reviewList }">
										<c:forEach var="reviews" items="${reviewList }">
											<c:forEach var="movie" items="${movieReviewDetailsList}">
												<c:if test="${movie.id == reviews.movieId}">
													<tr>
														<td>${reviews.username }</td>
														<td>${movie.title }</td>
														<td>${reviews.createdAt }</td>
														<td>${reviews.rating }</td>
													</tr>
												</c:if>
											</c:forEach>
										</c:forEach>
									</c:if>

									<c:if test="${empty reviewList}">
										<p>No review is found.</p>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-6 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Movie Table</h4>
						<div class="table-responsive">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>Title</th>
										<th>Genre</th>
										<th>Casting</th>
										<th>Director</th>
										<th>Issue Date</th>
										<th>Duration</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
								<c:if test="${not empty movieList }">
									<c:forEach var="movie" items="${movieList }">
										<tr>
											<td>${movie.title }</td>
											<td>${movie.genre }</td>
											<td>${movie.casting }</td>
											<td>${movie.director }</td>
											<td>${movie.releaseDate }</td>
											<td>${movie.duration_min }</td>
											<td><c:url var="updatelink" value="movie">
											<c:param name="mode" value="LOAD" />
											<c:param name="movieId" value="${movie.id}" />
										</c:url>
										<a href="${updatelink}" class="btn btn-detail  mx-2"><i
											class="fas fa-edit"></i> Update</a>

										<c:url var="deletelink" value="movie">
											<c:param name="mode" value="DELETE" />
											<c:param name="movieId" value="${movie.id}" />
										</c:url>
										<a href="${deletelink}" class="btn btn-detail mx-2"><i
											class="fas fa-trash-alt"></i> Delete</a></td>

										</tr>
									</c:forEach>
									</c:if>
									<c:if test="${empty movieList}">
										<p>No movie is found.</p>
									</c:if>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Striped Table</h4>
						<p class="card-description">
							Add class
							<code>.table-striped</code>
						</p>
						<div class="table-responsive">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>User</th>
										<th>First name</th>
										<th>Progress</th>
										<th>Amount</th>
										<th>Deadline</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="py-1"><img src="../../images/faces/face2.jpg"
											alt="image"></td>
										<td>Messsy Adam</td>
										<td>
											<div class="progress">
												<div class="progress-bar bg-danger" role="progressbar"
													style="width: 75%" aria-valuenow="75" aria-valuemin="0"
													aria-valuemax="100"></div>
											</div>
										</td>
										<td>$245.30</td>
										<td>July 1, 2015</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- content-wrapper ends -->
	<!-- partial:../../partials/_footer.html -->


	<script type="text/javascript">
		new DataTable('#example')
	</script>

	<c:import url="../common/footer.jsp" />
</body>
</html>

</body>
</html>