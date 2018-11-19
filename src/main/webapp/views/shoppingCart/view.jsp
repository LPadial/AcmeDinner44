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
<spring:message code="shoppingcart.dateCreation" var="dateCreation" />
<spring:message code="shoppingcart.deliveryAddress" var="deliveryAddress" />
<spring:message code="shoppingcart.priceTotal" var="priceTotal" />
<spring:message code="creditCard.brandName" var="brandName" />
<spring:message code="shoppingcart.items" var="items" />
<spring:message code="shoppingcart.isOrdered" var="isOrdered"/>
<spring:message code="shoppingcart.view" var="view" />
<spring:message code="shoppingcart.delete" var="delete" />


<div class=row style="text-align:center">
	<div class="col-md-6" >
	
		<div class='title'>${dateCreation}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${shoppingCart.dateCreation}" />
		<br />
		<br />
		
		
		<div class='title'>${priceTotal}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${shoppingCart.priceTotal}" />
		<br />
		<br />
		
		<div class='title'>${deliveryAddress}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${shoppingCart.deliveryAddress}" />
		<br />
		<br />
		
		<div class='title'>${brandName}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${shoppingCart.creditCard.brandName}" />
		<br />
		<br />
		
		<div class='title'>${isOrdered}:</div>
		&nbsp;&nbsp;
		<jstl:out value="${shoppingCart.isOrdered}" />
		<br />
		<br />		
	</div>
	<div class="col-md-6">
		<h1 class='title'>${items}</h1>
		<!-- Table -->
		<display:table  name="items" id="row" requestURI="diner/shoppingCart/view.do" class="table">
			
			<display:column property="SKU" title="${SKU}" sortable="false" />
				<display:column title="${photo}" sortable="false" >
					<img src="${row.photo}" style="max-width:120px;max-height:120px;">
				</display:column>
				<display:column property="price" title="${price}" sortable="false" />
				<display:column property="VAT" title="${VAT}" sortable="false" />
			
		</display:table>
	</div>
</div>