<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Liste des jouets : (${fn:length(mesJouets)})
</h1>

<!-- //Afficher le tableau que si la liste contient des éléments -->
      
        <div class="row">
        	<div class="col-md-12">
		        <table class="table">
			  		<tr>
					 	<TH> Numero </TH>
						<TH> Libelle </TH>
					 	<TH> Code tranche age </TH>
					 	<TH> Code categorie </TH>
			 		</tr>
				 	
				 	<c:forEach  items="${mesJouets}"  var="item" >
				 	<tr class="text-center">
				     	<td class="id"><a href="">${item.numero}</a></td>
				     	<td class="text-left libelle">${item.libelle}</td>
				       
				      	<td>${item.categorie.codecateg}</td>
					  	<td>${item.trancheage.codetranche}</td>
				  	</tr>
				 	</c:forEach>
				</table>
			</div>
		</div>

</body>
</html>
