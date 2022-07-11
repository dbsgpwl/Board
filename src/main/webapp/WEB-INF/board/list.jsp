<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판</title>

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
			<div>
				<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
				  <ol class="breadcrumb">
				    <li class="breadcrumb-item"><a href="#">Home</a></li>
				    <li class="breadcrumb-item active" aria-current="page">Library</li>
				  </ol>
				</nav>
			</div>
			
			<h1>자유 게시판</h1>
			<!-- 현재페이지/전체페이지 -->
			<table class="table">
				<tr >
					<td style=border:0px;>1/100 Page</td>
					<td style=border:0px;text-align:right;>
						<button class="btn btn-secondary">처음으로</button>
						<button class="btn btn-secondary">글쓰기</button>
					</td>
				</tr>
			</table>
			
			<table class="table">
				<tr>
					<th> NO </th>
					<th> TITLE </th>
					<th> WRITER</th>
					<th> Date</th>
					<th> COUNT</th>
				</tr>
				
				<%@page import="java.util.*,com.korea.dto.*" %>
				<%
					ArrayList<BoardDTO>list = (ArrayList<BoardDTO>)request.getAttribute("list");
					for(int i=0;i<list.size();i++)
					{
				%>
				<tr>
					<td><%=list.get(i).getNo() %></td>
					<td><%=list.get(i).getTitle() %></td>
					<td><%=list.get(i).getWriter() %></td>
					<td><%=list.get(i).getRegdate() %></td>
					<td><%=list.get(i).getCount() %></td>
				</tr>
				<%
					}
				%>
				
				<tr>
					<!-- 페이지네이션 -->
					<td colspan=5  style=border-bottom:0px;>
						<nav aria-label="Page navigation example">
						  <ul class="pagination">
						    <li class="page-item">
						      <a class="page-link" href="#" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
						    <li class="page-item"><a class="page-link" href="#">1</a></li>
						    <li class="page-item"><a class="page-link" href="#">2</a></li>
						    <li class="page-item"><a class="page-link" href="#">3</a></li>
						    <li class="page-item">
						      <a class="page-link" href="#" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
						  </ul>
						</nav>
					</td>
				</tr>
			</table>
			
			<a href="/Board/post.do">글쓰기</a>
		</div>
		<!-- Footer -->
	</div>
</body>
</html>