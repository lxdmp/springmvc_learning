<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<title>添加商品</title>
</head>
<body>
	<section>
	<div class="jumbotron">
	<div class="container">
		<h1>Products</h1>
		<p>Add products</p>
	</div>
	</div>
	</section>

	<section class="container">
	<c:url var="formAction" value="/products/add?${_csrf.parameterName}=${_csrf.token}"/>
	<form:form method="POST" action="${formAction}" modelAttribute="newProduct" class="form-horizontal" enctype="multipart/form-data">
	<fieldset>
	<legend>Add new product</legend>

	<%-- 
		form::input中的path属性值与绑定的bean(通过form:form modelAttribute)中的对应属性值一致
	--%>
	
	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="productId">Product Id</label>
		<div class="col-lg-10">
			<form:input id="productId" path="productId" type="text" class="form:input-large"/>
		</div>
	</div>


	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="name">Name</label>
		<div class="col-lg-10">
			<form:input id="name" path="name" type="text" class="form:input-large"/>
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="unitPrice">Unit Price</label>
		<div class="col-lg-10">
			<form:input id="unitPrice" path="unitPrice" type="text" class="form:input-large"/>
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="manufacturer">Manufacturer</label>
		<div class="col-lg-10">
			<form:input id="manufacturer" path="manufacturer" type="text" class="form:input-large"/>
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="category">Category</label>
		<div class="col-lg-10">
			<form:input id="category" path="category" type="text" class="form:input-large"/>
		</div>
	</div>

	<%--
	<!-- 该字段不由前台提交的表单绑定 -->
	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="unitsInStock">Units in Stock</label>
		<div class="col-lg-10">
			<form:input id="unitsInStock" path="unitsInStock" type="text" class="form:input-large" value="0"/>
		</div>
	</div>
	--%>

	<%--
	<!-- 该字段不由前台提交的表单绑定 -->
	<div class="form-group">
		<label class="control-label col-lg-2 col-lg-2" for="unitsInOrder">Units in Order</label>
		<div class="col-lg-10">
			<form:input id="unitsInOrder" path="unitsInOrder" type="text" class="form:input-large" value="0"/>
		</div>
	</div>
	--%>

	<div class="form-group">
		<label class="control-label col-lg-2" for="description">Description</label>
		<div class="col-lg-10">
			<form:textarea id="description" path="description" rows = "3"/>
		</div>
	</div>

	<%--
	<!-- 该字段不由前台提交的表单绑定 -->
	<div class="form-group">
		<label class="control-label col-lg-2" for="discontinued">Discontinued</label>
		<div class="col-lg-10">
			<form:checkbox id="discontinued" path="discontinued"/>
		</div>
	</div>
	--%>

	<div class="form-group">
		<label class="control-label col-lg-2" for="condition">Condition</label>
		<div class="col-lg-10">
			<form:radiobutton path="condition" value="New"/>New
			<form:radiobutton path="condition" value="Old"/>Old
			<form:radiobutton path="condition" value="Refurbished"/>Refurbished
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-lg-2" for="productImage">
			<spring:message code="addProduct.form.productImage.label"/>
		</label>
		<div class="col-lg-10">
			<form:input id="productImage" path="productImage" type="file" class="form:input-large" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-lg-offset-2 col-lg-10">
			<input type="submit" id="btnAdd" class="btn btn-primary" value ="Add"/>
		</div>
	</div>

	</fieldset>
	</form:form>
	</section>
</body>
</html>

