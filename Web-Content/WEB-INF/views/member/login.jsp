<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp" %>
<div class="container">
	<div class="jumbotron">
		<h2>회원 로그인</h2>
	</div>
	<form action="${contextPath}/member/login" method="post">
	<!-- csrf 토큰 -->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<div class="form-group">
			<label>아이디: </label>
			<input type="text" name="loginId" class="form-control">
		</div>
		<div class="form-group">
			<label>비밀번호 : </label>
			<input type="text" name="loginPwd" class="form-control">
		</div>
		로그인 상태 유지 : <input type="checkbox" name="remember-me"><br>
		<button class="btn btn-primary">로그인</button>
	</form>
</div>
<%@include file="../layout/footer.jsp" %>