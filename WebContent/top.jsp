<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板システム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="main-contents">
<div class="header">
	<c:if test="${ empty loginUser }">
		<a href="login">ログイン</a>

	</c:if>
	<c:if test="${ not empty loginUser }">
		<a href="./">ホーム</a>
		<a href="newMessage">新規投稿</a>
		<a href="setting">ユーザー管理</a>
		<a href="logout">ログアウト</a>
	</c:if>
</div>


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


		<p><input type="date" name="firstTime" value="${ firstTime }" ></p>
		<p><input type="date" name="lastTime" value="${ lastTime }" ></p>

	<br />


	<input type="submit" value="絞り込み" /> <br />

	<br />


</form>


<form action="./" method="get"><br />



	<input type="submit" value="絞り込み解除" /><br /><br />



</form>


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

<div class="messages">
	<c:forEach items="${messages}" var="message">

		<div class="message">
			<div class="account-name">
				<span class="account">[ユーザーID]<br /><c:out value="${message.account}" /></span><br />
				<span class="name">[ユーザー名]<br /><c:out value="${message.name}" /></span>
			</div>
			<div class="title">[タイトル]<br /><c:out value="${message.title}" /></div>
			<div class="category">[カテゴリー]<br /><c:out value="${message.category}" /></div>
			<div class="text">[本文]<br /><c:out value="${message.text}" /></div>
			<div class="date"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
			<br />


			<form action="deleteMessage" method="post" >

					<c:if test="${ editUsers.departmentID == 2 || (editUsers.branchID == message.userBranchId && editUsers.departmentID == 3)}">

					<input type="hidden" name="id" value="${message.messageId}"></input>
					<input type="submit"  value="この投稿を削除" />
					</c:if>

			</form>


		</div>

		<div class="comment">
			<c:forEach items="${comments}" var="comment">

				<c:if test ="${comment.messageId == message.messageId}">
					<div class="comment">
						<c:out value="コメント"></c:out>
						<div class="account-name">
							<span class="account">[ユーザーID]<br /><c:out value="${comment.account}" /></span><br />
							<span class="name">[ユーザー名]<br /><c:out value="${comment.name}" /></span>
						</div>
						<div class="text">[本文]<br /><c:out value="${comment.text}" /></div>
						<div class="date"><fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
						<br />

						<form action="deleteComment" method="post" >
							<c:if test="${ comment.userId == editUsers.id }">
								<input type="hidden" name="commentId" value="${comment.commentId}"></input>
								<input type="submit" value="このコメントを削除" /> <br />
							</c:if>
						</form>


					</div>
				</c:if>
			</c:forEach>
		</div>

		<div class="comments">
			<div class="message">
				<form action="comment" method="post" ><br />
					<textarea name="comment" cols="100" rows="5" class="tweet-box"></textarea>
					<br />
					<input type="submit" value="コメントする"">(500文字まで)
					<input type="hidden" name="messageId" value="${message.messageId}"></input>
				</form>
			</div>
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