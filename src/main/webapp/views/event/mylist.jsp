<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<security:authorize access="hasRole('DINER')">
	<div class="btn-group btn-group-xs" role="group" aria-label="label">
	
		<button onclick="javascript:location.href='event/diner/create.do'"
			type="button" class="btn btn-default">
			<spring:message code="event.create" />
		</button>
	</div>

	<br />

	<acme:list list="${courses}" requestURI="event/diner/mylist.do"
		hidden_fields="id,version" field_mapping="{levelCourse:value}"
		entityUrl="{soirees:soirees/list.do,diners:event/diner/listByEvent.do}"
		editUrl="event/diner/edit.do" deleteUrl="event/diner/delete.do"></acme:list>

</security:authorize>
