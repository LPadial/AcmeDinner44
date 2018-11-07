<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Messages -->
<spring:message code="personalsection.birthdate" var="birthdate" />
<spring:message code="personalsection.likes" var="likes" />
<spring:message code="personalsection.dislikes" var="dislikes" />

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="diner/personalSection/save-edit.do"
		skip_fields="birthdate,likes,dislikes" type="edit" entity="${personalsection}">
		
		<label for="label">${birthdate}</label>
		<div class="form-group" style="width: 55%;">
			<input value="<fmt:formatDate value="${personalsection.birthdate}" pattern="dd/MM/yyyy"/>" name="birthdate" placeholder="dd/MM/yyyy" type="text" class="form-control">
		</div>
		
		<label for="label">${likes}</label>
		<div class="form-group" style="width: 55%;">
			<textarea name="likes" style="resize: none;" class="form-control" rows="5" id="likes">${personalsection.likes}</textarea>
		</div>
		
		<label for="label">${dislikes}</label>
		<div class="form-group" style="width: 55%;">
			<textarea name="dislikes" style="resize: none;" class="form-control" rows="5" id="dislikes">${personalsection.dislikes}</textarea>
		</div>

		
	</acme:acme_form>

</security:authorize>
