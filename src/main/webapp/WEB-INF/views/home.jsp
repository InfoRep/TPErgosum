<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date,java.text.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Accueil</jsp:attribute>

    <jsp:attribute name="title">Accueil</jsp:attribute>
    
    <jsp:body>
   		<h3 style="margin-top:0">Bienvenue !</h3>
   		<div class="row">
   			<div class="col-md-1"></div>
   			<div class="col-md-7">
	   			<h4>Nous sommes le ${serverTime}</h4>
   				<br/>
		   		<p>Cette application vous permettra de modifier les jouets ainsi que leurs catalogues.</p>
		   		<h5>En vous souhaitant une agréable visite !</h5>
		   		
		   		<img src="resources/images/jouet.png" width="300" alt="" />
			</div>
			<div class="col-md-4">
	   			<img src="resources/images/polytech.png" width="300" alt="" />
			</div>
   		</div>
    </jsp:body>
</t:layout>
