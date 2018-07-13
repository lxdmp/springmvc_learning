
<security:authorize access="hasAnyAuthority('ADD_PRODUCT', 'CUSTOM_FORMAT')" var="isAuthenticated">
</security:authorize>

<security:authorize access="hasAnyAuthority('ADD_PRODUCT')" var="isAuthenticatedWithAddProduct">
</security:authorize>

