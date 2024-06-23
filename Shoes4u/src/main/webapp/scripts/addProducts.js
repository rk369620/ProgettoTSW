function validateForm() {
    var productName = document.getElementById("productName").value.trim();
    var brand = document.getElementById("brand").value.trim();
    var price = document.getElementById("price").value.trim();
    var availability = document.getElementById("availability").value.trim();
    var category = document.getElementById("category").value.trim();
    var image = document.getElementById("image").value.trim();

    
    if (productName === "" || brand === "" || price === "" || availability === "" || category === "" || image === "") {
        alert("Please fill in all fields.");
        return false;
    }

 
    if (isNaN(price) || parseFloat(price) <= 0) {
        alert("Please enter a valid positive price.");
        return false;
    }

    
    if (isNaN(availability) || parseInt(availability) < 0) {
        alert("Please enter a valid non-negative availability.");
        return false;
    }

    

    return true; 
}
