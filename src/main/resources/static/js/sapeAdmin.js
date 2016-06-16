
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
		if(id != null)
			window.location.href="rmUser?user="+id;
			alert("Você removeu o usuário");
	}
}