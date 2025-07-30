
$("#loginForm").submit( function(e){
	
	e.preventDefault();
	
	$.post("checkUser",{
		
		userName : $("#userName").val(),
		password : $("#password").val()
		
	}, function(data){
		if(data.includes("success")){
			console.log("success msg receives in the js file")
			window.location.href = "NewFile.jsp"; 
		}
		else{
			alert("Invalid Login details");
		}
		
	});
	
});



