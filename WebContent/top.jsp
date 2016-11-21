<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板システム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">


<script type="text/javascript">


<!--

function check(){

	//「OK」の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm('よろしいですか？')){
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

<h2>ホーム画面</h2>


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

<div class="main-contents">
<div class="header1">
	<c:if test="${ empty loginUser }">
		<a href="login">ログイン</a>

	</c:if>
	<c:if test="${ not empty loginUser }">
		<a href="newMessage">新規投稿</a>
		<c:if test="${ editUsers.departmentID == 1 && editUsers.branchID == 1}">
			<a href="setting">ユーザー管理</a>
		</c:if>

	</c:if>
</div>
<div class="header2">
	<a href="logout">ログアウト</a>
</div>

<br />

<div class="select">
<form action="./" method="get"><br />

	<label for="message_id">カテゴリー</label>
	<select name="category" size="1">
		<option value="">カテゴリーを選択してください</option>
		<c:forEach items="${ messageCatalogs }" var="messageCatalog">
			<c:if test ="${ category ==  messageCatalog.category }">
				<option value="${ messageCatalog.category }" selected>${ messageCatalog.category }</option>
			</c:if>
			<c:if test ="${ category !=  messageCatalog.category }">
				<option value="${ messageCatalog.category }" >${ messageCatalog.category }</option>
			</c:if>
		</c:forEach>
	</select>

	<br />

	<br />

	<label for="message_id">日付</label>
	<p><input type="date" name="firstTime" value="${ firstTime }" >～<input type="date" name="lastTime" value="${ lastTime }" ></p>


	<br />


	<input type="submit" value="絞り込み" /> <br />
	<br />

	<div><a href="./">絞り込み解除</a></div>


	<br />

</form>
</div>


<br>






<div class="messages">
	<c:forEach items="${messages}" var="message">

		<div class="message">
			<div class="account-name">
				<span class="account">ユーザーID : <c:out value="${message.account}" /></span>
				<span class="name">ユーザー名 : <c:out value="${message.name}" /></span>
			</div>
			<div class="title">タイトル : <c:out value="${message.title}" /></div>
			<div class="category">カテゴリー : <c:out value="${message.category}" /></div>
			<div class="text">本文 :<br />
				<c:forEach var="s" items="${fn:split(message.text, '
				')}">
					<div><c:out value="${s}"></c:out></div>
				</c:forEach>
			</div>
			<div class="date"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
			<br />


			<form action="deleteMessage" method="post" onSubmit="return check()">

					<c:if test="${ editUsers.departmentID == 2 || (editUsers.branchID == message.userBranchId && editUsers.departmentID == 3) || editUsers.id == message.userId}">
						<input type="hidden" name="id" value="${message.messageId}"></input>
						<input type="submit"  value="この投稿を削除" />
					</c:if>

			</form>


		</div>
		<br />

		<div class="comment">
			<c:forEach items="${comments}" var="comment">
				<c:if test ="${comment.messageId == message.messageId}">
					<div class="comment">
						<div class="account-name">
						<br />
							<span class="account">ユーザーID : <c:out value="${comment.account}" /></span>
							<span class="name"> ユーザー名 : <c:out value="${comment.name}" /></span>
						</div>
						<div class="text">コメント : <br />
							<c:forEach var="s" items="${fn:split(comment.text, '
							')}">
								<div><c:out value="${s}"></c:out></div>
							</c:forEach>
						</div>
						<div class="date"><fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
						<br />

						<form action="deleteComment" method="post" onSubmit="return check()">
							<c:if test="${ comment.userId == editUsers.id || editUsers.departmentID == 2}">
								<input type="hidden" name="commentId" value="${comment.commentId}"></input>
								<input type="submit" value="このコメントを削除" /> <br />
							</c:if>
						</form>


					</div>
				</c:if>
			</c:forEach>
		</div>
		<br />

		<div class="comments">

				<form action="comment" method="post" ><br />
					<textarea name="text" cols="100" rows="5" class="tweet-box">${comment.text}</textarea>
					<br />
					<input type="submit" value="コメントする"">(500文字まで)
					<input type="hidden" name="messageId" value="${message.messageId}"></input>
				</form>

		</div>
		<br />
		<br />
	</c:forEach>
</div>

</div>


<div class ="copyright">Copyright(c)Marin Sato</div>
</div>
</body>
</html>