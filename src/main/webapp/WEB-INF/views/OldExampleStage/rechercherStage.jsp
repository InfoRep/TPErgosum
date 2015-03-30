<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% 
/*
	 * (champ == null) => Page rechercher sans tableau (pas encore de recherche effectuée)
	 * (champ == '') => Affichage de la liste complète sans le champ de recherche
	 * (champ != '') => Recherche (Attention null != "" donc premier cas) 
*/
%>

<t:layout>
	<jsp:attribute name="pageTitle">Recherche de stage</jsp:attribute>

    <jsp:attribute name="title">
	  	<c:if test="${champ != ''}">
	    	Recherche de stage
	  	</c:if>
	  	<c:if test="${champ == ''}">
	   		Listing complet des Stages
	  	</c:if>
    </jsp:attribute>
    
    <jsp:attribute name="javascripts">
    	<script type="text/javascript" src="js/rechercher.js"  charset="UTF-8"></script>
    </jsp:attribute>
    
    <jsp:body>
    	<!-- //Afficher le formulaire de recherche que si on souhaite faire une recherche -->
    	<c:if test="${champ != ''}">
   		<div class="row">
   			<div class="col-md-12">
   				<form method="post" action="Controleur">
		        	<input type="hidden" value="rechercheStage" name="action" />
		        	
		        	<div class="form-inline">
			        	<label>Recherche par libelle : </label>
			        	<input class="form-control" type="text" value="${champ}" name="champ" required />&nbsp;&nbsp;&nbsp;
			        	<input type="submit" value="Rechercher" class="btn btn-primary" />
		        	</div>
		        </form>
		         <br />
   			</div>
   		</div>
        </c:if>
       
       	<!-- //Afficher le tableau que si la liste contient des éléments -->
        <c:if test="${fn:length(liste) > 0}">
        <div class="row">
        	<div class="col-md-12">
		        <table class="table">
			  		<tr>
					 	<TH> Numero </TH>
						<TH> Libellé  </TH>
					 	<TH> Date début  </TH>
					 	<TH> Date fin </TH>
					 	<TH>Nombre de places </TH>
				  		<TH>Nombre d'inscrits </TH>
			 		</tr>
				 	
				 	<c:forEach  items="${liste}"  var="item" >
				 	<tr class="text-center">
				     	<td class="id"><a href="Controleur?action=saisieStage&id=${item.id}">${item.id}</a></td>
				     	<td class="text-left libelle">${item.libelle}</td>
				      	<td>
				      		<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${item.datedebut}" pattern="dd/MM/yyyy"/>
				      	</td>
				       	<td>
				       		<fmt:formatDate type="both" dateStyle="short"  timeStyle="short" value="${item.datefin}" pattern="dd/MM/yyyy" />
				      	</td>
				       
				      	<td>${item.nbplaces}</td>
					  	<td>${item.nbinscrits}</td>
					  	<td>
					  		<a class="btn btn-default" href="Controleur?action=saisieStage&id=${item.id}">Modifier</a>&nbsp;&nbsp;&nbsp;
					  		<a class="btn btn-danger btnSuppr" href="Controleur?action=supprimerStage&id=${item.id}">Supprimer</a>
					  	</td>
				  	</tr>
				 	</c:forEach>
				</table>
			</div>
		</div>
		</c:if>
		
		<!-- //Taille liste == 0 ou recherche effectué ou affichage liste complète -->
		<c:if test="${fn:length(liste) == 0 and champ != null}">
			<h3 class="text-center">Aucune donnée.</h3>
		</c:if>
    </jsp:body>
</t:layout>