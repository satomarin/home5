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

<h2>ユーザー登録画面</h2>


<div class="header">
	<a href="setting">ユーザー管理画面</a>
	<div class="out"><a href="logout">ログアウト</a></div>
</div>


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

<div class="signup">
<form action="signup" method="post"><br /><br />


	<label for="account">ログインID（半角英数字6文字以上20文字以内）</label>
	<input name="account" id="account"value="${user.account}"/><br />

	<label for="name">名前（10文字以内）</label>
	<input name="name" id="name" value="${user.name}"/><br />

	<label for="password">パスワード（記号を含む全ての半角文字6文字以上255文字以内）</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="confirmationPassword">パスワード 確認用</label>
	<input name="confirmationPassword" type="password" id="confirmationPassword"/> <br />

	<label for="branch_id">支店</label>
	<select name="branch_id" size="1">
	<option value="">選択してください</option>
		<c:forEach items="${ branches }" var="branch">
			<c:if test ="${branch.id == user.branchID}">
				<option value="${ branch.id }" selected>${ branch.name }</option>
			</c:if>
			<c:if test ="${branch.id != user.branchID}">
				<option value="${ branch.id }" >${ branch.name }</option>
			</c:if>
		</c:forEach>
	</select>
	<br />
	<label for="department_id">部署・役職</label>
	<select name="department_id" size="1">
	<option value="">選択してください</option>
		<c:forEach items="${ departments }" var="department">
			<c:if test ="${department.id == user.departmentID}">
				<option value="${ department.id }" selected>${ department.name }</option>
			</c:if>
			<c:if test ="${department.id != user.departmentID}">
				<option value="${ department.id }" >${ department.name }</option>
			</c:if>
		</c:forEach>
	</select>
	<br />
	<br /><br />

	<input type="submit" value="登録" /> <br />


</form>
</div>
<div class ="copyright">Copyright(c)Marin Sato</div>
</div>
</body>
</html>