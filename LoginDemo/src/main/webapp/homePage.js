window.addEventListener('load', isValidUser);

function isValidUser() {
	let token1 = localStorage.getItem('jwt');

	if (token1 == null) {
		alert("Please login before entering!");
		window.location.href = 'Login.html';
		return;
	}

	$.ajax({
		url: 'authenticate',
		type: 'POST',
		data: {
			token: token1
		},
		dataType: 'JSON',
		success: function(data) {
			if (data.status === 'error') {
				alert("Invalid Token");
				window.location.href = 'Login.html';
			}
			else{
				document.getElementById("nameDisplay").textContent = localStorage.getItem('userName');
			}
			
		},
		error: function() {
			alert("Server error. Please login again.");
			localStorage.removeItem('jwt');
			window.location.href = 'Login.html';
		}
	});
}

$("#signoutBtn").on('click',function(e){
	e.preventDefault();
	
	localStorage.removeItem("jwt");
	
	alert("You are being signing out!")
	
	window.location.href = "Login.html";
})


$(".product-link").on('click',function(e){
	e.preventDefault();
	
	const category = $(this).data("category");
	
	$('#productDisplay').removeClass("hidden");
	$('#content').addClass("hidden");
	history.pushState(null,null,category);
	loadProductPage(category)
})

$("#homeButton").on('click',function(e){
	e.preventDefault();
	history.pushState(null,null,'home');
	showHome();
	
})

window.onpopstate = function(event) {
    handleUrl();
};

function loadProductPage(category){
	
	$("#heading").text(category);
	$("#para").text(`You are selecting ${category}`);
	
	
}

function showHome(){
	$('#content').removeClass("hidden");
	$('#productDisplay').addClass("hidden");
}

function handleUrl(){
	
	let path = window.location.pathname.split('/').pop();
	
	if (!path || path.toLowerCase() === "home" || path.toLowerCase() === "homePage.html") {
		showHome();
	} else {
		loadProductPage(path);
	}
	
}

$(document).ready(function () {
	const path = window.location.pathname.split("/").pop();
	if (path && path !== "LoginDemo") {
		loadProductPage(path);
	}
});


