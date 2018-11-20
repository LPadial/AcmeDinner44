<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!-- Messages -->
<spring:message code="shoppingcart.dateCreation" var="dateCreation" />
<spring:message code="shoppingcart.deliveryAddress" var="deliveryAddress" />
<spring:message code="shoppingcart.priceTotal" var="priceTotal" />
<spring:message code="creditcard.brandName" var="brandName" />
<spring:message code="shoppingcart.items" var="items" />
<spring:message code="shoppingcart.isOrdered" var="isOrdered"/>
<spring:message code="shoppingcart.view" var="view" />
<spring:message code="shoppingcart.delete" var="delete" />


<security:authorize access="permitAll">
		
		<security:authorize access="hasRole('DINER')">
			<!-- Table -->
			<display:table  name="shoppingCarts" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
			
				<display:column title="${dateCreation}" sortable="false" >
					<fmt:formatDate value="${row.dateCreation}" pattern="dd/MM/yyyy HH:mm:ss"/>
				</display:column>
				<display:column property="deliveryAddress" title="${deliveryAddress}" sortable="false" />
				<display:column property="priceTotal" title="${priceTotal}" sortable="false" />
				<display:column property="creditCard.brandName.value" title="${brandName}" sortable="false" />
				
				<security:authentication property="principal.id" var="id" />
				
				<display:column title="${items}" sortable="false">
					<!-- Añadir o borrar productos del carro de compra -->
					<jstl:if test="${row.isOrdered == false && row.owner.userAccount.id == id}">
						<acme:url url="diner/shoppingCart/modifyItems.do?q=${row.id}" code="shoppingcart.modifyItems"/>
					</jstl:if>
					<!-- Ver productos del carro de compra -->
					<jstl:if test="${row.isOrdered == true}">
						<acme:url url="diner/shoppingCart/seeItems.do?q=${row.id}" code="shoppingcart.seeItems"/>
					</jstl:if>
				</display:column>
				
				<display:column title="${isOrdered}" sortable="false">
					<!-- Pedir el carro de compra -->
					<jstl:if test="${row.isOrdered == false && row.owner.userAccount.id == id}">
						<acme:url url="diner/shoppingCart/formOrder.do?q=${row.id}" code="shoppingcart.orderShoppingCart"/>
					</jstl:if>
					<!-- El carro ya está pedido -->
					<jstl:if test="${row.isOrdered == true}">
						<spring:message code="shoppingcart.trueIsOrdered"/>
					</jstl:if>
				</display:column>
				
				<display:column title="${view}" sortable="false">
					<acme:url url="diner/shoppingCart/view.do?q=${row.id}" code="shoppingcart.view"/>
				</display:column>
				
				<jstl:if test="${row.owner.userAccount.id == id}">
					<display:column title="${delete}" sortable="false">
						<!-- Borrar el carro de compra -->
						<jstl:if test="${row.isOrdered == false}">
							<acme:url url="diner/shoppingCart/delete.do?q=${row.id}" code="shoppingcart.delete"/>
						</jstl:if>
					</display:column>
				</jstl:if>
				
			</display:table>
		
		</security:authorize>

</security:authorize>
