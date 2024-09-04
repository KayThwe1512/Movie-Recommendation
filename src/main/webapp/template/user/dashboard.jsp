<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>

<c:import url="../common/header.jsp" />
<link href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css" rel="stylesheet">
<link href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css" rel="stylesheet">
<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="../static/css/style.css" rel="stylesheet">
<link href="../static/fontawesome/css/all.min.css" rel="stylesheet">
<style type="text/css">
	body{
		background: linear-gradient(to right, #948e99, #2e1437 ) !important;
	}
</style>

</head>
<body>
	<c:import url="../common/nav.jsp" />
	<div class="container-fluid">

		<div class="row">
			<div id="sidebar" class="sidebar col-md-4">
				<ul class="nav flex-column profile-ul">
					<li class="nav-item"><c:url var="userlistlink" value="admin">
							<c:param name="mode" value="USERLIST" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${userlistlink}">User
							Information</a></li>
					<li class="nav-item"><c:url var="movielistlink" value="admin">
							<c:param name="mode" value="MOVIELIST" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${movielistlink}">Movie
							Information</a></li>
					<li class="nav-item"><c:url var="reviewlistlink" value="admin">
							<c:param name="mode" value="REVIEWLIST" />
							<c:param name="userId" value="${user.id}" />
						</c:url> <a class="nav-link profile-text" href="${reviewlistlink}">Review
							Information</a></li>
				</ul>
			</div>
			<div id="main-content" class="content  offset-md-2 col-md-9">
				<c:choose>
					<c:when test="${mode == 'USERLIST' || mode == null}">
						<div id="user-section" class="section">
							<h2>User Information</h2>
							<table id="example" class="table"
								style="width: 100%; color: white;">
								<thead>
									<tr>
										<th>ID</th>
										<th>Firstname</th>
										<th>Lastname</th>
										<th>Username</th>
										<th>Email</th>
										<th>CreatedAt</th>
										<th>Biography</th>
										<th>Gender</th>
										<th>Date Of birth</th>
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
											<td>${user.biography }</td>
											<td>${user.gender }</td>
											<td>${user.dateOfBirth }</td>
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
										<th>Biography</th>
										<th>Gender</th>
										<th>Date Of birth</th>
										<th>Action</th>
									</tr>
								</tfoot>
							</table>
						</div>
					</c:when>
					<c:when test="${mode == 'MOVIELIST'}">
						<div id="movie-section" class="section">
							<h2>Movies Information</h2>
							<div class="dashboard-card grid-margin stretch-card">
								<div class="card-body">
									<div class="table-responsive">
										<table class="table" style="width: 100%; color: white;">
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
															<td class="casting">${movie.casting }</td>
															<td>${movie.director }</td>
															<td>${movie.releaseDate }</td>
															<td>${movie.duration_min }</td>
															<td><c:url var="updatelink" value="movie">
																	<c:param name="mode" value="LOAD" />
																	<c:param name="movieId" value="${movie.id}" />
																</c:url> <a href="${updatelink}" class="btn btn-detail mx-2" style="display:flex"><i
																	class="fas fa-edit"></i> Update</a> <c:url var="deletelink"
																	value="movie">
																	<c:param name="mode" value="DELETE" />
																	<c:param name="movieId" value="${movie.id}" />
																</c:url> <a href="${deletelink}" class="btn btn-detail mx-2" style="display:flex"><i
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
					</c:when>
					<c:when test="${mode == 'REVIEWLIST'}">
						<div id="review-section" class="section">
							<h2>Review Information</h2>
							<div class="dashboard-card grid-margin stretch-card">
								<div class="card-body">
									<div class="table-responsive">
										<table class="table" style="width: 100%; color: white;">
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
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		new DataTable('#example')
	</script>
<c:import url="../common/footer.jsp" />
</body>
</html>