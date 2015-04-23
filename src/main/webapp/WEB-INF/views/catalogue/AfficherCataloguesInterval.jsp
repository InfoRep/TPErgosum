<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page session="false" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Liste des catalogues</jsp:attribute>

    <jsp:attribute name="title">Liste des catalogues depuis ${annee} jusqu'� ${anneefin} : (${fn:length(catQuantites)})</jsp:attribute>
    
    <jsp:body>
    	<c:if test="${not empty categorie}">
    		<h4><i>Pour la cat�gorie <span style="color:green">${ categorie.libcateg }</span> : </i></h4>
    	</c:if>
   		<div class="row">
        	<div class="col-md-12">
		        <table class="table table-bordered">
		   
			  		<tr>
					 	<TH> Catalogues </TH>
					 	<th> Num Jouet </th>
						<TH> Quantit� affect�e </TH>
					 	<TH> Quantit� distribu�e </TH>
					 	<TH> Diff�rence </TH>
			 		</tr>
			 	
				 	<c:set var="total" value="0" />
				 	<c:forEach  items="${catQuantites}"  var="item" >
				 	<tr class="text-center">
				     	<td>Ann�e ${item.id}</td>
				     	<td>${item.numeroJouet}</td>
				     	<td>${item.quantite}</td>
				      	<td>${item.quantiteDistribuee}</td>
				      	<td>${item.quantite - item.quantiteDistribuee}</td>
				      	<c:set var="total" value="${total + item.quantite}" />
				  	</tr>
				 	</c:forEach>
				
			 		<tr>
			 			<td class="text-center" style="color:#00F"><b>Total : </b></td>
			 			<td></td>
			 			<td class="text-center" style="color:#00F"><b>${total}</b></td>
			 		</tr>
				</table>
			</div>
		</div>
		<h4><a href="selectionnerAnnee.htm" class="btn btn-sm btn-default">Retour</a></h4>
    </jsp:body>
</t:layout>

