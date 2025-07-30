
$("#signupForm").submit(function(e){
	e.preventDefault();
	
	$.post("signupUser",{
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