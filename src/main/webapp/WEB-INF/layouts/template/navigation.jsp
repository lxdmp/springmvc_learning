<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layouts/template/privilege.jsp" %>

<li>
	<a href="<spring:url value="/"/>">Home</a>
</li>

<li>
	<a href="<spring:url value="/products"/>">Products</a>
</li>

<c:if test="${isAuthenticatedWithAddProduct}">
<li>
	<a href="<spring:url value="/products/add"/>">Add Product</a>
</li>
</c:if>

<%--
<li>
	<a href="<spring:url value="/cart"/>">Cart</a>
</li>
--%>

