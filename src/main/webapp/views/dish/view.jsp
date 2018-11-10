<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>



<!-- Messages -->
<spring:message code="dish.name" var="name" />
<spring:message code="dish.description" var="description" />
<spring:message code="dish.ingredients" var="ingredients" />
<spring:message code="dish.dishType" var="dishType" />

<div class=row style="text-align:center">
	<div class="col-md-4" ></div>
	<div class="col-md-4" >
	
		<div class='title'>${name}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${dish.name}"/>
		<br />
		<br />
		
		<div class='title'>${description}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${dish.description}" />
		<br />
		<br />
		
		<div class='title'>${ingredients}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${dish.ingredients}" />
		<br />
		<br />
		
		<div class='title'>${dishType}:</div>		
		&nbsp;&nbsp;
		<jstl:out value="${dish.dishType.value}" />
		<br />
		<br />
	</div>
	<div class="col-md-4" ></div>

</div>