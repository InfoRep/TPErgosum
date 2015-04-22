<%@page import="com.epul.ergosum.metier.Comporte"%>
<%@page import="com.epul.ergosum.metier.Jouet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page session="false" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
  	//Traiter l'objet comporte : Prendre le premier rempli, pour page modifier sauvegarder la liste pour la modification des valeurs
 	Comporte comporte = null;
	if (request.getAttribute("jouet") != null) {
		Jouet j = (Jouet)request.getAttribute("jouet");
		
		if (j.getComportes().iterator().hasNext())
			comporte = j.getComportes().iterator().next();
		
		request.setAttribute("comportesList", j.getComportes());
	} else
		comporte = new Comporte();

	request.setAttribute("comporte", comporte);
%>

<t:layout>
	<jsp:attribute name="pageTitle">Saisie d'un jouet</jsp:attribute>	

    <jsp:attribute name="title">
    	Formulaire <c:if test="${type == 'ajout'}">d'ajout</c:if><c:if test="${type == 'modif'}">de modification</c:if> d'un jouet
    </jsp:attribute>
    
    <jsp:attribute name="javascripts">
    	<c:if test="${type == 'modif'}">
    		<script type="text/javascript">
   				function changeAnnee() {
   					$("#quantite").val($(".quantites[idcat='"+$("#catalogue").val()+"']").val());
   				}
    		</script>
    	</c:if>
    </jsp:attribute>
    
    <jsp:body>
	  	<form method="post" action="sauverJouet.htm" class="form-horizontal" id="form">
	  		<input type="hidden" name="type" value="${type}"  id="type"/>
	  		
	  		<div class="form-group">
	  			<label for="id" class="control-label col-md-2">Numéro</label>
	  			<div class="col-md-1">
	  				<input class="form-control" type="number" name="id" value="${jouet.numero}" id="id" placeholder="Num" step="1" min="1" required <c:if test="${type == 'modif'}">readonly</c:if>  />
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="libelle" class="control-label col-md-2">Libellé</label>
	  			<div class="col-md-5">
	  				<input type="text" class="form-control" placeholder="Nom du jouet" name="libelle" value="${jouet.libelle}" id="libelle" required />
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="trancheage" class="control-label col-md-2">Tranche d'âge</label>
	  			<div class="col-md-2">
	  				<select class="form-control" name="trancheage" required>
	  					<c:forEach  items="${tranches}"  var="item" >
	  						<option value="${item.codetranche}"
	  							<c:if test="${type == 'modif' && item.codetranche == jouet.trancheage.codetranche}">selected</c:if>
	  						>${item.agemin}-${item.agemax} ans</option>
	  					</c:forEach>
	  				</select>
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="categorie" class="control-label col-md-2">Catégorie</label>
	  			<div class="col-md-2">
	  				<select class="form-control" name="categorie" required>
	  					<c:forEach  items="${categories}"  var="item" >
	  						<option value="${item.codecateg}" 
	  							<c:if test="${type == 'modif' && item.codecateg == jouet.categorie.codecateg}">selected</c:if>
	  						>${item.libcateg}</option>
	  					</c:forEach>
	  				</select>
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<div for="quantite" class="control-label col-md-2">Quantitée de distribution</div>
	  			<div class="col-md-5 form form-inline">
	  				<input type="number" class="form-control" id="quantite" name="quantite" value="${comporte.quantite}" required /> dans le catalogue  
	  				<select class="form-control" name="catalogue" id="catalogue" required onchange="changeAnnee()">
	  					<c:forEach  items="${catalogues}"  var="item" >
	  						<option value="${item.annee}" <c:if test="${comporte.id.annee == item.annee }">selected</c:if>>${item.annee}</option>
	  					</c:forEach>
	  				</select> 
	  				<c:if test="${type == 'modif'}">
		  				<br />
		  				(Changer de catalogue pour voir les autres quantités. Penser à sauvegarder le catalogue actuel avant!)
	  				</c:if>
	  			</div>
	  		</div>
	  		
	  		<c:if test="${type == 'modif'}">
	  		<div id="quantiteHidden" style="display:none">
	  			<c:forEach  items="${comportesList}"  var="item" >
	  				<input type="text" value="${item.quantite}" idCat="${item.id.annee}" class="quantites" />
	  			</c:forEach>
	  		</div>
	  		</c:if>
	  		
	  		<div class="form-group text-right">
  				<c:if test="${type == 'ajout'}">
				    <input type="submit" class="btn btn-primary" value="Ajouter" />
  					<input type="reset" class="btn btn-warning" value="Reset" />
				</c:if>
		        <c:if test="${type == 'modif'}">
				   <input type="submit" class="btn btn-primary" value="Modifier" />
				</c:if>
				
				<a href="afficherJouets.htm" class="btn btn-default">Retour</a>
	  		</div>
	  	</form>
    </jsp:body>
</t:layout>
