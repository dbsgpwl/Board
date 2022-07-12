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
			
			<%
				//list 구성에 사용되는 변수들
				int totalcount = 0; 	//전체 게시물 건수 ex) 500건
				int numPerPage=10; 		//페이지당 표시할 게시물 수 ex)10건
				
				int totalPage=0; 		//전체 페이지 수 ex)50페이지
				int nowPage=1; 			//현재 페이지 번호 ex
				
				int start=1; 			//게시물 읽을 때 사용되는 시작 값
				int ent=10; 			//게시물 읽을 때 사용되는 끝 값
				
				//Pageing 처리를 위한 변수들
				int pagePerBlock=15; 	//블럭당 표시할 페이지 수 ex) 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 -> 16,17,...
				int totalBlock=0; 		//전체 페이징 블럭 수
				int nowBlock=1;			//현재 페이징 블럭 수
				
				
			%>
			
			<%
				totalcount = (int) request.getAttribute("tcnt"); //전체 게시물 수 받기
				totalPage = (int) Math.ceil(totalcount/numPerPage); //전체 페이지 수 계산
				
				//전체 블럭 수 계산
				totalBlock = (int)Math.ceil((double)totalPage/pagePerBlock);
				//현재 블럭 숫자 계산
				nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);
			%>
			
			<script>
				//페이징 처리 함수
				function paging(pageNum)
				{
					form=document.readFrm;
					form.nowPage.value=pageNum;
					var numPerPage = <%=numPerPage%> //numPerPage 10으로 고정하여 
					form.start.value=(pageNum*numPerPage)-numPerPage+1;
					form.end.value=(pageNum*numPerPage);
					form.action="/Board/list.do"; //페이지 넘버를 클릭하는 순간, start와 end 값 전달
					form.submit();
				}
			</script>
				<!-- 페이징 처리 폼 -->
				<form name="readFrm" method="get"> 		
					<input type="hidden" name="no" />		<!-- 게시물 번호 -->
					<input type="hidden" name="start" /> 	<!-- DB로부터 읽을 시작 번호 -->
					<input type="hidden" name="end" />  	<!-- DB로부터 읽을 끝 번호 -->
					<input type="hidden" name="nowPage" /> 	<!-- 현재 페이지 번호 -->
				</form>
			<!-- 현재페이지/전체페이지 -->
			<table class="table">
				<tr >
					<td style=border:0px;>1/<%= %> Page</td> 
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
						  
						  <!-- 이전으로 -->
						  <%
						   	if(nowBlock>1)
						   	{
						  %>
						    <li class="page-item">
						      <a class="page-link" href="#" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
						    <% 
						    }
						  	%> 
						  	<%
						  		int pageStart=(nowBlock-1)*pagePerBlock+1;
						  		int pageEnd=((pageStart+pagePerBlock)<=totalPage)?(pageStart+pagePerBlock):totalPage+1;
						  	%>
						    <!-- 페이지 번호 -->
						    
						    <%
						    	for( ;pageStart<=pageEnd;pageStart++)
						    	{
						    %>
						   		 <li class="page-item"><a class="page-link" href="javascript:paging('<%=pageStart%>')"><%=pageStart %></a></li>
						    <% 		
						    	}
						    %>
						    
						    
						    <!-- 다음으로 -->
						    <%
						   	if(totalBlock>nowBlock)
						   	{
						 	 %>
						    <li class="page-item">
						      <a class="page-link" href="#" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
						    <% 
						    }
						  	%> 
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