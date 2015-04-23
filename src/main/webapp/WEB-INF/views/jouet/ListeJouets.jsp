<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page session="false" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Liste jouets</jsp:attribute>

    <jsp:attribute name="title">Liste des jouets : (${fn:length(mesJouets)})</jsp:attribute>
    
    <jsp:body>
   		<div class="row">
        	<div class="col-md-12">
		        <form method="post" action="effacerJouet.htm"> 
			        <table class="table">
				  		<tr>
						 	<TH> 
						 		Numero 
					 		</TH>
							<TH> Libelle </TH>
						 	<TH> 
						 		Tranche age
					 		</TH>
						 	<TH> 
						 		Catégorie 
					 		</TH>
						 	<th></th>
						 	<TH><button class="btn btn-danger"> Supprimer </button></TH>
				 		</tr>
					 	
					 	<c:forEach  items="${mesJouets}"  var="item" >
					 	<tr class="text-center">
					     	<td class="id"><a href="modifierJouet.htm?id=${item.numero}">${item.numero}</a></td>
					     	<td class="text-left libelle">${item.libelle}</td>
					       
					      	<td>${item.trancheage.agemin}-${item.trancheage.agemax} ans</td>
						  	<td>${item.categorie.libcateg}</td>
						  	<td><a href="modifierJouet.htm?id=${item.numero}" class="btn btn-primary">Modifier</a></td>
						  	<td><input type="checkbox" name="id" value="${item.numero}" /></td>
						  	
					  	</tr>
					 	</c:forEach>
					</table>
				</form>
			</div>
		</div>
    </jsp:body>
</t:layout>

