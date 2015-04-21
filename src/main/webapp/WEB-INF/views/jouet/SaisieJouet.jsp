<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page session="false" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Saisie d'un jouet</jsp:attribute>	

    <jsp:attribute name="title">
    	Formulaire <c:if test="${type == 'ajout'}">d'ajout</c:if><c:if test="${type == 'modif'}">de modification</c:if> d'un jouet
    </jsp:attribute>
    
    <jsp:body>
	  	<form method="post" action="Controleur?action=ajoutJouet" class="form-horizontal" id="form">
	  		<input type="hidden" name="typeAction" value="${type}"  id="type"/>
	  		
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
	  			<div class="col-md-1">
	  				<input type="number"  class="form-control" step="1" min="0"  name="trancheage" value="${jouet.trancheage}" id="trancheage" required/>
	  			</div>
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="categorie" class="control-label col-md-2">Catégorie</label>
	  			<div class="col-md-1">
	  				<input type="number"  class="form-control" name="categorie" value="${jouet.categorie}" step="1" min="0" id="categorie" required/>
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
