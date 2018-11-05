<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="code" required="false" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="target" required="false" %>


<%-- Definition --%>
<a href="${url}" target="${target}">
<button>
<jstl:if test="${code != null}">
	<spring:message code="${code}" />
</jstl:if>
<jstl:if test="${value != null}">
	<jstl:out value="${value}"></jstl:out>
</jstl:if>
</button>
</a>