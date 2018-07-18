<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<section>
<div class="container">
	<h3 class="alert"><spring:message code="fileUploadError.title"/></h3>

	<c:if test="${fileTypeInvalid}">
	<%-- tips内容以,分隔 --%>
	<p><spring:message code="fileUploadError.fileTypeErrorInfo" arguments="${tips}" argumentSeparator=";"/></p>
	</c:if>

	<c:if test="${fileSizeInvalid}">
	<p><spring:message code="fileUploadError.fileSizeErrorInfo" arguments="${tips}" argumentSeparator=";"/></p>
	</c:if>

	<div class="JUMP_TIPS">
		<h3></h3>
	</div>
</div>
</section>

<script type="text/javascript" src="<spring:url value="/js/custom.js" />"></script>
<script type="text/javascript">
var target = '<spring:url value="${jumpUrl}" />';
var delay = ${jumpDelay};
$(document).ready(function(){
	jumper = new delay_jumper(
		delay, target, 
		function(){
			s = jumper.left_secs()+'秒后返回'+'<a href=\''+target+'\'>货物添加页面</a>'+'...';
			$('.JUMP_TIPS').html(s);
		}, 
		function(){
			s = '正在跳转...';
			$('.JUMP_TIPS').html(s);
		}
	);
	jumper.start();
});
</script>

