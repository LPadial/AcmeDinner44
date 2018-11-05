<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!-- Messages -->
<spring:message code="personalSection.title" var="titlePersonal" />
<spring:message code="personalSection.fullName" var="fullNamePersonal" />
<spring:message code="personalSection.photo" var="photoPersonal" />
<spring:message code="personalSection.birthdate" var="birthdatePersonal" />
<spring:message code="personalSection.likes" var="likesPersonal" />
<spring:message code="personalSection.dislikes" var="dislikesPersonal"/>
<spring:message code="professionalSection.position" var="position"/>
<spring:message code="professionalSection.company" var="company"/>
<spring:message code="socialSection.network" var="network"/>
<spring:message code="socialSection.nickName" var="nickName"/>
<spring:message code="socialSection.link" var="link"/>
<spring:message code="acme.modify" var="modify"/>
<spring:message code="acme.delete" var="delete"/>


<div class=row>
	<div class="col-md-4"></div>
	<div class="col-md-4">	
		<!-- Table -->
		
		<acme:url url="diner/personalSection/edit.do" code="personalSection.modify"/>
		<display:table  name="personalSection" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
			
			<display:column property="title" title="${titlePersonal}" sortable="false" />
			<display:column property="fullName" title="${fullNamePersonal}" sortable="false" />
			<display:column title="${photoPersonal}">
				<img src="${personalSection.photo}" style="max-width: 120px;max-height: 120px;"><br />
			</display:column>
			<display:column title="${birthdatePersonal}" sortable="false">
				<fmt:formatDate value="${personalSection.birthdate}" pattern="dd/MM/yyyy"/>
			</display:column>
			<display:column property="likes" title="${likesPersonal}" sortable="false" />
			<display:column property="dislikes" title="${dislikesPersonal}" sortable="false" />
		</display:table>
	</div>
	<div class="col-md-4"></div>
</div>
<br>
<div class=row>
	<div class="col-md-4"></div>
	<div class="col-md-4">
	<!-- Table -->
		<acme:url url="diner/professionalSection/create.do" code="acme.create"/>
		<display:table  name="professionalSections" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
		
			<display:column property="title" title="${titlePersonal}" sortable="false" />
			<display:column property="position" title="${position}" sortable="false" />
			<display:column property="company" title="${company}" sortable="false" />		
			<display:column title="${modify}">
				<acme:url url="diner/professionalSection/edit.do?q=${row.id}" code="acme.modify"/>
			</display:column>
			<display:column title="${delete}">
				<acme:url url="diner/professionalSection/delete.do?q=${row.id}" code="acme.delete"/>
			</display:column>	
		
		</display:table>
	</div>
	<div class="col-md-4"></div>
</div>
<br>
<div class=row>
	<div class="col-md-4"></div>
	<div class="col-md-4">
	<!-- Table -->
		<acme:url url="diner/socialSection/create.do" code="acme.create"/>
		<display:table  name="socialSections" id="row" requestURI="${requestURI}" pagesize="10" class="table table-hover">
		
			<display:column property="title" title="${titlePersonal}" sortable="false" />
			<display:column property="network" title="${network}" sortable="false" />
			<display:column property="nickname" title="${nickname}" sortable="false" />
			<display:column title="${link}">
				<a href="${row.link}">${row.link}</a><br />
			</display:column>
			<display:column title="${modify}">
				<acme:url url="diner/socialSection/edit.do?q=${row.id}" code="acme.modify"/>
			</display:column>
			<display:column title="${delete}">
				<acme:url url="diner/socialSection/delete.do?q=${row.id}" code="acme.delete"/>
			</display:column>
	
		</display:table>
	</div>
	<div class="col-md-4"></div>
</div>