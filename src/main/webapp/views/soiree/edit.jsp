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
<spring:message code="soiree.date" var="date" />

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="diner/soiree/save-create.do"
		skip_fields="pictures,date"
		hiddenFields="organizer,event,dishes,sponsorships" type="edit" entity="${soiree}">
		
		<label for="label">${date}</label>
		<div class="form-group" style="width: 55%;">
			<input placeholder="dd/MM/yyyy HH:mm" value="<fmt:formatDate value="${soiree.date}" pattern="dd/MM/yyyy HH:mm"/>" type="text" class="form-control" id="date" name="date">
		</div>
		
		<label for="label">${pictures}</label>
		<div class="form-group" style="width: 55%;">			
			<textarea name="pictures" id="pictures" class="form-control"><jstl:forEach items="${soiree.pictures}" var="p" varStatus="loop">${p.trim()}<jstl:if test="${!loop.last}">, </jstl:if></jstl:forEach></textarea>
		</div>
		
	</acme:acme_form>

</security:authorize>
