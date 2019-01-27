/**
 * 
 */

$(document).ready(
	$('.table .eBtn').on('click',function(event){
		event.preventDefault();
		var href = $ (this).attr('href');
		$.get(href,function(filme, status)){
			$('.myForm #idFilme').value(filme.idFime)
		}
		}
		$(('.myForme #myModelTeste').modal();
		
	}
);