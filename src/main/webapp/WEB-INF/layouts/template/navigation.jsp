<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<li>
	<a href="<spring:url value="/"/>">Home</a>
</li>
<li>
	<a href="<spring:url value="/products"/>">Products</a>
</li>
<li>
	<a href="<spring:url value="/products/add"/>">Add Product</a>
</li>

<%--
<li>
	<a href="<spring:url value="/cart"/>">Cart</a>
</li>
--%>

