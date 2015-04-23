<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page session="false" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<jsp:attribute name="pageTitle">Recherche avancée de jouets</jsp:attribute>

    <jsp:attribute name="title">Recherche avancée de jouets</jsp:attribute>
    
    <jsp:body>
   		<div class="row">
        	<div class="col-md-12">
		        <form method="post" class="form-horizontal" action="afficherJouets.htm"> 
		        	<div class="form-group">
			  			<label for="trancheage" class="control-label col-md-2">Tranche d'âge</label>
			  			<div class="col-md-2">
			  				<select class="form-control" name="codetranche" required>
			  					<option value="0">  </option>
			  					<c:forEach  items="${tranches}"  var="item" >
			  						<option value="${item.codetranche}"
			  							<c:if test="${type == 'modif' && item.codetranche == jouet.trancheage.codetranche}">selected</c:if>
			  						>${item.agemin}-${item.agemax} ans</option>
			  					</c:forEach>
			  				</select>
			  			</div>
			  		</div>
			  		<br/>
			       <div class="form-group">
			  			<label for="categorie" class="control-label col-md-2">Catégorie</label>
			  			<div class="col-md-2">
			  				<select class="form-control" name="codecateg" required>
			  					<option value="0">  </option>
			  					<c:forEach  items="${categories}"  var="item" >
			  						<option value="${item.codecateg}" 
			  							<c:if test="${type == 'modif' && item.codecateg == jouet.categorie.codecateg}">selected</c:if>
			  						>${item.libcateg}</option>
			  					</c:forEach>
			  				</select>
			  			</div>
			  		</div>
			  		
			  		<div class="form-group">
				  		<div class="control-label col-md-2"></div>
			  			<div class="col-md-2">
			  				 <input type="submit" class="btn btn-primary" value="Rechercher" />
			  			</div>
			  		</div>
				</form>
			</div>
		</div>
    </jsp:body>
</t:layout>

