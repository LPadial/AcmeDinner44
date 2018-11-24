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
<spring:message code="vote.comments" var="comments" />
<spring:message code="vote.pictures" var="pictures" />

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="diner/vote/save-create.do"
		numberMin="0"
		numberMax="10" skip_fields="comments,pictures"
		hiddenFields="voter,soiree" type="create" entity="${vote}">
		
		<div class="form-group" style="width: 55%;">	
		
		<label for="label">${comments}</label>
		<textarea name=comments id="comments" class="form-control"></textarea>
		
		<label for="label">${pictures}</label>
		<textarea name=pictures id="pictures" class="form-control"></textarea>
		
		</div>
		
	</acme:acme_form>

</security:authorize>
