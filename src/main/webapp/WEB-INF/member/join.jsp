<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="resources/css/common.css">
</head>
<body>
	<div class="contanier-md w-25" style="margin:100px auto; text-align:center; ">
		<h2 class="mb-4">JOIN</h2>
			<form action="/MemberJoin.do" method="post">
				<input type="email" name="email" placeholder="example@example.com" class="form-control m-2">
				<input type="password" name="pwd" placeholder="Enter Password" class="form-control m-2">
				<input name="addr1" placeholder="Enter Address1" class="form-control m-2">
				<input name="addr2" placeholder="Enter Address2" class="form-control m-2">
				<input type="submit" value="가입하기" class="btn btn-outline-dark w-100 m-2">
				<input type="reset" value="RESET" class="btn btn-outline-dark w-100 m-2">
				<a href="/" class="btn btn-outline-dark w-100 m-2 ">Prev</a>
				<input type="hidden" name="flag" value="true">   <!-- flag 파라미터 -->
			</form>
	</div>
</body>
</html>