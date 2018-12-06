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
<spring:message code="shoppingcart.creditcard" var="creditcard" />
<spring:message code="shoppingcart.items" var="items" />
<spring:message code="shoppingcart.isOrdered" var="isOrdered"/>
<spring:message code="shoppingcart.view" var="view" />
<spring:message code="shoppingcart.delete" var="delete" />

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="diner/shoppingCart/save-order.do" skip_fields="creditCard"
		hiddenFields="dateCreation,isOrdered,priceTotal,owner" type="edit" entity="${shoppingcart}">
		<label for="label"><spring:message code='shoppingcart.creditcard'/></label>
		<select name="creditCard">
			<jstl:forEach var="cc" items="${creditCards}">
				<option value="${cc.id}">${cc.brandName.value} - ${cc.number}</option>
			</jstl:forEach>
		</select>
		<br>
		<br>
	</acme:acme_form>
	<br>
	<a href="diner/creditCard/create.do?q=${shoppingcart.id}">
		<button style="color:white; background-color:green;">
			<spring:message code="newcreditcard" />
		</button>
	</a>

</security:authorize>