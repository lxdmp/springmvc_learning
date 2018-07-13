<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<title>Products</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/privilege.jsp" %>

	<%--
		根据具有的角色确定是否已登录.
		若未登录,显示登录表单;否则不显示.
	--%>

	<section>
	<div class="jumbotron">
	<div class="container">
		<h1>Welcome to Web Store!</h1>
		<p>The one and only amazing web store</p>
	</div>
	</div>
	</section>

	<div class="container">
	<div class="row">
	<div class="col-md-4 col-md-offset-4">
	<div class="panel panel-default">

	<c:choose>
	<c:when test="${isAuthenticated}"> <%-- 当前已登录,不显示表单,显示一些提示 --%>
	<div>
		<p class="alert alert-danger">Already login</p>
	</div>
	</c:when>

	<c:otherwise> <%-- 当前未登录,显示登录表单 --%>
	<div class="panel-heading">
		<h3 class="panel-title">Please sign in</h3>
	</div>
	
	<div class="panel-body">
		<c:url var="loginUrl" value="/login"/>
		<form:form action="${loginUrl}" method="post" class="form-horizontal">
			<c:if test="${param.error!=null}">
				<div class="alert alert-danger">
					<p>Invalid username or password.</p>
				</div>
			</c:if>
			<c:if test="${param.logout!=null}">
				<div class="alert alert-success">
					<p>You have been logged out successfully.</p>
				</div>
			</c:if>
			<c:if test="${param.accessDenied!=null}">
				<div class="alert alert-danger">
					<p>Access Denied: You are not authorised! </p>
				</div>
			</c:if>
			<div class="input-group input-sm">
				<label class="input-group-addon" for="username">
					<i class="fa fa-user"></i>
				</label>
				<input type="text" class="form-control" id="userId" name="userId" placeholder="Enter Username" required>
			</div>
			<div class="input-group input-sm">
				<label class="input-group-addon" for="password">
					<i class="fa fa-lock"></i>
				</label>
				<input type="password" class="form-control" id="password" name="password" placeholder="Enter
Password" required>
			</div>
			<div class="form-actions">
				<input type="submit" class="btn btn-block btn-primary btn-default" value="Log in">
			</div>
		</form:form>
	</div>
	</c:otherwise>
	</c:choose>

	</div>
	</div>
	</div>
	</div>
</body>

