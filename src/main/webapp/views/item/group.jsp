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
<spring:message code="item.SKU" var="SKU" />
<spring:message code="item.name" var="name" />
<spring:message code="item.photo" var="photo" />
<spring:message code="item.price" var="price" />
<spring:message code="item.VAT" var="VAT" />
<spring:message code="item.retailed" var="retailed"/>
<spring:message code="acme.view" var="view"/>
<spring:message code="item.copy" var="copy"/>
<spring:message code="item.add" var="add"/>
<spring:message code="item.remove" var="remove"/>
<spring:message code="item.orders" var="num"/>

<jstl:if test="${a==1}">
		<security:authorize access="hasRole('SUPERMARKET')">
			<jstl:if test="${items.size()>0}">
				
				<jstl:forEach items="${dirItems}" var="it" >
				
					<div class="title">${it.key}</div>
					
					<jstl:if test="${notDelivered==1}">
						<acme:url url="supermarket/item/markDelivered.do?address=${it.key}" code="item.markDelivered"/>
					</jstl:if>
					
					<!-- Table -->
					<jstl:set var="listItems" value="${it.value}" />
					<display:table  name="${listItems}" id="val" requestURI="${requestURI}" pagesize="10" class="table table-hover">
						<display:column property="SKU" title="${SKU}" sortable="false"/>
						<display:column property="name" title="${name}" sortable="false"/>
						
						<display:column title="${photo}" sortable="false">
							<img src="${val.photo}" style="max-width:120px;max-height:120px;">
						</display:column>
						
						<display:column property="price" title="${price}" sortable="false"/>
						<display:column property="VAT" title="${VAT}" sortable="false"/>
						
						<security:authentication property="principal.id" var="id" />
						
						<jstl:if test="${val.supermarket.userAccount.id == id}">
						
							<display:column title="${retailed}" sortable="false" >
								<jstl:if test="${val.retailed == true}">
									<acme:url url="supermarket/item/changeToNotRetailed.do?q=${v.id}" code="item.changeToFalse"/>
								</jstl:if>
								<jstl:if test="${val.retailed == false}">
									<acme:url url="supermarket/item/changeToRetailed.do?q=${v.id}" code="item.changeToTrue"/>
								</jstl:if>
							</display:column>
							
							<display:column title="${view}" sortable="false">
								<acme:url url="supermarket/item/view.do?q=${val.id}" code="acme.view"/>
							</display:column>
							
							<display:column title="${copy}" sortable="false">
								<acme:url url="supermarket/item/copy.do?q=${val.id}" code="item.copy"/>
							</display:column>	
											
						</jstl:if>
						
					</display:table>
					
				</jstl:forEach>
				
			</jstl:if>
		</security:authorize>
		
	</jstl:if>
	