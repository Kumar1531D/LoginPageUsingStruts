
$("#loginForm").submit( function(e){
	
	e.preventDefault();
	
	const $form = $(this);
	
	$.ajax({
		url : 'checkUser',
		type : "POST",
		data : {
				userName : $form.find("input[name='userName']").val(),
				password : $form.find("input[name='password']").val()
				},
		dataype : 'json',
		
		success : function(data){
			if(data.status==="success"){
				//localStorage.setItem("jwt",data.token);
				localStorage.setItem("userName",data.userName);
				window.location.href = "home"; 
			}
			else{
				alert("Invalid Login details");
			}
		}
	});
});

$("#signupForm").submit(function(e){
	e.preventDefault();
	
	const $form = $(this);
	
	$.post("/LoginDemo/signupUser",{
		userName : $form.find("input[name='userName']").val(),
		password : $form.find("input[name='password']").val(),
		email : $form.find("input[name='email']").val()
	},function(data){
		
		if(data.includes("success")){
			alert("Signup successful ")
			loadLogin();
		}
		else if(data.includes("emailError")){
			alert("Email already exists!")
		}
		else if(data.includes("userNameError")){
			alert("Username already exists!")
		}
		else{
			alert("Something is wrong! Try Again!");
		}
		
	})
})

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

//window.addEventListener('load',checkIfLogin);

function checkIfLogin(){
	if(localStorage.getItem("jwt")!=null){
		window.location.href = "home";
	}
	
}



