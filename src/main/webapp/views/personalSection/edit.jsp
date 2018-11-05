<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('DINER')">

	<acme:acme_form url="diner/personalSection/save-edit.do"
		skip_fields="birthdate" type="edit" entity="${personalsection}">

		<div class="form-group" style="width: 55%;">
			<input value="<fmt:formatDate value="${personalsection.birthdate}" pattern="dd/MM/yyyy"/>" name="birthdate" placeholder="dd/MM/yyyy" type="text" class="form-control">
		</div>

		
	</acme:acme_form>

</security:authorize>
