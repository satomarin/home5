<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<h2>新規投稿画面</h2>

<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<div class="form-area">
	<form action="newMessage" method="post"><br />

		<label for="title">件名(50文字以内)</label>
		<input name="title" id="title" value="${ message.title }"/>
		<br />

		<label for="category">カテゴリー(10文字以内)</label>
		<input name="category" id="category" value="${ message.category }"/>
		<br />

		<label for="text">本文(1000文字以内)</label>
		<textarea name="text" cols="100" rows="5" class="tweet-box" >${ message.text }</textarea>
		<br />

		<input type="submit" value="投稿"">
	</form>
</div>
</div>
<br /><br />
<a href="./">戻る</a>
</body>
</html>