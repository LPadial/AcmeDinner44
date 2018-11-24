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
<spring:message code="dish.view" var="view" />
<spring:message code="dish.update" var="edit" />
<spring:message code="dish.delete" var="delete" />
<spring:message code="dish.orderServed" var="orderServed" />

<!-- Table -->

<display:table name="dishes" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">	
	<display:column property="orderServed" title="${orderServed}" sortable="false"></display:column>
	<display:column property="name" title="${dishName}" sortable="false" />
	<display:column property="description" title="${dishDescription}" sortable="false" />	
	<display:column property="dishType.value" title="${dishType}" sortable="false"/>
	<display:column title="${dishIngredients}" sortable="false" ><jstl:forEach var="e" items="${row.ingredients}" varStatus="loop">${e}<jstl:if test="${!loop.last}">,</jstl:if></jstl:forEach></display:column>
	
	<security:authorize access="hasRole('DINER')">
		<!-- Mostrar -->
		<display:column title="${view}" sortable="false">
			<acme:url url="dish/view.do?q=${row.id}" code="dish.view"/>
		</display:column>
				
		<security:authentication property="principal.id" var="id" />
		
		<!-- Editar -->		
		<display:column title="${edit}" sortable="false">		
			<jstl:if test="${row.soiree.organizer.userAccount.id == id}">
				<acme:url url="diner/dish/edit.do?q=${row.id}&soiree=${soiree}" code="dish.update"/>
			</jstl:if>
		</display:column>
		
		<!-- Borrar-->
		<display:column title="${delete}" sortable="false">		
			<jstl:if test="${row.soiree.organizer.userAccount.id == id}">
				<acme:url url="diner/dish/delete.do?q=${row.id}&soiree=${soiree}" code="dish.delete"/>
			</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>

