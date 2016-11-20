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
	<c:if test="${ isShowMessageForm }">
		<form action="newMessage" method="post"><br />
			<pre>件名(50文字以内)</pre>
			<textarea name="title" cols="100" rows="5" class="tweet-box"  >${ title }</textarea>
			<br />

			<pre>カテゴリー(10文字以内)</pre>
			<textarea name="category" cols="100" rows="5" class="tweet-box">${ category }</textarea>
			<br />

			<pre>本文(1000文字以内)</pre>
			<textarea name="message" cols="100" rows="5" class="tweet-box">${ message }</textarea>
			<br />
			<input type="submit" value="投稿"">
		</form>
	</c:if>
</div>
</div>

<a href="./">戻る</a>
</body>
</html>