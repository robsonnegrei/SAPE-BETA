
/*
var addUsrButton = document.getElementById("btnAddUsuario");
addUsrButton.addEventListener("click", addUsuario);

function addUsuario(){
	alert("Hello, i am box!");
	
		var id = document.getElementById("btnExcluirUsr1");

}
*/

function excluirSchool(id) {
	decisao = confirm("Deseja realmente excluir essa Escola?");
	if (decisao){
		if(id != null)
			window.location.href="rmSchool?escola="+id;
			alert("Você removeu a escola");
	}
}