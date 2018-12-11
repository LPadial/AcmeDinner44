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
<spring:message code="entidad.ticker" var="ticker" />
<spring:message code="entidad.title" var="title" />
<spring:message code="entidad.description" var="description" />
<spring:message code="entidad.gauge" var="gauge" />
<spring:message code="entidad.moment" var="moment" />

<div class=row style="text-align:center">
	<div class="col-md-4" ></div>
	<div class="col-md-4" >
	
		<div class='title'>${ticker}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${entidad.ticker}"/>
		<br />
		<br />
		
		<div class='title'>${title}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${entidad.title}" />
		<br />
		<br />
		
		<div class='title'>${description}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${entidad.description}" />
		<br />
		<br />
		
		<div class='title'>${gauge}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${entidad.gauge}" />
		<br />
		<br />
		
		<div class='title'>${moment}:</div>
		&nbsp;&nbsp;
		<jstl:if test="${param.language == 'es' || empty param.language}">
			<fmt:formatDate value="${entidad.moment}" pattern="dd-MM-yy hh:mm" />
		</jstl:if>
		<jstl:if test="${param.language == 'en'}">
			<fmt:formatDate value="${entidad.moment}" pattern="yy/MM/dd hh:mm" />
		</jstl:if>
		
	</div>
	<div class="col-md-4" ></div>

</div>