<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!-- Messages -->
<spring:message code="dish.name" var="dishName" />
<spring:message code="dish.description" var="dishDescription" />
<spring:message code="dish.ingredients" var="dishIngredients" />
<spring:message code="dish.dishType" var="dishType" />

<!-- Table -->

<display:table name="dishes" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">	
	<display:column property="name" title="${dishName}" sortable="false" />
	<display:column property="description" title="${dishDescription}" sortable="false" />	
	<display:column property="dishType.value" title="${dishType}" sortable="true"/>
	<display:column property="ingredients" title="${dishIngredients}" sortable="false" />
</display:table>

