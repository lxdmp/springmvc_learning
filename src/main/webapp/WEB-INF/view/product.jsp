<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<section class="container">
<div class="row">
<div class="col-md-5">
	<img src="<c:url value="/img/${product.productId}.png"></c:url>" alt="image" style = "width:100%"/>
</div>
<div class="col-md-5">
	<h3>${product.name}</h3>
	<p>${product.description}</p>
	<p>
		<strong>Item Code</strong> : ${product.productId}
	</p>
	<p>
		<strong>manufacturer</strong> : ${product.manufacturer}
	</p>
	<p>
		<strong>category</strong> : ${product.category}
	</p>
	<p>
		<strong>Availble units in stock </strong> : ${product.unitsInStock}
	</p>
	<h4>${product.unitPrice} USD</h4>

	<p>
		<a href=" <spring:url value="/products"/> " class="btn btn-default">
			<span class="glyphicon-hand-left glyphicon"></span>back
		</a>
		<a href="#" class="btn btn-warning btn-large">
			<span class="glyphicon-shopping-cart glyphicon"></span>Order Now
		</a>
	</p>
</div>
</div>
</section>

