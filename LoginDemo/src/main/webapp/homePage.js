window.addEventListener('load', loadName);
window.addEventListener('DOMContentLoaded', handleUrl);
window.addEventListener('storage',function(e){
	if(e.key === 'logout'){
		window.location.href = 'Login.html';
	}	
})

function loadName(){
	document.getElementById("nameDisplay").textContent = localStorage.getItem('userName');
}

function isValidUser() {
	let token1 = localStorage.getItem('jwt');

	if (token1 == null) {
		alert("Please login before entering!");
		window.location.href = 'login';
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
		localStorage.removeItem("userName");
		localStorage.setItem('logout',Date.now())
		$.ajax({
            url: 'logout',
            type: 'POST',
            success : function(){
				window.location.replace("login");
        		window.location.href = 'login';
			},
			error : function(){
				alert("something is wrong")
			}
			
        });
        
	}
})


$(document).on('click', '.category-link', function(e) {
	e.preventDefault();

	const category = $(this).data("category");

	history.pushState(null, null, category);
	loadProductPage(category);
});


$("#homeButton").on('click',function(e){
	e.preventDefault();
	history.pushState(null,null,'home');
	showHome();
})

$("#categoryButton").on('click',function(e){
	e.preventDefault();
	history.pushState(null,null,'category');
	
	loadCategory();
})


window.onpopstate = function(event) {
    handleUrl();
};

function loadProductPage(category){
	$('#productDisplay').removeClass("hidden");
	$('#content').addClass("hidden");
	
	$("#heading").text(category);
	$("#para").text(`You are selecting ${category}`);
	console.log("in loadproductpage")
	loadProducts(category);
}

function showHome(){
	$('#content').removeClass("hidden");
	$('#productDisplay').addClass("hidden");
	$('#product-list').addClass("hidden");
	$('#category-links').addClass('hidden');
}

function handleUrl(){
	
	let path = window.location.pathname.split('/').pop();
		
	if (!path || path.toLowerCase() === "home" || path === "homepage.html") {
		showHome();
	}
	else if(path === "category"){
		loadCategory();
	} 
	else {
		loadCategory();
		loadProductPage(path);
	}
	
}

function loadProducts(category){
	console.log("in loadproducts "+category)
	$.ajax({
		url : `/LoginDemo/products/getProducts?category=${category}`,
		type : 'GET',
		dataType: 'json',
		success : function(products){

			const list = document.getElementById("product-list");
			list.innerHTML = ""; 

			renderProducts(products)
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

function loadCategory(){
	const links = document.getElementById("category-links");
	links.innerHTML = ""; 
	
	$.ajax({
      url: "/LoginDemo/products/getCategories",
      type: "GET",
      dataType: "json",
      success: function (data) {
        var categoryDiv = $("#category-links");
        
        console.log(data);
        
        data.forEach(function (category) {
          var categoryName = category.trim();
          var categoryLink = $("<a>")
            .text(categoryName)
            .attr("href", "#")
            .attr("data-category", categoryName)
    		.addClass("category-link") 
            .css({
              display: "block",
              color: "blue",
              textDecoration: "underline",
              cursor: "pointer"
            });
          categoryDiv.append(categoryLink);
        });
      },
      error: function () {
        $("#category-links").html("<p>Error loading categories.</p>");
      }
    });
    
    $('#content').addClass("hidden");
	$('#productDisplay').addClass("hidden");
	$('#product-list').addClass("hidden");
	$('#category-links').removeClass('hidden');
}

//$(document).ready(function () {
//	handleUrl();
//});


