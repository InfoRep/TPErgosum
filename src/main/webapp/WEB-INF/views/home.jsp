<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date,java.text.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Accueil</jsp:attribute>

    <jsp:attribute name="title">Accueil Ergosum</jsp:attribute>
    
    <jsp:body>
   		<h4>Bonjour, nous sommes le ${serverTime}</h4>
   		
   		<div class="row">
   			<div class="col-md-1"></div>
   			<div class="col-md-11">
	   			<h5 style="">Que souhaitez vous faire ?</h5>
		   		<br/>
		        <div id="menu">
				  	<ul class="list-group">
				      	<li class="list-group-item">
				      		<i class="glyphicon-envelope glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;&nbsp;
				      		<a href="" class="lienAccueil">d</a>
				      		<span class="badge"><a href=""><i class="glyphicon-envelope glyphicon glyphicon-pencil"></i></a></span>
				      	</li>
				  	</ul>
				</div>
			</div>
   		</div>
    </jsp:body>
</t:layout>
