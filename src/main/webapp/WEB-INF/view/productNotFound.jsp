<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<section>
<div class="container">
	<h3 class="alert"> There is no product found with the Product id ${invalidProductId}</h3>
	<%--
	<p>${url}</p>
	<p>${exception}</p>
	--%>
	<%--
	<p>
		<a href="<spring:url value="/products" />" class="btn btn-primary">
			<span class="glyphicon-hand-left glyphicon"></span>products
		</a>
	</p>
	--%>
	<div class="JUMP_TIPS">
		<h3></h3>
	</div>
</div>
</section>

<script type="text/javascript" src="<spring:url value="/js/custom.js" />"></script>
<script type="text/javascript">
var target = '<spring:url value="/" />';
var delay = ${jumpDelay};
$(document).ready(function(){
	jumper = new delay_jumper(
		delay, target, 
		function(){
			s = jumper.left_secs()+'秒后返回'+'<a href=\''+target+'\'>主页</a>'+'...';
			$('.JUMP_TIPS').html(s);
		}, 
		function(){
			s = '正在跳转主页...';
			$('.JUMP_TIPS').html(s);
		}
	);
	jumper.start();
});
</script>

