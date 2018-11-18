<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMINISTRATOR')">

	<b><spring:message code="administrator.numberOfDinersInTheSystem" /> </b>
	<jstl:out value="${numberOfDiners}" />
	<br />
	<br />
	
	
	<b><spring:message code="administrator.minScoreDiners" /> </b>
	<jstl:out value="${avgMinMaxScore[1]}" />
	<br />
	<b><spring:message code="administrator.maxScoreDiners" /> </b>
	<jstl:out value="${avgMinMaxScore[2]}" />
	<br />
	<b><spring:message code="administrator.avgScoreDiners" /> </b>
	<jstl:out value="${avgMinMaxScore[0]}" />
	<br />
	<br />


	<b><spring:message code="administrator.minEventsDiners" /> </b>
	<jstl:out value="${minEventsOrganised}" />
	<br />
	<b><spring:message code="administrator.maxEventsDiners" /> </b>
	<jstl:out value="${maxEventsOrganised}" />
	<br />
	<b><spring:message code="administrator.avgEventsDiners" /> </b>
	<jstl:out value="${avgEventsOrganised}" />
	<br />
	<br /> 
	
	<b><spring:message code="administrator.dinersWhoHaveOrganisedMoreEvents" /> </b>
	<jstl:forEach var="c" items="${dinersMoreEvents}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
		<jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">; </jstl:if>
	</jstl:forEach>
	<br />
	<br />

	<b><spring:message code="administrator.ratioEventsOver" /> </b>
	<jstl:out value="${ratioOfEventsOver}" />
	<br />
	<br />
	
	<b><spring:message code="administrator.minNumDishesPerSoiree" /> </b>
	<jstl:out value="${avgMinMaxNumberOfDishesPerSoiree[1]}" />
	<br />
	<b><spring:message code="administrator.maxNumDishesPerSoiree" /> </b>
	<jstl:out value="${avgMinMaxNumberOfDishesPerSoiree[2]}" />
	<br />
	<b><spring:message code="administrator.avgNumDishesPerSoiree" /> </b>
	<jstl:out value="${avgMinMaxNumberOfDishesPerSoiree[0]}" />
	<br />
	<br />
	
	<b><spring:message code="administrator.ratioDinersWithMoreThan1SocialSection" /> </b>
	<jstl:out value="${ratioPS}" />
	<br />
	<br />
	
	
	<b><spring:message code="administrator.ratioDinersWithMoreThan1ProfessionalSection" /> </b>
	<jstl:out value="${ratioSS}" />
	<br />
	<br />


	<b><spring:message code="administrator.bestSelledItems" /> </b>
	<jstl:forEach var="c" items="${bestSelledItems}" varStatus="loop" end="3">
		<jstl:out value="${c[0].name}" />:
		<jstl:out value="${c[1]}"></jstl:out>
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />
	
	<b><spring:message code="administrator.bestBuyingDiners" /> </b>
	<jstl:forEach var="c" items="${bestBuyingDiners}" varStatus="loop" end="3">
		<jstl:out value="${c[0].actorName}" />:
		<jstl:out value="${c[1]}"></jstl:out>
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />
	
	<b><spring:message code="administrator.bestSellingSupermarkets" /> </b>
	<jstl:forEach var="c" items="${bestSellingSupermarkets}" varStatus="loop" end="3">
		<jstl:out value="${c[0].brand}" />:
		<jstl:out value="${c[1]}"></jstl:out>
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />


	<!-- <b><spring:message code="administrator.avgChirpActor" /> </b>
	<jstl:forEach var="c" items="${chirpActor}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
		<jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">; </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.avgChirpSubcription" /> </b>
	<jstl:forEach var="c" items="${chirpSubcription}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
		<jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br /> -->




</security:authorize>
