<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Dictionnaires</jsp:attribute>

    <jsp:attribute name="title">
    	<c:choose>
		  	<c:when test="${annee != null}">
		  		<!-- Quand affichage du dico  -->
		    	Dictionnaire pour le catalogue de l'année ${annee}
		  	</c:when>
		 	<c:otherwise>
		    	Dictionnaires
		  	</c:otherwise>
		</c:choose>
    </jsp:attribute>
    
    <jsp:body>
    	<!-- Affichage du dictionnaire  -->
    	<c:if test="${annee != null}">
   		<h3 style="margin-top:0">Voici le dictionnaire pour le catalogue choisi : (${fn:length(dictionnaire)})</h3>
   		<div class="row">
   			<div class="col-md-1"></div>
   			<div class="col-md-7">
	   			<table class="table table-striped table-hover" style="border:thin grey groove">
			  		<thead>
					 	<TH> Catégorie </TH>
						<TH> Quantité affectée </TH>
			 		</thead>
			 		
			 		<tbody>
				 	<c:set var="total" value="${0}"/>
				 	<c:forEach  items="${dictionnaire}"  var="item" >
					 	<tr>
					     	<td>${item.key.libcateg}</td>
					     	<td class="text-center">${item.value}</td>
					  	</tr>
					  	<c:set var="total" value="${total + item.value}"/>
				 	</c:forEach>
				 	</tbody>
				 	
				 	<tfoot>
				 		<tr>
				 			<td><b><i>Total : </i></b></td>
				 			<td class="text-center">${total}</td>
				 		</tr>
				 	</tfoot>
				</table>
				<c:if test="${fn:length(dictionnaire) == 0}">
					<p class="text-warning text-center">Ups il n'y a aucun enregistrement.</p>
				</c:if>
			</div>
   		</div>
   		<h4><a href="?" class="btn btn-sm btn-default">Retour</a></h4>
   		</c:if>
   		
   		<!-- //Choix du catalogue  -->
   		<c:if test="${annee == null}">
   		<h3 style="margin-top:0">Choisir un catalogue</h3>
   		<div class="row">
   			<div class="col-md-1"></div>
   			<div class="col-md-7">
	   			<form method="post" action="?">
		        	<div class="form-inline">
			        	<label for="selectAnnee">Liste : </label>
			        	&nbsp;&nbsp;&nbsp;
						<select id="selectAnnee" class="form-control" name="annee" required>
							<c:forEach  items="${catalogues}"  var="i" >
								<option value="${i.annee}">${i.annee}</option>
							</c:forEach>
						</select>			        	
			        	&nbsp;&nbsp;&nbsp;
			        	<input type="submit" value="Afficher" class="btn btn-primary" />
		        	</div>
		        </form>
			</div>
   		</div>
   		</c:if>
    </jsp:body>
</t:layout>
