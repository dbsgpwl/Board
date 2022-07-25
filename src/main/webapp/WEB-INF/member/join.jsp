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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>

		function test(){
			//다움 주소 API
			
		 	addr1 = document.getElementById("addr1");
			zipcode = document.getElementById("zipcode"); 
			
			
		    new daum.Postcode({
		        oncomplete: function(data) {
		        	if(data.userSelectedType==='R') // 도로명 주소인 경우(R : 도로명 주소)
		        	{
		        		//alert("도로명주소 : " +data.roadAddress);
		        		addr1.value=data.roadAddress;
		        		//입력한 내용이 data에 담기게 됨!
		        	}
		        	else							// 지번 주소인 경우 
		        	{
		        		//alert("지번주소 : " +data.jibunAddress); 
		        		addr1.value=data.jibunAddress;
		        		//입력한 내용이 data에 담기게 됨!
		        	}
		        	
		        	zipcode.value=data.zonecode;
		        }
		    }).open();
			
		}

</script>


	<div class="contanier-md w-25" style="margin:100px auto; text-align:center; ">
		<h2 class="mb-4">JOIN</h2>
			<form action="/MemberJoin.do" method="post">
				<input type="email" name="email" placeholder="example@example.com" class="form-control m-2">
				<input type="password" name="pwd" placeholder="Enter Password" class="form-control m-2">
				<!-- 지도! -->
				<div class="row ">
					<div class="col-md-4 ms-2">
						<input type="text" name="zipcode" id="zipcode" placeholder="zipcode" class="form-control" />
					</div>
					<div class="col-md-5">
						<a href="javascript:test()" class="btn btn-dark">우편번호 검색</a>
					</div>
				</div>
				
				<input name="addr1" id="addr1" placeholder="Enter Address1" class="form-control m-2">
				<input name="addr2" placeholder="Enter Address2" class="form-control m-2">
				<input type="submit" value="가입하기" class="btn btn-outline-dark w-100 m-2">
				<input type="reset" value="RESET" class="btn btn-outline-dark w-100 m-2">
				<a href="/index.do" class="btn btn-outline-dark w-100 m-2 ">Prev</a>
				<input type="hidden" name="flag" value="true">   <!-- flag 파라미터 -->
			</form>
	</div>
</body>
</html>