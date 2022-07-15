<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 내용</title>

<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="../resources/css/common.css">

</head>
<body>
   <div class="contanier-md" id="wrapper" style="margin:100px auto;">
      <!-- TopMenu -->
      <%@include file="/resources/includes/topmenu.jsp" %>
      <!-- Nav -->
      <%@include file="/resources/includes/nav.jsp" %>
      <!-- MainContents -->
      <div id="maincontents" style="margin-top:15px;">
         <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
           <ol class="breadcrumb">
             <li class="breadcrumb-item"><a href="#">Home</a></li>
             <li class="breadcrumb-item active"><a href="#">board</a></li>
             <li class="breadcrumb-item active" aria-current="page">Read</li>
           </ol>
         </nav>
      </div>
      <h1>글내용</h1>
      
      <%@page import="com.korea.dto.*" %>
      <%
      	BoardDTO dto = (BoardDTO)request.getAttribute("dto");
      	String nowPage = (String)request.getAttribute("nowPage");
      	String [] filelist=null;
      	String [] filesize=null;
      	if(dto.getFilename()!=null)
      	{	
      		filelist = dto.getFilename().split(";");
      		filesize = dto.getFilesize().split(";");
      	}
      %>
      <form action="#" method ="post">
         <input name = "title" class = "form-control mb-3 w-50" value="<%=dto.getTitle() %>"/ >
         <input name="writer" class="form-control mb-3 w-50" value="<%=dto.getWriter() %>"/>
        
         <textarea name = "content"  class = "form-control mb-3 w-50" style= "height:500px;">
         	<%=dto.getContent() %>
         </textarea>
       
         
         <input type = submit name ="글수정" class = "btn btn-dark"> 
         <a href="#" class = "btn btn-dark">리스트</a>
         <a href="#" class = "btn btn-dark">글삭제</a>
        
      <!-- 첨부파일 -->
			<button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
			  첨부파일 보기
			</button>
      </form>    
      
			
			<!-- Modal -->
			<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			  <div class="modal-dialog modal-dialog-centered">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="staticBackdropLabel">첨부파일 리스트</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			      <!-- 첨부파일 -->
			        <%
			        	if(filelist!=null){//파일이름이 있다면 
			        		
			         		for (int i=0; i<filelist.length;i++){
			         			out.println("<a href=#>"+filelist[i]+"("+filesize[i]+"byte)</a><br>");
				        	}
			        		
			        		}else{
			        			out.println("파일 없음");
			        		}
		         		
         			%>
         			
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			        <button type="button" class="btn btn-dark">모두 받기</button>
			      </div>
			    </div>
			  </div>
			</div>
			
         	
      
      
       
        
      <!-- Footer -->
   </div>
</body>
</html>