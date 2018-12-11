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
<spring:message code="diner.actorName" var="dinerName" />
<spring:message code="diner.surname" var="dinerSurname" />
<spring:message code="diner.email" var="dinerEmail" />
<spring:message code="diner.businessCard" var="dinerBusinessCard" />
<spring:message code="diner.events" var="organizedEvents" />
<spring:message code='diner.search' var="search"/>
<spring:message code='diner.avgScore' var="avgScore"/>
<spring:message code='diner.entidades' var="entidades"/>
<spring:message code='diner.createEntidad' var="createEntidad"/>

<!-- Finder -->
<jstl:if test="${finder==1}">
	<form class="searchForm" action="diner/search.do" method="get">
		<spring:message code='diner.searchtext'/>
		<input type="search" class="form-control" name="q" placeholder="${search}">
	</form>
</jstl:if>

<jstl:if test="${a==0}">
	<br/>
	<!-- Table -->

	<display:table  name="diners" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
		
		<display:column property="actorName" title="${dinerName}" sortable="false" />
		<display:column property="surname" title="${dinerSurname}" sortable="false" />
		<display:column property="email" title="${dinerEmail}" sortable="false" />
		<display:column property="avgScore" title="${avgScore}" sortable="false" />
		<display:column title="${dinerBusinessCard}">
			<acme:url url="diner/businessCard/view.do?q=${row.id}" code="diner.businessCard"/>
		</display:column>
		<!-- Comienzo plantilla -->
		<display:column title="${entidades}" sortable="false">
				<acme:url url="diner/entidad/list.do?q=${row.id}" code="diner.entidades"/>
		</display:column>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column title="${createEntidad}" sortable="false">
					<acme:url url="administrator/entidad/create.do?q=${row.id}" code="diner.createEntidad"/>
			</display:column>
		</security:authorize>
		<!--  Fin plantilla -->
		
		<security:authorize access="isAnonymous()">
			<display:column title="${organizedEvents}" sortable="false">
				<acme:url url="diner/eventPast/list.do?q=${row.id}" code="diner.events"/>
			</display:column>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
		<display:column title="${organizedEvents}" sortable="false">
				<acme:url url="diner/event/list.do?q=${row.id}" code="diner.events"/>
			</display:column>
		</security:authorize>

	</display:table>
</jstl:if>

<security:authorize access="isAuthenticated()">
	<jstl:if test="${a!=0}">
		<display:table  name="diners" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
			
			<display:column property="actorName" title="${dinerName}" sortable="false" />
			<display:column property="surname" title="${dinerSurname}" sortable="false" />
			<display:column property="email" title="${dinerEmail}" sortable="false" />
			<display:column property="avgScore" title="${avgScore}" sortable="false" />
				<!-- Comienzo plantilla -->
			<display:column title="${entidades}" sortable="false">
					<acme:url url="diner/entidad/list.do?q=${row.id}" code="diner.entidades"/>
			</display:column>
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<display:column title="${createEntidad}" sortable="false">
						<acme:url url="administrator/entidad/create.do?q=${row.id}" code="diner.createEntidad"/>
				</display:column>
			</security:authorize>
			<!--  Fin plantilla -->
			
			<display:column title="${dinerBusinessCard}">
				<acme:url url="diner/businessCard/view.do?q=${row.id}" code="diner.businessCard"/>
			</display:column>
			<display:column title="${organizedEvents}" sortable="false">
				<acme:url url="diner/event/list.do?q=${row.id}" code="diner.events"/>
			</display:column>
	
		</display:table>
	</jstl:if>
</security:authorize>


