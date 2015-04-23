<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Affichage de catalogues</jsp:attribute>

    <jsp:attribute name="title">Choix du catalogue selon un interval</jsp:attribute>
    
    <jsp:body>
   		<div class="row">
        	<div class="col-md-12">
		        <form method="post" class="form-horizontal" action="afficherCatalogues.htm"> 
		        	<p>Choisir une date : </p>
		        	
		        	<div class="form-group">
			  			<label for="catalogues" class="control-label col-md-2">Année de départ : </label>
			  			<div class="col-md-2">
			  				<select id="catalogues" class="form-control" name="annee" required>
			  					<c:forEach  items="${catalogues}"  var="item" >
			  						<option value="${item.annee}">${item.annee}</option>
			  					</c:forEach>
			  				</select>
			  			</div>
			  		</div>
			  		<br/>
			       <div class="form-group">
			  			<label for="interval" class="control-label col-md-2">Nombre d'années : </label>
			  			<div class="col-md-2">
			  				<input type="number" name="interval" min="1" id="interval" class="form-control" required step="1" />
			  			</div>
			  		</div>
			  		
			  		<c:if test="${cat}">
			  		<br />
			  		<div class="form-group">
			  			<label for="categories" class="control-label col-md-2">Catégorie : </label>
			  			<div class="col-md-2">
			  				<select id="categories" class="form-control" name="categorie" required>
			  					<c:forEach  items="${categories}"  var="item" >
			  						<option value="${item.codecateg}">${item.libcateg}</option>
			  					</c:forEach>
			  				</select>
			  			</div>
			  		</div>
			  		</c:if>
			  		
			  		<div class="form-group">
				  		<div class="control-label col-md-2"></div>
			  			<div class="col-md-2">
			  				 <input type="submit" class="btn btn-primary" value="Afficher" />
			  			</div>
			  		</div>
				</form>
			</div>
		</div>
    </jsp:body>
</t:layout>
