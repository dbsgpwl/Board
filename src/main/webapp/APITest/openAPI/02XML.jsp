<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link rel="shortcut icon" href="#">  <!-- favicon.ico 오류 404 메시지 방지 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%@include file="/resources/includes/link.jsp" %>
<body>
	<div class="container-md w-75" style="margin:50px auto;">
		<div class="row">
			<div class="col-md-2">
				<label class="form-check-lavel" for="tstart">출발지터미널</label>
				<select name="depTerminalId" id="tstart" class="form-select" id="tstart">
				</select>
			</div>
			<div class="col-md-2">
				<label class="form-check-lavel" for="tend">목적지터미널</label>
				<select name="arrTerminalId" id="tend" class="form-select" id="tstart">
				</select>
			</div>
			<div class="col-md-2">
				<label class="form-check-lavel" for="tstart">출발날짜</label>
				<input type="date" name="depPlandTime" id="date" class="form-control" />
			</div>
			<div class="col-md-2">
				<label class="form-check-lavel" for="tstart">버스등급</label>
				<select name="busGradeId" id="busGradeId" class="form-select">
					<option value=1 selected>우동</option>
					<option value=2 >고속</option>
					<option value=3 >심야우등</option>
					<option value=4 >심야고속</option>
					<option value=5 >일반</option>
					<option value=6 >일반심야</option>
					<option value=7 >프리미엄</option>
					<option value=8 >심야프리미엄</option>
				</select>
			</div>
			
			<div class="col-md-2">
				<br>
				<a href="javascript:ReqBusInfo()" class="btn btn-dark">조회</a>
			</div>
		</div>
	</div>
	
	
	<div class="row">
		<table id="resulttbl" class="table table-striped" style="text-align:center;">
			
		</table>
	</div>

	<script>
	$.ajax({
		type : "GET",
		dataType : "xml",
		url : "/ShowBusXML.Info",
		contentType : "application/xml",	//요청하는 파일 형식
		success : function(result){
			//alert(result);
			$(result).find('item').each(function(){
				var terminalId=$(this).find("terminalId").text();
				var terminalNm=$(this).find("terminalNm").text();
				$('#tstart').append("<option value="+terminalId+">"+terminalNm+"</option>");
				$('#tend').append("<option value="+terminalId+">"+terminalNm+"</option>");
				
			})				
		},
		error : function(request,status,error){
			alert("에러발생");
		}
	});
		
		// API로부터 요청받은 버스 데이터 정보 
		function ReqBusInfo()
	{
		$.ajax({
			type : "GET",
			dataType : "json",
			url :"/ReqBus.Info",
			data :{
				"depTerminalId":$("#tstart").val(),"arrTerminalId":$("#tend").val(),"depPlandTime":$("#date").val(),
				"busGradeId":$("#busGradeId").val()
				},
			contentType : "application/json",
			success : function(result){
				
				bodylist=result["response"]["body"]["items"]["item"];
				str="<tr>"
				str+="<th>출발지터미널</th>"
				str+="<th>목적지터미널</th>"
				str+="<th>출발시간</th>"
				str+="<th>도착시간</th>"
				str+="<th>이용요금</th>"
				str+="</tr>"
				
				for(i=0;i<bodylist.length;i++)
				{
					str+="<tr>"
					str+="<td>"+bodylist[i].depPlaceNm+"</td>"	    
					str+="<td>"+bodylist[i].arrPlaceNm+"</td>"	   
					str+="<td>"+bodylist[i].depPlandTime+"</td>"  
					str+="<td>"+bodylist[i].arrPlandTime+"</td>"  
					str+="<td>"+bodylist[i].charge+"</td>"		   
					str+="</tr>"
				}
				
				$("#resulttbl").append(str);
			},
			error : function(){
				alert("에러시바..");
			}
		});	
	}
	
		
		
	</script>
</body>
</html>