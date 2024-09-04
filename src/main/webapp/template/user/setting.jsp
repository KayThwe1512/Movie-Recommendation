<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- head -->
<head>
<c:import url="../common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<link href="static/fontawesome/css/all.min.css" rel="stylesheet">
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> -->
<style>
body {
	background: linear-gradient(to right, #20002c, #cbb4d4 );
	font-family: Roboto Slab, Lora;
}

label {
	color: black;
}

div {
	color: black;
}

.settings-container {
	max-width: 800px;
	margin: 20px auto;
	background: linear-gradient(to right, #948e99, #2e1437) !important;
	padding: 20px;
	border-radius: 8px;
	border: 1px;
	border-color: rgb(106, 15, 106);
	box-shadow: 0px 4px 8px rgba(106, 15, 106, 0.5);
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	display: flex;
	flex-direction: column;
}

.form-wrapper {
	width: 100%;
	max-width: 700px;
	min-height: 550px;
}

.password{
	margin-top : 65px;
	margin-left: 35px;
}

.profile-picture {
	display: flex;
	align-items: center;
	margin-bottom: 20px;
}

.profile-picture img {
	border-radius: 50%;
	width: 80px;
	height: 80px;
	margin-right: 20px;
}

.upload-btn {
	background-color: #007bff;
	color: #fff;
	padding: 8px 16px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.upload-btn:hover {
	background-color: #0056b3;
}

.form-group {
	margin-bottom: 15px;
}

.form-control:disabled, .form-control[readonly] {
	background-color: #e9ecef;
	opacity: 1;
}

.unverified {
	color: #ff0000;
	font-weight: bold;
}

.save-btn {
	background-color: #007bff;
	color: #fff;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.save-btn:hover {
	background-color: #0056b3;
}

.user-account {
	margin-top: 20px;
	margin-bottom: 20px;
}
.nav-tabs{
background: linear-gradient(to right, #948e99, #2e1437) !important;
}
</style>
</head>
<body>
	<c:import url="../common/nav.jsp" />
	<div class="settings-container">
		<h3>Settings</h3>
		<ul class="nav nav-tabs" style="margin-bottom: 10px;">
			<c:url var="updateprofile" value="user">
				<c:param name="mode" value="UPDATE_PROFILE_FORM" />
				<c:param name="userId" value="${user.id}" />
			</c:url>
			<c:url var="updatepassword" value="user">
				<c:param name="mode" value="CHANGE_PASSWORD_FORM" />
				<c:param name="userId" value="${user.id}" />
			</c:url>
			<li class="nav-item"><a
				class="nav-link ${mode == 'UPDATE_PROFILE_FORM' ? 'active' : ''} active"
				href="${updateprofile }">Profile</a></li>
			<li class="nav-item"><a
				class="nav-link ${mode == 'CHANGE_PASSWORD_FORM' ? 'active' : ''}"
				href="${updatepassword }">General Settings</a></li>
		</ul>
		<div class="form-wrapper">
			<c:choose>
				<c:when test="${mode == 'UPDATE_PROFILE_FORM' || mode == null}">
					<form action="user" method="post" enctype="multipart/form-data">
						<input type="hidden" name="mode" value="UPDATE_PROFILE"> <input
							type="hidden" name="userId" value="${user.id }">
						<div class="row mb-3 user-account" id="user-account">
							<div class="col-sm-3">
								<c:choose>
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
								<span id="username-display" style="text-transform: uppercase;">
									<c:out
										value="${user.username != null ? user.username : 'Unknown User'}" />
								</span>
							</div>
							<div class="col-sm-9">
								<c:url var="uploadpiclink" value="user">
									<c:param name="mode" value="UPLOAD_PICTURE" />
									<c:param name="userId" value="${user.id}" />
								</c:url>
								<label class="upload-btn" for="profile_pic">Upload</label> <input
									hidden="" type="file" class="form-control" id="profile_pic"
									name="profile_pic">
							</div>
						</div>


						<div class="row mb-3">
							<label for="firstname" class="col-sm-3 col-form-label">First
								Name</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="firstname"
									name="firstname" value="${user.firstname }" required="required">
							</div>
						</div>
						<div class="row mb-3">
							<label for="lastname" class="col-sm-3 col-form-label">Last
								Name</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="lastname"
									name="lastname" value="${user.lastname }" required="required">
							</div>
						</div>
						<div class="row mb-3">
							<label for="username" class="col-sm-3 col-form-label">Display
								Name</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="username"
									name="username" value="${user.username }" required="required">
							</div>
						</div>
						<div class="row mb-3">
							<label for="email" class="col-sm-3 col-form-label">Email</label>
							<div class="col-sm-9">
								<input type="email" class="form-control" id="email" name="email"
									value="${user.email }" required="required">
							</div>
						</div>
						<div class="row mb-3">
							<label for="gender" class="col-sm-3 col-form-label">Gender</label>
							<div class="col-sm-9">
								<select class="form-control" id="gender" name="gender"
									required="required">
									<option value="-">-</option>
									<option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
									<option value="female"
										${user.gender == 'female' ? 'selected' : ''}>Female</option>
									<option value="other"
										${user.gender == 'other' ? 'selected' : ''}>Other</option>
								</select>

							</div>
						</div>
						<div class=" row mb-3">
							<label for="dateOfBirth" class="col-sm-3 col-form-label">Date
								of Birth</label>
							<div class="col-sm-9 d-flex">
								<input type="date" class="form-control" id="dateOfBirth"
									name="dateOfBirth" value="${user.dateOfBirth}"
									required="required">
							</div>
						</div>
						<div class="row mb-3">
							<label for="biography" class="col-sm-3 col-form-label">Biography</label>
							<div class="col-sm-9">
								<textarea class="form-control" id="biography" name="biography"
									rows="4" placeholder="Describe yourself in a sentence or two."
									required="required">${user.biography != null ? user.biography : ''}</textarea>
							</div>
						</div>
						<button type="submit" class="save-btn ">Save Changes</button>
					</form>
				</c:when>
				<c:when test="${ mode == 'CHANGE_PASSWORD_FORM'}">
					<h4 style="margin-top:100px; text-align:center; font-size:1.9rem ">Change Password </h4>
					<form action="user" method="post" class="password">
						<input type="hidden" name="mode" value="CHANGE_PASSWORD">
						<input type="hidden" name="userId" value="${user.id }">
						<div class="row mb-4">
							<label for="currentPassword" class="col-sm-3 col-form-label">Current
								Password:</label>
							<div class="col-sm-9">
								<input type="password" class="form-control"
									name="currentPassword" required="required"
									placeholder="Current Password">
							</div>
						</div>
						<div class="row mb-4">
							<label for="newPassword" class="col-sm-3 col-form-label">New
								Password:</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" name="newPassword"
									required="required" placeholder="New Password">
							</div>
						</div>
						<div class="row mb-4">
							<label for="confirmPassword" class="col-sm-3 col-form-label">Confirm
								Password:</label>
							<div class="col-sm-9">
								<input type="password" class="form-control"
									name="confirmPassword" required="required"
									placeholder="Confirm Password">
							</div>
						</div>
						<button type="submit" class="save-btn float-end mt-3">Change</button>
					</form>
					<%
					if (request.getAttribute("message") != null) {
					%>
					<p style="color: red;"><%=request.getAttribute("message")%></p>
					<%
					}
					%>
				</c:when>
			</c:choose>
		</div>
	</div>
	<div id="toast"></div>
	<script>
		window.onload = function() {
			var notification = '<c:out value="${sessionScope.notification}" />';
			if (notification) {
				var toast = document.getElementById("toast");
				toast.innerHTML = notification;
				toast.className = "show";
				setTimeout(function() {
					toast.className = toast.className.replace("show", "");
				}, 3000);
	<%request.getSession().removeAttribute("notification");%>
		}
		}
	</script>

	<c:import url="../common/footer.jsp" />
</body>
</html>
