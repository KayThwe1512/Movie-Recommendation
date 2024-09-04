<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<!-- head -->
<head>
<c:import url="common/header.jsp" />
<link href="static/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	body{
		background: linear-gradient(to right, #20002c, #cbb4d4 );
	}
</style>
</head>

<body>
	<!-- Nav Bar Start -->
	<c:import url="common/nav.jsp" />
	<!-- Nav Bar End -->

	<!-- Movie List Start -->
	<c:import url="common/movie-list.jsp"/>
	<!-- Movie List End -->

	<!-- footer -->
	<c:import url="common/footer.jsp" />
	
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</body>
</html>