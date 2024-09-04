<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="static/fontawesome/css/all.min.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<nav class="navbar navbar-dark bg-dark navbar-expand-lg ">
	<div class="container-fluid">
		<a class="navbar-brand" href="movie">Movie Recommendation App</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-lg-2">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="movie"><i class="fas fa-home"></i><span>Home</span></a></li>
				<c:if test="${user == null}">
					<li class="nav-item"><c:url var="signuplink" value="user">
							<c:param name="mode" value="SIGNUP_FORM" />
						</c:url> <a class="nav-link" href="${signuplink }"><i
							class="fas fa-user-plus"></i> <span>Sign Up</span></a></li>
					<li class="nav-item"><c:url var="signinlink" value="login">
							<c:param name="mode" value="LOGIN_FORM" />
						</c:url> <a class="nav-link" href="${ signinlink}"><i
							class="fas fa-sign-in-alt"></i> <span>Login</span></a></li>
				</c:if>
				<c:if test="${user != null}">
					<c:choose>
						<c:when test="${user.role == 'user'}">
							<li class="nav-item"><c:url var="profilelink" value="user">
									<c:param name="mode" value="PROFILE" />
									<c:param name="userId" value="${user.id}" />
								</c:url> <a class="nav-link" href="${profilelink}" id="profile-picture">
									<i class="fas fa-user"></i><span>My Account</span>
							</a></li>
							<li class="nav-item"><c:url var="settinglink" value="user">
									<c:param name="mode" value="SETTING" />
									<c:param name="userId" value="${user.id}" />
								</c:url> <a class="nav-link" href="${settinglink}"><i
									class="fas fa-gear"></i><span>Setting</span></a></li>
						</c:when>
					</c:choose>
				</c:if>
			</ul>
			<form class="d-flex">
				<input type="hidden" name="mode" value="SEARCH"> <input
					class="form-control me-2" type="search" name="query"
					placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-success" type="submit">
					<i class="fas fa-search"></i> <span class="btn-text">Search</span>
				</button>
			</form>
			<c:if test="${user.role == 'admin'}">
				<ul class="navbar-nav  float-end">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> <c:choose>
								<c:when
									test="${user.profile_pic != null && !user.profile_pic.isEmpty()}">
									<img alt="Profile Picture"
										src="template/image/${user.profile_pic}"
										class="img-fluid rounded-circle"
										style="width: 40px; height: 40px;">
								</c:when>
								<c:otherwise>
									<img alt="Profile Picture"
										src="https://ui-avatars.com/api/?name=<c:out value='${user.username != null && !user.username.isEmpty() ? user.username.substring(0, 1) : "U"}'/>&length=1&background=007bff&color=fff&size=40"
										class="img-fluid rounded-circle"
										style="width: 40px; height: 40px;">
								</c:otherwise>
							</c:choose>
					</a>
						<ul class="dropdown-menu dropdown-menu-end"
							aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item" href="movie?mode=MOVIE_FORM"><i
									class="fas fa-plus"></i><span>Add Movie</span></a></li>
							<li><a class="dropdown-item" href="admin"><i
									class="fas fa-tachometer-alt"></i><span>Admin Dashboard</span></a></li>
							<li><a class="dropdown-item" href="login?mode=LOGOUT">Log
									out</a></li>
						</ul></li>
				</ul>
			</c:if>
			<c:if test="${user != null and user.role == 'user' }">
				<ul class="navbar-nav  float-end">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> <c:choose>
								<c:when
									test="${user.profile_pic != null && !user.profile_pic.isEmpty()}">
									<img alt="Profile Picture"
										src="template/image/${user.profile_pic}"
										class="img-fluid rounded-circle"
										style="width: 40px; height: 40px;">
								</c:when>
								<c:otherwise>
									<img alt="Profile Picture"
										src="https://ui-avatars.com/api/?name=<c:out value='${user.username != null && !user.username.isEmpty() ? user.username.substring(0, 1) : "U"}'/>&length=1&background=007bff&color=fff&size=40"
										class="img-fluid rounded-circle"
										style="width: 40px; height: 40px;">
								</c:otherwise>
							</c:choose>
					</a>
						<ul class="dropdown-menu dropdown-menu-end"
							aria-labelledby="navbarDropdownMenuLink">
							<c:url var="watchlaterlink" value="user">
									<c:param name="mode" value="WATCH_LATER" />
									<c:param name="userId" value="${user.id}" />
								</c:url> 
								<c:url var="favoritelink" value="user">
									<c:param name="mode" value="FAVORITE" />
									<c:param name="userId" value="${user.id}" />
								</c:url>
							<li><a class="dropdown-item" href="${profilelink}">Profile</a></li>
							<li><a class="dropdown-item" href="${watchlaterlink }">My Watchlist</a></li>
							<li><a class="dropdown-item" href="${favoritelink }">Favorite</a></li>
							<li><a class="dropdown-item" href="${settinglink}">Settings</a></li>
							<li><a class="dropdown-item" href="login?mode=LOGOUT">Log
									out</a></li>
							<li><a class="dropdown-item" href="user?mode=DELETE_USER">Delete Acc</a></li>
						</ul></li>
				</ul>
			</c:if>
			
			
		</div>
	</div>
</nav>
<script>
	var username = "${user.username}";
	var initialLetter = username.charAt(0).toUpperCase();
	document.getElementById('initial-letter').innerText = initialLetter;
</script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

