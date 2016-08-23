
/*
var addUsrButton = document.getElementById("btnAddUsuario");
addUsrButton.addEventListener("click", addUsuario);

function addUsuario(){
	alert("Hello, i am box!");
	
		var id = document.getElementById("btnExcluirUsr1");

}
*/

function excluirUsuario(id) {
	decisao = confirm("Deseja realmente excluir o Usuario?");
	if (decisao){
		if(id != null  && id >= 0)
			//window.location.href="rmUser="+id;
			location.href='/admin/rmuser?id='+id; 
			alert("Você removeu o usuário");
	}
	
}


//Document Ready
$(document).ready(function(){
	//Selecionar somente uma funcao de usuario
	//Codigo especificao para MDL
	$("input:checkbox").on('click', function(element) {
			$.each($('.mdl-js-checkbox'), function (index, element) {
			    element.MaterialCheckbox.uncheck();
			});
			var $box = $(this);
			$box.prop("checked", true);
	});
});

