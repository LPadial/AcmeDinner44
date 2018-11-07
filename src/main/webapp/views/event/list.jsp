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
<spring:message code="event.searchtext" var="eventSearchText" />
<spring:message code="event.search" var="eventSearch" />
<spring:message code="event.ticker" var="eventTicker" />
<spring:message code="event.title" var="eventTitle" />
<spring:message code="event.city" var="eventCity" />
<spring:message code="event.description" var="eventDescription"/>
<spring:message code="event.registeredDiners" var="eventRegisteredDiners" />
<spring:message code="event.soirees" var="eventSoirees" />
<spring:message code="event.view" var="view" />
<spring:message code="event.register" var="register" />
<spring:message code="event.unregister" var="unregister" />
<spring:message code="event.regist" var="regist" />
<spring:message code="event.edit" var="edit" />
<spring:message code="event.delete" var="delete" />
<spring:message code="event.createsoirees" var="createsoirees" />


<security:authorize access="permitAll">
	
	<!-- Finder -->
	<form class="searchForm" action="event/search.do" method="get">
		${eventSearchText}
		<input type="search" class="form-control" name="q" placeholder="${eventSearch}">
	</form>

	<jstl:if test="${a==0}">
		
		<br>
		<!-- Table -->
		<display:table  name="events" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
		
			<display:column property="ticker" title="${eventTicker}" sortable="false" />
			<display:column property="title" title="${eventTitle}" sortable="false" />
			<display:column property="city" title="${eventCity}" sortable="false" />
			<display:column property="description" title="${eventDescription}" sortable="false" />
			<display:column title="${eventRegisteredDiners}">
				<acme:url url="event/diner/list.do?q=${row.id}" code="event.registeredDiners"/>
			</display:column>
			<display:column title="${eventSoirees}" sortable="false">
				<acme:url url="event/soiree/list.do?q=${row.id}" code="event.soirees"/>
			</display:column>
			
			<security:authorize access="hasRole('DINER')">
		
			<display:column title="${view}" sortable="false">
				<acme:url url="event/view.do?q=${row.id}" code="event.view"/>
			</display:column>
			
			<security:authentication property="principal.id" var="id" />
			
			<display:column title="${edit}" sortable="false">
				<!-- Veo mis eventos -->
				<jstl:if test="${row.organizer.userAccount.id == id}">
					<acme:url url="event/edit.do?q=${row.id}" code="event.edit"/>
				</jstl:if>
			</display:column>
			
			<display:column title="${delete}" sortable="false">
				<!-- Veo mis eventos -->
				<jstl:if test="${row.organizer.userAccount.id == id}">
					<acme:url url="event/delete.do?q=${row.id}" code="event.delete"/>
				</jstl:if>
			</display:column>
			
			<display:column title="${regist}" sortable="false">
				<jstl:if test="${!myRegisteredEvents.contains(row) and row.organizer.userAccount.id != id}">
					<acme:url url="event/diner/register.do?q=${row.id}" code="event.register"/>
				</jstl:if>
				<jstl:if test="${myRegisteredEvents.contains(row) and row.organizer.userAccount.id != id}">
					<acme:url url="event/diner/unregister.do?q=${row.id}" code="event.unregister"/>
				</jstl:if>
			</display:column>
			
			<!-- Botón organizar una velada -->
			<display:column title="${createsoirees}" sortable="false">
				<jstl:if test="${myRegisteredEvents.contains(row)}">
					<acme:url url="event/soiree/create.do?q=${row.id}" code="event.createsoirees"/>				
				</jstl:if>
			</display:column>

		</security:authorize>
	
		</display:table>
		
		
		
	</jstl:if>

	<!--<jstl:if test="${a==1}">

		<acme:list requestURI="course/listByAcademy.do" list="${courses}"
			hidden_fields="applications,academy"  time_stamps="{time:hh mm,start:dd/MM/yyyy,end:dd/MM/yyyy}"
			field_mapping="{levelCourse:value,style:name}"
			entityUrl="{style:style/view.do,academy:academy/view.do}">
		</acme:list>

	</jstl:if>

	<jstl:if test="${a==2}">

		<acme:list requestURI="course/listByStyle.do" list="${courses}"
			hidden_fields="id,version,applications"  time_stamps="{time:hh mm,start:dd/MM/yyyy,end:dd/MM/yyyy}"
			field_mapping="{levelCourse:value,academy:commercialName}"
			entityUrl="{style:style/view.do,academy:academy/view.do}">
		</acme:list>

	</jstl:if>-->

</security:authorize>
