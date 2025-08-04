
$("#signupForm").submit(function(e){
	e.preventDefault();
	
	$.post("/LoginDemo/signupUser",{
		userName : $("#userName").val(),
		password : $("#password").val(),
		email : $("#email").val()
	},function(data){
		
		if(data.includes("success")){
			alert("Signup successful :)");
			window.location.href = "Login.html";
		}
		else{
			alert("Something is wrong! Try Again!");
		}
		
	})
})


$("#loginLink").on("click", function(e) {
    e.preventDefault(); 
    $("#loginBody").load('Login.html', function() {
        history.pushState(null, null, 'login');
    });
});