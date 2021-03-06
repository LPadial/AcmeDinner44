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
<spring:message code="dish.ingredients" var="ingredients" />
<spring:message code="dish.dishType" var="dishType" />

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="diner/dish/save-create.do?q=${soiree}"
		numberMin="1" skip_fields="dishType,ingredients"
		hiddenFields="soiree" type="edit" entity="${dish}">
		
		<label for="label">${ingredients}</label>
		<div class="form-group" style="width: 55%;">			
			<textarea name="ingredients" id="ingredients" class="form-control"><jstl:forEach var="e" items="${dish.ingredients}" varStatus="loop">${e}<jstl:if test="${!loop.last}">,</jstl:if></jstl:forEach></textarea>
		</div>
		
		<div class="form-group" style="width: 55%;">
			<label for="label"><spring:message code='dish.dishType' /></label>
			<select name="dishType">
				<jstl:forEach var="dt" items="${dishTypes}">
					<option value="${dt.id}">${dt.value}</option>
				</jstl:forEach>
			</select>
		</div>
		
	</acme:acme_form>

</security:authorize>
