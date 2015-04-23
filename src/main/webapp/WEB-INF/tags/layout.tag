<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="pageTitle" fragment="true"%>
<%@attribute name="css" fragment="true"%>
<%@attribute name="javascripts" fragment="true"%>

<%@attribute name="title" fragment="true"%>
<%@attribute name="content" fragment="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><jsp:invoke fragment="pageTitle" /> ~ Ergosum<% //Titre de la page %> </title>

<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/images/icone.png" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/lib/bootstrap/css/theme_bleu.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/lib/bootstrapDialog/css/bootstrap-dialog.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/lib/zebra_Datepicker/css/bleu.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/layout_bleu.css">

<style type="text/css">
	.modal-backdrop {
		z-index: 0; /* Bug bootstrap 3.3.4 & bootstrapDialog */
	}
</style>

<jsp:invoke fragment="css" />
<%
	//Pour inclure des fichiers ou code css
%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/lib/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/lib/bootstrapDialog/js/bootstrap-dialog.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/lib/zebra_Datepicker/js/zebra_datepicker.js"></script>
</head>
<body>
	<!--  MENU  -->
	<nav class="navbar navbar-default" style="margin-bottom:0px;">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">Ergosum</a>
		</div>

		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/">Accueil</a></li>
				<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
						Jouets
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="${pageContext.request.contextPath}/jouet/afficherJouets.htm">Lister</a></li>
						<li><a href="${pageContext.request.contextPath}/jouet/ajouterJouet.htm">Ajouter</a></li>
						<li class="divider"></li>
						<li>
							<form method="post" role="search" action="${pageContext.request.contextPath}/jouet/afficherJouets.htm">
						        <div class="form-group"  style="margin-bottom:2px;">
						        	<div class="input-group">
							        	<span class="input-group-addon" style="padding: 2px" id="basic-addon1">
							        		<button style="padding:0;margin: 0" class="btn-link glyphicon glyphicon-search"></button>
							        	</span>
							         	<input type="text" class="form-control" placeholder="numero" <c:if test="${not empty numeroSearch}">value="${numeroSearch}"</c:if> name="numero" />
							         </div>
						        </div>
					     	</form>
					     	<a href="${pageContext.request.contextPath}/jouet/rechercheAvancee.htm">Recherche avancée</a>
						</li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
						Catalogues
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="${pageContext.request.contextPath}/catalogue/selectionnerAnnee.htm">Par interval</a></li>
						<li><a href="${pageContext.request.contextPath}/catalogue/selectionnerAnnee.htm?cat">Par catégorie</a></li>
					</ul>
				</li>
				<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
				<li><a href="${pageContext.request.contextPath}/dictionnaires.htm">Dictionnaires</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>

	<div class="container-fluid">
		<!-- TITLE -->
		<div class="row">
			<div class="col-md-12">
				<h2>
					<jsp:invoke fragment="title" />
					<%
						//Titre
					%>
				</h2>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<!-- ALERTS -->
				<c:if test="${not empty messSuccess}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${messSuccess}
					</div>
				</c:if>
				<c:if test="${not empty messInfo}">
					<div class="alert alert-info alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${messInfo}
					</div>
				</c:if>
				<c:if test="${not empty messWarning}">
					<div class="alert alert-warning alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${messWarning}
					</div>
				</c:if>
				<c:if test="${not empty messError}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${messError}
					</div>
				</c:if>
			</div>
		</div>

		<!--  CONTENT  -->
		<div class="row">
			<div class="col-md-12">
				<div class="well well-lg">
					<jsp:doBody />
					<%
						//Contenu de la page
					%>
				</div>
			</div>
		</div>
	</div>

	<div class="well well-sm text-center"
		style="font-size: 11px; font-style: italic;">Elodie Mourier --
		Quentin Vanhauteghem</div>

	<jsp:invoke fragment="javascripts" />
	<%
		//Pour include des fichiers javascripts ou code javascript
	%>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.date').Zebra_DatePicker({
				format : 'Y/m/d'
			}) //activer datepicker 
			.css('background-color', "#FFF").css('cursor', 'text');

		});
	</script>
</body>
</html>