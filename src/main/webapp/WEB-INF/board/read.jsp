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
      	
      	//시작번호 계산
		int np = Integer.parseInt(nowPage);
		int numPerPage=10;
		int start=(np*numPerPage)-numPerPage+1;
		
		//끝번호 계산 
		int end=np*numPerPage;
		
      %>
      <form action="#" method ="post">
         <input name = "title" class = "form-control mb-3 w-50" value="<%=dto.getTitle() %>"/ >
         <input name="writer" class="form-control mb-3 w-50" value="<%=dto.getWriter() %>"/>
        
         <textarea name = "content"  class = "form-control mb-3 w-50" style= "height:500px;"><%=dto.getContent() %>
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
			      <%@page import="java.net.URLEncoder" %>
			        <%
			        	if(filelist!=null){//파일이름이 있다면 
			        		
			         		for (int i=0; i<filelist.length;i++){
			         			
			         			String tmpfilename = filelist[i].substring(0,filelist[i].lastIndexOf("_"));
			         			tmpfilename += filelist[i].substring(filelist[i].lastIndexOf("."),filelist[i].length());
			         			
			         			filelist[i] = URLEncoder.encode(filelist[i], "utf-8").replaceAll("\\+", "%20"); //인코딩된 파일이름
			         			
			         			
			         			out.println("<a href=/Board/download.do?filename="+filelist[i]+">"+tmpfilename+"("+filesize[i]+"byte)</a><br>");
				        
			         		}
			        	
			        	
			        		
			        		}else{
			        			out.println("파일 없음");
			        		}
		         		
         			%>
         			
			      </div>
				      <div class="modal-footer">
				      
				        <a  id="downall" class="btn btn-dark" href="#"> 모두 받기(NoZIP)</a>
				        <a class="btn btn-dark" href="/Board/downloadAll.do">모두받기(ZIP)</a>
			        
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			      </div>
			      
			      <!-- 다중파일 무압축 받기 -->
			      <form name="multiform">
			      	<%
			      	
			      		for(int i=0; i<filelist.length; i++){
			      			
			      	%>
			      			<input type="hidden" name="file" value=<%=filelist[i] %> /> <%-- <%=filelist[i] %> :인코딩 된 파일 리스트 이름들 --%>
			      	<%		
			      		}
			      	%>
			      </form>
			      
			 <script>
				    $(document).ready(function () {
				    	
				        form = document.multiform;
				        var iFrameCnt = 0;			//프레임 개수 확인, 프레임 이름 지정
				
				        $('#downall').click(function (event){				//다운로드 이미지 실행
				        	
				            for (i = 0; i < form.childElementCount; i++) {	//form 하위에 있는 자식태그의 개수만큼 반복
				
				                fileName = form[i].value;
				                var url = "/Board/download.do?filename=" + fileName;		//전달할 url 경로 넣어주기
				                fnCreateIframe(iFrameCnt);	//보이지 않는 iframe 생성, name 은 숫자로
				                $("iframe[name=" + iFrameCnt + "]").attr("src", url);
				                iFrameCnt++;
				                fnSleep(1000);		//1초
				            }
				        });
				        fnCreateIframe = function (name){
				
				            var frm = $('<iframe name = "' + name + '" style = " display: none;"></iframe>');
				            frm.appendTo("body");
				        }
				        fnSleep = function (delay) {
				            var start = new Date().getTime();
				            while (start + delay > new Date().getTime());	//언제까지 반복할 것인지! 현재시간보다 Start+delay가 크다면 반복
				        };
				    });
				</script>
			      
			    </div>
			  </div>
			</div>
			
        
   </div>
</body>
</html>