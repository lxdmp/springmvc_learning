<%-- 
	jsp中某些显示与权限相关,在该jsp中声明所有的权限,显示内容与权限有关的jsp需要include该jsp 
--%>

<security:authorize access="hasAnyAuthority('all', 'ADD_PRODUCT', 'CUSTOM_FORMAT')" var="isAuthenticated">
</security:authorize>

<security:authorize access="hasAnyAuthority('all', 'ADD_PRODUCT')" var="isAuthenticatedWithAddProduct">
</security:authorize>

