<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Messages -->
<spring:message code="soiree.pictures" var="pictures" />

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="event/soiree/save-create.do"
		date_stamp="date"
		hiddenFields="organizer,event,dishes,sponsorships,pictures" type="create" entity="${soiree}">
		
		<label for="label">${pictures}</label>
		<div class="form-group" style="width: 55%;">			
			<input type="text" name="pictures" id="pictures" class="form-control">
		</div>
		
	</acme:acme_form>

</security:authorize>
