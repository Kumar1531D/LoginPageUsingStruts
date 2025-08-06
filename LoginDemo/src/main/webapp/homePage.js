window.addEventListener('load', isValidUser);
window.addEventListener('DOMContentLoaded', handleUrl);
window.addEventListener('storage',function(e){
	if(e.key === 'logout'){
		window.location.href = 'Login.html';
	}
	
})

function isValidUser() {
	let token1 = localStorage.getItem('jwt');

	if (token1 == null) {
		alert("Please login before entering!");
		window.location.href = 'Login.html';
		return;
	}
	else{
		document.body.style.display = 'block';
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
	
	
	let confirmationForSignout = confirm("Are you sure want to Signout")
	
	if(confirmationForSignout){
		localStorage.removeItem("jwt");
		localStorage.removeItem("userName");
		localStorage.setItem('logout',Date.now())
		window.location.href = "Login.html";
		window.location.replace("Login.html");
	}
})


$(".product-link").on('click',function(e){
	e.preventDefault();
	
	const category = $(this).data("category");
	
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
	
	
	$('#productDisplay').removeClass("hidden");
	$('#content').addClass("hidden");
	
	$("#heading").text(category);
	$("#para").text(`You are selecting ${category}`);
	loadProducts(category);
	
	
}

function showHome(){
	$('#content').removeClass("hidden");
	$('#productDisplay').addClass("hidden");
	$('#product-list').addClass("hidden");
}

function handleUrl(){
	
	let path = window.location.pathname.split('/').pop().toLowerCase();
		
	if (!path || path.toLowerCase() === "home" || path === "homepage.html") {
		showHome();
	} else {
		loadProductPage(path);
	}
	
	
}

function loadProducts(category){
	$.ajax({
		url : `/LoginDemo/products/getProducts?category=${category}`,
		type : 'GET',
		dataType: 'json',
		success : function(products){

			const list = document.getElementById("product-list");
			list.innerHTML = ""; 

			renderProducts(products)

			$('#content').addClass("hidden");
			$('#product-list').removeClass("hidden");
		},
		error: function(err){
			console.error("Failed to load products", err);
		}
	});
}

function renderProducts(products) {
  const listContainer = document.getElementById('product-list');
  const template = document.getElementById('product-template');

  listContainer.innerHTML = ''; 

  products.forEach(product => {
    const clone = template.content.cloneNode(true);

    clone.querySelector('.product-name').textContent = product.productName.trim();
    clone.querySelector('.product-category').textContent = `Category: ${product.category.trim()}`;

    const attrList = clone.querySelector('.product-attributes');
    for (let key in product.attributeValue) {
      const li = document.createElement('li');
      li.textContent = `${key.trim()}: ${product.attributeValue[key].trim()}`;
      attrList.appendChild(li);
    }

    listContainer.appendChild(clone);
  });
}



$(document).ready(function () {
	handleUrl();
});


