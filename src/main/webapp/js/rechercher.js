$(document).ready(function() {
	//Fenetre de confirmation avant suppression
	$(".btnSuppr").click(function() {
		var btn = $(this);
		
		var num = $(this).parent().parent().find('.id').text(); //numéro stage
		var libelle = $(this).parent().parent().find('.libelle').text(); //libelle stage

		//Dialog 
		BootstrapDialog.confirm({
			'title' : 'Êtes vous sûr de vouloir supprimer le stage n°<b>'+num+'</b> :',
			'message' : '<div class="text-center">'+libelle+'</div>',
			callback :  function(result){
	            if(result) {
	            	window.location.href = btn.attr('href'); //utiliser l'adresse du lien 
	            }
			}
        }); 
		
		return false; //annulé envoi formulaire 
	});
});