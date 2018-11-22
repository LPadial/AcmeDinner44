<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('SUPERMARKET')">

	<acme:acme_form url="supermarket/item/save-copy.do" skip_fields="retailed" copy="true"
		hiddenFields="supermarket,delivered" numberMin="0" numberSteps="0.01" type="edit" entity="${item}">
		 <div class="checkbox">
		 	<acme:checkBox code="item.retailed" path="retailed" css="w3-check"/>
		</div>
	</acme:acme_form>

</security:authorize>
