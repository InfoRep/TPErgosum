<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date,java.text.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% request.setAttribute("dateNow",  DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL).format(new Date())); %>

<t:layout>
	<jsp:attribute name="pageTitle">Accueil</jsp:attribute>

    <jsp:attribute name="title">Accueil gestion de stage</jsp:attribute>
    
    <jsp:body>
   		<h4>Bonjour, nous sommes le ${dateNow}</h4>
   		
   		<div class="row">
   			<div class="col-md-1"></div>
   			<div class="col-md-11">
	   			<h5 style="">Que souhaitez vous faire ?</h5>
		   		<br/>
		        <div id="menu">
				  	<ul class="list-group">
				      	<li class="list-group-item">
				      		<i class="glyphicon-envelope glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;&nbsp;
				      		<a href="Controleur?action=saisieStage" class="lienAccueil">Saisir un stage</a>
				      		<span class="badge"><a href="Controleur?action=saisieStage"><i class="glyphicon-envelope glyphicon glyphicon-pencil"></i></a></span>
				      	</li>
			      		<li class="list-group-item">
			      			<i class="glyphicon glyphicon-list-alt"></i>&nbsp;&nbsp;&nbsp;
				      		<a href="Controleur?action=rechercheStage&complet" class="lienAccueil">Afficher la liste complète des stages</a>
				      		<span class="badge"><a href="Controleur?action=rechercheStage&complet"><i class="glyphicon glyphicon-list-alt"></i></a></span>
				      	</li>
				      	<li class="list-group-item">
				      		<i class="glyphicon glyphicon-search"></i>&nbsp;&nbsp;&nbsp;
				      		<a href="Controleur?action=rechercheStage" class="lienAccueil">Rechercher un stage</a>
				      		<span class="badge"><a href="Controleur?action=rechercheStage"><i class="glyphicon glyphicon-search"></i></a></span>
				      	</li>
				  	</ul>
				</div>
				<br />
				<h5>La modification et suppression de stages se fait via les boutons sur la liste des stages.</h5>
			</div>
   		</div>
    </jsp:body>
</t:layout>
