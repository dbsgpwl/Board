<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패스워드</title>

<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="resources/css/common.css">

</head>
	<%@page import="com.korea.dto.MemberDTO" %>
	<%
		MemberDTO dto = (MemberDTO)request.getAttribute("dto");
	%>
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
<body>
	<div class="contanier-md" id="wrapper" style="width:80%; margin:100px auto;">
	
		<!-- TopMenu -->
		<%@include file="/resources/includes/topmenu.jsp" %>
		
		<!-- Nav -->
		<%@include file="/resources/includes/nav.jsp" %>
		
		<!-- MainContents -->
		<div id="maincontents" style="border: 1px solid gray; margin-top:15px;">
		
			<form action="/MemberUpdate.do" method="post">
				<table class="table w-75 table-striped" style="margin:100px auto; text-align:center;">
					<tr>
						<td><input type="password" name="pwd" class="form-control"></td>
					</tr>
					<tr>
						<td><input type="submit" value="확인" class="btn btn-dark"></td>
					</tr>
				</table>
				
				<input type="hidden" name="flag" value="true">
				
				<!-- 새로운 req가 재요청 되므로 hidden 창 생성해줌 -->
				<input type="hidden" name="addr1" value="<%=request.getParameter("addr1") %>">
				<input type="hidden" name="addr2" value="<%=request.getParameter("addr2") %>">
				<input type="hidden" name="newpwd" value="<%=request.getParameter("pwd") %>">
			</form>
		</div>
		<!-- Footer -->
	</div>
</body>
</html>