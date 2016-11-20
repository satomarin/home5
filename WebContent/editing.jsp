<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー情報編集</title>
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


<form action="editing" method="post"><br />

	<input type="hidden" name="id" value="${editUser.id}"></input>

	<label for="account">ログインID</label>
	<input name="account" id="account"value="${editUser.account}"/>（あなたの公開プロフィール: http://localhost:8080/Chapter6/?account=アカウント名）<br />

	<label for="name">名前</label>
	<input name="name" id="name" value="${editUser.name}"/>（名前はあなたの公開プロフィールに表示されます）<br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="confirmationPassword">パスワード 確認用</label>
	<input name="confirmationPassword" type="password" id="confirmationPassword"/> <br />

	<label for="branch_id">支店</label>
	<select name="branch_id" size="1">
		<c:forEach items="${ branches }" var="branch">
			<c:if test ="${branch.id == editUser.branchID}">
				<option value="${ branch.id }" selected>${ branch.name }</option>
			</c:if>
			<c:if test ="${branch.id != editUser.branchID}">
				<option value="${ branch.id }" >${ branch.name }</option>
			</c:if>
		</c:forEach>
	</select>
	<br />

	<label for="department_id">部署・役職</label>
	<select name="department_id" size="1">
		<c:forEach items="${ departments }" var="department">
			<c:if test ="${department.id == editUser.departmentID}">
				<option value="${ department.id }" selected >${ department.name }</option>
			</c:if>
			<c:if test ="${department.id != editUser.departmentID}">
				<option value="${ department.id }" >${ department.name }</option>
			</c:if>
		</c:forEach>
	</select>
	<br />

	<input type="submit" value="登録" /> <br />
	<a href="setting">戻る</a>

</form>
<div class="copyright">Copyright(c)Marin Sato</div>
</div>
</body>
</html>