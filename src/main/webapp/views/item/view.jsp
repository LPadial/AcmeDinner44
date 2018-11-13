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
<spring:message code="item.SKU" var="SKU" />
<spring:message code="item.name" var="name" />
<spring:message code="item.photo" var="photo" />
<spring:message code="item.price" var="price" />
<spring:message code="item.VAT" var="VAT" />
<spring:message code="item.retailed" var="retailed"/>
<spring:message code="acme.view" var="view"/>


<div class="row" style="text-align:center">
	
		<div class='title'>${SKU}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${item.SKU}" />
		<br />
		<br />
		
		<div class='title'>${name}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${item.name}" />
		<br />
		<br />
		
		<div class='title'>${photo}:</div>
		&nbsp;&nbsp;
		<img src="${item.photo}" style="max-width:300px;max-height:300px;">
		<br />
		<br />
		
		<div class='title'>${price}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${item.price}" />
		<br />
		<br />
		
		<div class='title'>${VAT}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${item.VAT}" />
		<br />
		<br />
		
		<div class='title'>${retailed}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${item.retailed}" />
		<br />
		<br />
	
</div>