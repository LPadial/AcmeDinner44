<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message code="entidad.gauge" var="gauge" />

<security:authorize access="hasRole('ADMINISTRATOR')">

	<acme:acme_form url="administrator/entidad/save-create.do" type="create" numberMin="1" numberSteps="1.0" numberMax="3"
		 hiddenFields="ticker,moment,admin,diner,isSaveFinalMode" skip_fields="gauge" entity="${entidad}">
		 
		 <label for="label">${gauge}</label>
		 <div class="form-group" style="width: 55%;">
		 	<input name="gauge" type="number" min=1 max=3  class="form-control" id="gauge">
		 </div>
	
	</acme:acme_form>
	
</security:authorize>