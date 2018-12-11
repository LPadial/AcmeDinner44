<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div style="text-align: center;">
	<img src="images/logo.png" alt="Acme-Dinner44 Co., Inc." style="height: 200px;" />
</div>


<div style="width: 100%;">
	<nav class="navbar navbar-light" style="background-color: #e3f2fd;margin-bottom: 0px;">
		<div class="container-fluid">
			<div class="navbar-header">
				<ul class="nav navbar-nav navbar-left">
					<security:authorize access="isAnonymous()">
						<li>
							<a class="fNiv" href="security/login.do">
								<spring:message code="master.page.login" />
							</a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> 
								<spring:message code="master.page.register" /> <span class="caret"></span>
							</a>
							<ol class="dropdown-menu">
								<li><a class="fNiv" href="diner/create.do"><spring:message code="master.page.diner.signup" /></a></li>
								<li><a class="fNiv" href="supermarket/create.do"><spring:message code="master.page.supermarket.signup" /></a></li>
								<li><a class="fNiv" href="sponsor/create.do"><spring:message code="master.page.sponsor.signup" /></a></li>
							</ol>
						</li>
					</security:authorize>				

					<security:authorize access="isAuthenticated()">
						<security:authentication property="principal.id" var="id" />
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"><security:authentication
									property="principal.username" /><span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="actor/edit.do"><spring:message
											code="master.page.actor.edit" /></a></li>
								<li><a href="j_spring_security_logout"><spring:message
											code="master.page.logout" /> </a></li>
							</ul>
						</li>

						<!-- <li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"><spring:message
									code="master.page.actor.chirps" /><span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="chirp/actor/list.do"><spring:message
											code="master.page.actor.chirps" /></a></li>
								<li><a href="chirp/actor/create.do"><spring:message
											code="master.page.actor.createChirps" /></a></li>
								<li><a href="chirp/actor/myListSubscribe.do"><spring:message
											code="master.page.actor.chirpSubscribe" /></a></li>
								<li><a href="chirp/actor/mylist.do"><spring:message
											code="master.page.actor.mychirps" /></a></li>
							</ul>
						</li> -->
					</security:authorize>
					
					<!-- Register new administrator -->
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li>
							<a class="fNiv" href="administrator/dashboard.do">
								<spring:message code="master.page.dashboard" />
							</a>
						</li>
						<li>
							<a class="fNiv" href="administrator/updateScore.do">
								<spring:message code="master.page.updateScore" />
							</a>
						</li>
						<!-- PLANTILLA -->
						<li>
							<a class="fNiv" href="administrator/entidad/list.do">
								<spring:message code="master.page.entidades" />
							</a>
						</li>
						<!-- FIN PLANTILLA -->
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> 
								<spring:message code="master.page.register" /> <span class="caret"></span>
							</a>
							<ol class="dropdown-menu">
								<li><a class="fNiv" href="administrator/create.do"><spring:message code="master.page.administrator.signup" /></a></li>
							</ol>
						</li>
					</security:authorize>
					
					<!-- My business card -->
					<security:authorize access="hasRole('DINER')">
						<li>
							<a class="fNiv" href="diner/businessCard/myView.do">
								<spring:message code="diner.myBusinessCard" />
							</a>
						</li>
					</security:authorize>
					<!-- My events -->
					<security:authorize access="hasRole('DINER')">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
								<spring:message code="diner.events" />
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="diner/event/create.do"><spring:message code="diner.createEvent" /></a></li>
								<li><a href="diner/event/organizedList.do"><spring:message code="diner.organizedEvents" /></a></li>
								<li><a href="diner/event/registeredList.do"><spring:message code="diner.registeredEvents" /></a></li>
							</ul>
						</li>
						<!-- My soirees -->
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
								<spring:message code="diner.soirees" />
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="diner/soiree/organizedList.do"><spring:message code="diner.organizedSoirees" /></a></li>
							</ul>
						</li>
						<!-- My shopping carts -->
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
								<spring:message code="diner.shoppingCarts" />
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a class="fNiv" href="diner/shoppingCart/createShoppingCart.do">
										<spring:message code="diner.createShoppingCart" />
									</a>
								</li>
								
								<li><a href="diner/shoppingCart/mylist.do"><spring:message code="diner.myShoppingCart" /></a></li>
							</ul>
						</li>
					</security:authorize>
					
					<!-- My items -->
					<security:authorize access="hasRole('SUPERMARKET')">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
								<spring:message code="supermarket.items" />
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="supermarket/item/create.do"><spring:message code="supermarket.createItem" /></a></li>
								<li><a href="supermarket/item/mylist.do"><spring:message code="supermarket.myItems" /></a></li>
								<li><a href="supermarket/item/notDelivered.do"><spring:message code="supermarket.itemsToDeliver" /></a></li>
								<li><a href="supermarket/item/delivered.do"><spring:message code="supermarket.itemsDelivered" /></a></li>
							</ul>
						</li>
					</security:authorize>
					
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> 
							<spring:message code="master.page.finder" /> <span class="caret"></span>
						</a>
						<ol class="dropdown-menu">
							<li><a href="event/list.do"><spring:message code="master.page.event" /></a></li>
							<li><li><a href="diner/list.do"><spring:message code="master.page.diner" /></a></li>
						</ol>
					</li>
					
				</ul>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="javascript:setParam('language', 'en');"> <img src="images/flag_en.png" /></a></li>
				<li><a href="javascript:setParam('language', 'es');"> <img src="images/flag_es.png" /></a></li>
			</ul>
		</div>
	</nav>
		
</div>


