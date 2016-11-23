<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理画面</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">


<!--

function stopcheck(){

	//「OK」の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm('アカウント停止してもよろしいですか？')){
		location.href = "example_confirm.html"; // example_confirm.html へジャンプ
		return true; // 「OK」時は送信を実行
	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else{
		window.alert('キャンセルされました'); // 警告ダイアログを表示
		return false; // 送信を中止
	}
	// 「キャンセル」時の処理終了
}

function startcheck(){

	//「OK」の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm('アカウント稼働してもよろしいですか？')){
		location.href = "example_confirm.html"; // example_confirm.html へジャンプ
		return true; // 「OK」時は送信を実行
	}
	// 「OK」時の処理終了

	// 「キャンセル」時の処理開始
	else{
		window.alert('キャンセルされました'); // 警告ダイアログを表示
		return false; // 送信を中止
	}
	// 「キャンセル」時の処理終了
}

// -->
</script>

</head>
<body>

<h2>ユーザー管理画面</h2>


<div class="header">
	<a href="signup">ユーザー登録</a>
	<a href="./">ホーム画面</a>
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


<br />
<br />
<div class="main-contents">




<div class="users">
	<div class="user">
		<table border="1">
			<tr>
				<td><b>ユーザー名</b></td>
				<td><b>ログインID</b></td>
				<td><b>支店</b></td>
				<td><b>部署・役職</b></td>
				<td><b>アカウント</b></td>
				<td><b>編集</b></td>
			</tr>

			<c:forEach items="${users}" var="user">
				<tr>
					<td>${user.name}</td>
					<td>${user.account}</td>

					<c:forEach items="${branches}" var="branch">
						<c:if test ="${user.branchID == branch.id}">
							<td>${branch.name}</td>
						</c:if>
					</c:forEach>

					<c:forEach items="${departments}" var="department">
						<c:if test ="${user.departmentID == department.id}">
							<td>${department.name}</td>
						</c:if>
					</c:forEach>


					<td>
					<c:if test="${ loginUser.id != user.id }">
						<form action="setting" method="post" onSubmit="return startcheck()">
							<input type="hidden" name="id" value="${user.id}"></input>
							<c:if test ="${user.stopped == true}">
								<input type="submit"  value="稼動" /> 停止中
								<input type="hidden" name="stopped" id="stopped" value="false"></input>

							</c:if>
						</form>
						<form action="setting" method="post" onSubmit="return stopcheck()">
						<input type="hidden" name="id" value="${user.id}"></input>
							<c:if test = "${user.stopped == false}">
								<input type="submit"  value="停止" />
								<input type="hidden" name="stopped" id="stopped" value="true"></input>
							</c:if>
						</form>
					</c:if>
					</td>


					<td><a href="editing?id=${user.id}">編集</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<br />
<br />


<div class ="copyright">Copyright(c)Marin Sato</div>
</div>
</div>
</body>
</html>
