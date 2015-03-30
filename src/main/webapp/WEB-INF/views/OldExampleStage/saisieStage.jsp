<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ //page import="java.text.DateFormat, java.text.SimpleDateFormat, metier.Stage" %>
<% 
	//Traitement des dates
	//Stage stage = (Stage)request.getAttribute("stage");
	//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	//String dd = stage.getDatedebut() != null ? dateFormat.format(stage.getDatedebut()) : "";
	//String df = stage.getDatefin() != null ? dateFormat.format(stage.getDatefin()) : "";
	
	//request.setAttribute("dd", dd);
	//request.setAttribute("df", df);
%>

<t:layout>
	<jsp:attribute name="pageTitle">Saisie d'un stage</jsp:attribute>	

    <jsp:attribute name="title">
    	Formulaire <c:if test="${type == 'ajout'}">d'ajout</c:if><c:if test="${type == 'modif'}">de modification</c:if> d'un stage
    </jsp:attribute>
    
    <jsp:body>
	  	<form method="post" action="Controleur?action=saisieStage" class="form-horizontal" id="form">
	  		<input type="hidden" name="typeAction" value="${type}"  id="type"/>
	  		
	  		<div class="form-group">
	  			<label for="id" class="control-label col-md-2">Numéro</label>
	  			<div class="col-md-1">
	  				<input class="form-control" type="number" name="id" value="${stage.id}" id="id" placeholder="Num" step="1" min="1" required <c:if test="${type == 'modif'}">readonly</c:if>  />
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="libelle" class="control-label col-md-2">Libellé</label>
	  			<div class="col-md-5">
	  				<input type="text" class="form-control" placeholder="Nom du stage" name="libelle" value="${stage.libelle}" id="libelle" required />
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="datedebut" class="control-label col-md-2">Date de début du stage</label>
	  			<div class="col-md-4">
	  				<input type="text" class="form-control date" placeholder="Format yyyy/mm/dd" name="datedebut" value="${dd}" id="datedebut" required />
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="datefin" class="control-label col-md-2">Date de fin de stage</label>
	  			<div class="col-md-4">
	  				<input type="text" class="form-control date"  placeholder="Format yyyy/mm/dd" name="datefin" value="${df}" id="datefin" required />
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="nbplaces" class="control-label col-md-2">Nombre de places</label>
	  			<div class="col-md-1">
	  				<input type="number"  class="form-control" name="nbplaces" value="${stage.nbplaces}" step="1" min="0" id="nbplaces" required/>
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="nbinscrits" class="control-label col-md-2">Nombre d'inscrits</label>
	  			<div class="col-md-1">
	  				<input type="number"  class="form-control" step="1" min="0"  name="nbinscrits" value="${stage.nbinscrits}" id="nbinscrits" required/>
	  			</div>
	  		</div>
	  		
	  		<div class="form-group text-right">
  				<c:if test="${type == 'ajout'}">
				    <input type="submit" class="btn btn-primary" value="Ajouter" />
  					<input type="reset" class="btn btn-warning" value="Reset" />
				</c:if>
		        <c:if test="${type == 'modif'}">
				   <input type="submit" class="btn btn-primary" value="Modifier" />
				</c:if>
	  		</div>
	  	</form>
    </jsp:body>
</t:layout>
