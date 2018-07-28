<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1.0">
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<meta name="_csrf" content="${_csrf.token}"/>
	<title><tiles:insertAttribute name="title" /></title>
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.0.0/css/bootstrap.min.css" type="text/css"/>
	<script src="https://cdn.bootcss.com/jquery/3.3.0/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/angular.js/1.0.1/angular.min.js"></script>
</head>

<body>
	<%@ include file="/WEB-INF/layouts/template/privilege.jsp" %>

	<%--
		根据具有的角色确定是否已登录.
		若登录,显示Logout链接;若未登录,显示Login链接.
	--%>

	<section class="container">
	<div class="pull-right" style="padding-right:50px">
		<div style="float:left"><a href="?language=zh_CN">中文</a>|</div>
		<div style="float:left"><a href="?language=en_US">English</a>|</div>
		<c:choose>
			<%-- 登出 --%>
			<c:when test="${isAuthenticated}">
				<div style="float:left">
					<%-- csrf开启情况下登出需用post --%>
					<%--
					<a href="<spring:url value="/logout"/>">Logout</a>
					--%>
					<c:url var="logoutUrl" value="/logout"/>
					<form:form action="${logoutUrl}" method="post" id="logout_form">
						<a onclick="document:logout_form.submit();" style="cursor:pointer">Logout</a>
					</form:form>
				</div>
			</c:when>
			<%-- 登录 --%>
			<c:otherwise>
				<div style="float:left">
					<a href="<spring:url value="/login"/>">Login</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	</section>

	<div class="container">
	<div class="jumbotron">
		<div class="header">
			<ul class="nav nav-pills pull-right">
				<tiles:insertAttribute name="navigation" />
			</ul>
			<h3 class="text-muted">Web Store</h3>
		</div>
		<h1><tiles:insertAttribute name="heading" /></h1>
		<p><tiles:insertAttribute name="tagline" /></p>
	</div>

	<div class="row">
		<tiles:insertAttribute name="content" />
	</div>

	<div class="footer">
		<tiles:insertAttribute name="footer" />
	</div>
	</div>
</body>
</html>

