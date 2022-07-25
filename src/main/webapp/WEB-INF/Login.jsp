<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="/resources/css/common.css">
</head>
<body>
	<%
		String MSG=(String)request.getAttribute("MSG");
		if(MSG!=null){ //요청 받은 메시지가 있다면! 메시지창 뜨기~
			%>
			<script>
				alert("<%=MSG%>");
			</script>
			<%
		}
	%>
	<div class="container-md" style="width:500px; height:500px; margin:0px auto;">
	<form action="/Login.do" method="post">
		<div class="row m-3" id="logo">
			<div class="col" style="text-align:center; color:pink;">
				<i class="bi bi-person" style="font-size:5rem;"></i>
			</div>
		</div>
		<div class="row m-3">
			<input name="email" type="email" class="form-control" placeholder="아이디를 입력하세요">
		</div>
		<div class="row m-3">
			<input type="password" name="pwd" class="form-control" placeholder="패스워드를 입력하세요">
		</div>
		<div class="row m-3">
			<input type="submit" value="LOGIN" class="btn btn-outline-dark">
		</div>
		<div class="row m-3">
			<a href="/MemberJoin.do" class="btn btn-outline-dark">JOIN</a>
		</div>
	</form>
	</div>
</body>
</html>