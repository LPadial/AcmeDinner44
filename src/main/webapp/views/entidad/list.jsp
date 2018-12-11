<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- <script type="text/javascript">
	$(document).ready(function(){
		alert($(".gauge").val());
		if($("#gauge").val() == 1){
			$("#gauge").css('color','DarkGrey');
		}else if($("#gauge").val() == 2){
			$("#gauge").css('color','GreenYellow');
		}else if($("#gauge").val() == 3){
			$("#gauge").css('color','SandyBrown');
		}
	});
</script> -->

<!-- Messages -->
<spring:message code="entidad.ticker" var="ticker" />
<spring:message code="entidad.title" var="title" />
<spring:message code="entidad.description" var="description" />
<spring:message code="entidad.gauge" var="gauge" />
<spring:message code="entidad.moment" var="moment" />
<spring:message code="entidad.changeToFinalMode" var="changeToFinalMode" />
<spring:message code="acme.edit" var="edit" />
<spring:message code="acme.delete" var="delete" />
<spring:message code="acme.view" var="view" />


<br />
<!-- Table -->

<display:table name="entidades" id="row" requestURI="${requestURI}"
	pagesize="10" class="table table-hover">

	<display:column property="ticker" title="${ticker}" sortable="false" />
	<display:column property="title" title="${title}" sortable="false" />
	<display:column property="description" title="${description}" sortable="false" />
	<display:column property="gauge" title="${gauge}" sortable="false" style="${(row.gauge == '1')  ? 'color:DarkGrey' : (row.gauge == '2') ? 'color:GreenYellow' : 'color:SandyBrown'} "/>
	<display:column title="${moment}" sortable="false">
		<jstl:if test="${param.language=='es' || empty param.language}">
			<fmt:formatDate value="${row.moment}" pattern="dd-MM-yy hh:mm" />
		</jstl:if>
		<jstl:if test="${param.language=='en'}">
			<fmt:formatDate value="${row.moment}" pattern="yy/MM/dd hh:mm" />
		</jstl:if>
	</display:column>
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<security:authentication property="principal.id" var="id" />
		
		<!-- Mostrar -->		
			<display:column title="${view}" sortable="false">		
				<jstl:if test="${!entidadesFinalMode.contains(row) }">
					<jstl:if test="${row.admin.userAccount.id == id}">
						<acme:url url="administrator/entidad/view.do?q=${row.id}" code="acme.view"/>
					</jstl:if>
				</jstl:if>
			</display:column>
		
			<!-- Editar -->		
			<display:column title="${edit}" sortable="false">		
				<jstl:if test="${!entidadesFinalMode.contains(row) }">
					<jstl:if test="${row.admin.userAccount.id == id}">
						<acme:url url="administrator/entidad/edit.do?q=${row.id}" code="acme.edit"/>
					</jstl:if>
				</jstl:if>
			</display:column>
			
			<!-- Borrar-->
			<display:column title="${delete}" sortable="false">		
				<jstl:if test="${!entidadesFinalMode.contains(row) }">
					<jstl:if test="${row.admin.userAccount.id == id}">
						<acme:url url="administrator/entidad/delete.do?q=${row.id}" code="acme.delete"/>
					</jstl:if>
				</jstl:if>
			</display:column>
			
			<!-- Modo final-->
			<display:column title="${changeToFinalMode}" sortable="false">	
				<jstl:if test="${row.admin.userAccount.id == id && !entidadesFinalMode.contains(row)}">
					<acme:url url="administrator/entidad/finalMode.do?q=${row.id}" code="entidad.changeToFinalMode"/>
				</jstl:if>
			</display:column>
	</security:authorize>

</display:table>

