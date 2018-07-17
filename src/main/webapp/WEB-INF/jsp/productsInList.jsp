<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="custom" uri="/WEB-INF/tlds/Paginator.tld"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<title>产品</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
	<section>
	<div class="jumbotron">
		<div class="container">
			<h1>产品</h1>
			<p>All the available products in our store</p>
		</div>
	</div>
	</section>

	<section class="container">
	<div class="row">
	<c:forEach items="${paginator.items}" var="product">
		<div class="col-sm-6 col-md-3" style="padding-bottom:15px">
		<div class="thumbnail">
		<div class="caption">
			<h3>${product.name}</h3>
			<p>${product.description}</p>
			<p>${product.unitPrice} USD</p>
			<p>Available ${product.unitsInStock} units in stock</p>
			<p>
				<a href=" <spring:url value="/product?id=${product.productId}"/> " class="btn btn-primary">
					<span class="glyphicon-info-sign glyphicon"/></span>Details
				</a>
			</p>
		</div>
		</div>
		</div>
	</c:forEach>
	</div>
	</section>


	<c:url var="productsInListUrl" value="/products/list"/>
	<custom:paginator curr="${paginator.currentPage}" total="${paginator.pageCount}" size="${paginator.pageSize}" href="${productsInListUrl}" beginNavigatorIndex="${paginator.beginNavigatorIndex}" endNavigatorIndex="${paginator.endNavigatorIndex}"/>

	<br/>

	<custom:paginator curr="${paginator.currentPage}" total="${paginator.pageCount}" size="${paginator.pageSize}" href="${productsInListUrl}"/>

</body>
</html>

