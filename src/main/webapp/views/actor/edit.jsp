<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="isAuthenticated()">


	<security:authorize access="hasRole('DINER')">
		<jstl:set var="url" value="actor/save-diner.do" />
		<jstl:set var="person" value="${diner}" />
	</security:authorize>

	<security:authorize access="hasRole('SUPERMARKET')">
		<jstl:set var="url" value="actor/save-supermarket.do" />
		<jstl:set var="person" value="${supermarket}" />
	</security:authorize>
	
	<security:authorize access="hasRole('SPONSOR')">
		<jstl:set var="url" value="actor/save-sponsor.do" />
		<jstl:set var="person" value="${sponsor}" />
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:set var="url" value="actor/save-administrator.do" />
		<jstl:set var="person" value="${administrator}" />
	</security:authorize>


	<acme:acme_form url="${url}" type="edit" another_mapped_classes="domain.Actor" hiddenFields="followers,chirps,bussinessCard,events,finder,avgScore" skip_fields="userAccount" entity="${person}">
		
		<acme:acme_input entity="${person.userAccount}" name="userAccount.username" field="username" showVal="true" />
		<acme:acme_input entity="${person.userAccount}" name="userAccount.password" field="password" typeIn="password"/>
		
		<form:hidden path="userAccount.Authorities" />
	</acme:acme_form>
</security:authorize>


