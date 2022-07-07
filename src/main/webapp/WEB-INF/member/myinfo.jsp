<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의정보</title>

<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="resources/css/common.css">

</head>
<%
		String email=(String)request.getAttribute("email");
		String addr1=(String)request.getAttribute("addr1");
		String addr2=(String)request.getAttribute("addr2");
		if(email==null){ //
			response.sendRedirect("/");
		}
	%>
<body>
	<div class="contanier-md" id="wrapper" style="width:80%; margin:100px auto;">
		<!-- TopMenu -->
		<%@include file="/resources/includes/topmenu.jsp" %>
		<!-- Nav -->
		<%@include file="/resources/includes/nav.jsp" %>
		<!-- MainContents -->
		<div id="maincontents" style="border: 1px solid gray; margin-top:15px;">
		<h3 align=center style="margin-top:30px;">회원정보</h3>
			<table class="table w-75 table-striped" style="margin:100px auto; text-align:center;">
				<tr>
					<td>Email</td>
					<td name="email"><%=email %></td>
				</tr>
				<tr>
					<td>Addr1</td>
					<td name="addr1"><%=addr1 %></td>
				</tr>
				<tr>
					<td>Addr2</td>
					<td name="addr2"><%=addr2 %></td>
				</tr>
				<tr>
					<td >
						<button class="btn btn-outline-dark w-100">정보수정</button>
					</td>
					<td>
						<button class="btn btn-outline-dark w-100">메인이동</button>
					</td>
				</tr>
			</table>
		</div>
		<!-- Footer -->
	</div>
</body>
</html>