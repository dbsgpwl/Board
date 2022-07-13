<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>

<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="../resources/css/common.css">

</head>
<body>
	<div class="contanier-md" id="wrapper" style="width:80%; margin:100px auto;">
		<!-- TopMenu -->
		<%@include file="/resources/includes/topmenu.jsp" %>
		<!-- Nav -->
		<%@include file="/resources/includes/nav.jsp" %>
		<!-- MainContents -->
		<div id="maincontents" style="margin-top:15px;">
			<!-- PagePath -->
			<div>
				<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
				  <ol class="breadcrumb">
				    <li class="breadcrumb-item"><a href="#">Home</a></li>
				    <li class="breadcrumb-item"><a href="#">Board</a></li>
				    <li class="breadcrumb-item active" aria-current="page">Post</li>
				  </ol>
				</nav>
			</div>
			<h1>글쓰기</h1>
			<form action="/Board/post.do" method="post">
				<input name="title" class="form-control mb-3 w-75"  placeholder="글제목"/>
				<textarea name="content" class="form-control mb-3 w-75" style="height:500px;"/></textarea>
				<input type="password" name="pwd" class="form-control mb-3 w-75"  placeholder="Enter Password"/>
				<input type="file" name="files" class="form-control mb-3 w-75" multiple/>
				<input type="submit" value="글쓰기" class="btn btn-dark" />
				<a href="#" class="btn btn-dark" >처음으로</a>
				<input type="hidden" name="flag" value="true" />
			</form>
		</div>
		<!-- Footer -->
	</div>
</body>
</html>