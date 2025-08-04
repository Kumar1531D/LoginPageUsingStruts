
$("#loginForm").submit( function(e){
	
	e.preventDefault();
	
	$.ajax({
		url : 'checkUser',
		type : "POST",
		data : {
				userName : $("#userName").val(),
				password : $("#password").val()
				},
		dataype : 'json',
		
		success : function(data){
			if(data.status==="success"){
				localStorage.setItem("jwt",data.token);
				localStorage.setItem("userName",data.userName);
				window.location.href = "homePage.html"; 
			}
			else{
				alert("Invalid Login details");
			}
		}
	});
});

$("#signupLink").on("click", function(e) {
	loadSignup(e);
});

$("#loginLink").on("click", function(e) {
	loadLogin(e);
});


function loadSignup(e){
	
	  	if(e) e.preventDefault(); 
	    $('#loginDiv').addClass("hidden")
	    $('#signupDiv').removeClass("hidden")
	    history.pushState(null,null,'signup');
	
}

function loadLogin(e){
	
	    if(e) e.preventDefault(); 
	    $('#signupDiv').addClass("hidden")
	    $('#loginDiv').removeClass("hidden")
	    history.pushState(null,null,'login');
	
}

function loadPageFromUrl() {
    const path = window.location.pathname;
    
    if (path.includes("signup")) {
        loadSignup();
    } else if (path.includes("login") || path.includes("Login.html")) {
        loadLogin();
    }
}

window.onpopstate = function(event) {
    loadPageFromUrl();
};

window.addEventListener('load',checkIfLogin);

function checkIfLogin(){
	if(localStorage.getItem("jwt")!=null){
		window.location.href = "homePage.html";
	}
	
}



