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


<security:authorize access="permitAll">

	<jstl:if test="${a==0}">
		<security:authorize access="hasRole('SUPERMARKET')">
			<!-- Table -->
			<display:table  name="items" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
		
				<display:column property="SKU" title="${SKU}" sortable="false" />
				<display:column property="name" title="${name}" sortable="false" />
				<display:column title="${photo}" sortable="false" >
					<img src="${row.photo}" style="max-width:120px;max-height:120px;">
				</display:column>
				<display:column property="price" title="${price}" sortable="false" />
				<display:column property="VAT" title="${VAT}" sortable="false" />
				
				<security:authentication property="principal.id" var="id" />
				
				<jstl:if test="${row.supermarket.userAccount.id == id}">
					<display:column title="${retailed}" sortable="false" >
						<jstl:if test="${row.retailed == true}">
							<acme:url url="supermarket/item/changeToNotRetailed.do?q=${row.id}" code="item.changeToFalse"/>
						</jstl:if>
						<jstl:if test="${row.retailed == false}">
							<acme:url url="supermarket/item/changeToRetailed.do?q=${row.id}" code="item.changeToTrue"/>
						</jstl:if>
					</display:column>
					<display:column title="${view}" sortable="false">
						<acme:url url="supermarket/item/view.do?q=${row.id}" code="acme.view"/>
					</display:column>
					<display:column title="${copy}" sortable="false">
						<acme:url url="supermarket/item/copy.do?q=${row.id}" code="item.copy"/>
					</display:column>					
				</jstl:if>
				
			</display:table>

		</security:authorize>
	</jstl:if>
</security:authorize>
