<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${article.title }</title>
<!-- 引入样式 -->
<link href="/resource/css/bootstrap.css" rel="stylesheet">

<script type="text/javascript">
	function shou(){
		var text='${article.title}'
			var url="http:localhost:90/article?id=20";
			 $.post("/my/shou/add",{text:text,url:url},function(flag){
				  if(flag){
					  alert("操作成功");
				  }else{
					  alert("操作失败");
				  }
			  })
	}
</script>
</head>
<body>
	<div class="container">
		<h1 align="center">${article.title }</h1><input type="button" value="收藏" onclick="shou()">
		<span style="float: right"><a href="/complain?id=${article.id }">投诉</a></span>

		<h3>${a.user.username}
			<fmt:formatDate value="${article.created }"
				pattern="yyyy-MM-dd HH:mm:ss" />
		</h3>
		<div align="center">${article.content }</div>

		<div>
			<jsp:include page="/WEB-INF/view/index/comment.jsp" />

		</div>
		<div>
			<dl>
				<c:forEach items="${info.list}" var="comment">
					<dt>
						${comment.user.username },
						<fmt:formatDate value="${comment.created }"
							pattern="yyyy-MM-dd HH:mm:ss" />

					</dt>
					<dd>${comment.content }</dd>
					<hr>
				</c:forEach>
			</dl>


		</div>

	</div>
</html>