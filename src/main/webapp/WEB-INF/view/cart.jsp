<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="container" ng-app="cartApp">
	<div ng-controller="cartCtrl" ng-init="initCartId('${cartId}')">
	<div>
		<a class="btn btn-danger pull-left" ng-click="clearCart()">
			<span class="glyphicon glyphicon-remove-sign"></span>Clear Cart
		</a>
		<a href="#" class="btn btn-success pull-right">
			<span class="glyphicon-shopping-cart glyphicon"></span> Check out
		</a>
	</div>
	<table class="table table-hover">
		<tr>
			<th>Product</th>
			<th>Unit price</th>
			<th>Qauntity</th>
			<th>Price</th>
			<th>Action</th>
		</tr>
		<tr ng-repeat="item in cart.cartItems">
			<td ng-cloak>{{item.product.productId}}-{{item.product.name}}</td>
			<td ng-cloak>{{item.product.unitPrice}}</td>
			<td ng-cloak>{{item.quantity}}</td>
			<td ng-cloak>{{item.totalPrice}}</td>
			<td ng-cloak>
				<a href="#" class="label label-danger" ng-click="removeFromCart(item.product.productId)">
					<span class="glyphicon glyphicon-remove" /></span>Remove
				</a>
				<%--
				<a href="#" class="label label-info" ng-click="removeFromCart(item.product.productId)">
					<span class="glyphicon glyphicon-remove-sign" /></span>Remove
				</a>
				--%>
			</td>
		</tr>
		<tr>
			<th></th>
			<th></th>
			<th>Grand Total</th>
			<th ng-cloak>{{cart.grandTotal}}</th>
			<th></th>
		</tr>
	</table>

	<a href="<spring:url value="/products" />" class="btn btn-default">
		<span class="glyphicon-hand-left glyphicon"></span>Continue shopping
	</a>
	</div>
</section>

<script src="<spring:url value="/js/cartController.js" />"></script>

