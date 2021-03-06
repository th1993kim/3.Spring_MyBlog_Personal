<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- tiles를 사용하기 위한 taglib 지시어  --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%-- jstl를 사용하기 위한 taglib 지시어 --%>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- contextPath를 변수명이 contextPath에 담는 jstl의 core 부분 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- List 사이즈 , 개행문자 처리  --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 시간,숫자 형식 --%>
<c:set var="contextPath" value ="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<style>
.footer-container{ 
	margin-top:100px;
	width:100%;
	height:150px;   
	background-color:#5B6882;
	text-align:center;
	font-family: 'Open Sans';
}
.footer-left{
	display:inline-block;
	width:550px;
	 color:white;
	font-weight: 500;
	font-size:1rem;
}
.footer-left a{
	text-decoration: none;
	color:white;
}
.footer-left a:hover{
	color:skyblue;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="footer-container"> 
		<div class="footer-left">
			<br/> 
			<span>P-Call : 010-5536-5570</span> 
			<br/>   
			<span>E-mail : th1993kim@gmail.com</span>  
			<br/>    
			<span><img src="${contextPath}/resources/imgs/GithubIcon.png" style="width:30px;height:30px;" />GitHub :&nbsp; <a href="https://github.com/th1993kim">Link</a> </span>
			<br/>
			<span>Copyright ⓒ Tae Hyun Kim</span>
		</div>
	</div>
</body> 
<script src="${contextPath}/resources/js/main.js"></script>
</html>