<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
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
<form action="signup" method="post"><br />


	<label for="account">ログインID</label>
	<input name="account" id="account"value="${account}"/>（あなたの公開プロフィール: http://localhost:8080/Chapter6/?account=アカウント名）<br />

	<label for="name">名前</label>
	<input name="name" id="name" value="${name}"/>（名前はあなたの公開プロフィールに表示されます）<br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="confirmationPassword">パスワード 確認用</label>
	<input name="confirmationPassword" type="password" id="confirmationPassword"/> <br />

	<label for="branch_id">支店</label>
	<select name="branch_id" size="1">
		<c:forEach items="${ branches }" var="branch">
			<option value="${ branch.id }" >${ branch.name }</option>
		</c:forEach>
	</select>
	<br />
	<label for="department_id">部署・役職</label>
	<select name="department_id" size="1">
		<c:forEach items="${ departments }" var="department">
			<option value="${ department.id }" >${ department.name }</option>
		</c:forEach>
	</select>
	<br />

	<input type="submit" value="登録" /> <br />
	<a href="./">戻る</a>

</form>
<div class="copyright">Copyright(c)Marin Sato</div>
</div>
</body>
</html>