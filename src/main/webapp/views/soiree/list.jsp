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
<spring:message code="soiree.address" var="soireeAddress" />
<spring:message code="soiree.date" var="soireeDate" />
<spring:message code="soiree.pictures" var="soireePictures" />
<spring:message code="soiree.dishes" var="soireeDishes" />

<!-- Table -->

<display:table  name="soirees" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">

	<display:column property="address" title="${soireeAddress}" sortable="false" />
	<display:column property="date" title="${soireeDate}" sortable="false" />
	<display:column title="${soireePictures}">
		<jstl:forEach var="e" items="${row.pictures}">
			<img src="${e}" style="max-width:120px;max-height:120px;"><br />
		</jstl:forEach>		
	</display:column>
	<display:column title="${soireeDishes}">
		<acme:url url="event/soiree/dish/list.do?q=${row.id}" code="soiree.dishes"/>
	</display:column>

</display:table>

