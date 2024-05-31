// login.js
document.addEventListener("DOMContentLoaded", function() {
    var username = document.getElementById("username");
    var password = document.getElementById("password");

    username.addEventListener("blur", validateUsername);
    password.addEventListener("blur", validatePassword);

    username.addEventListener("focus", clearError);
    password.addEventListener("focus", clearError);
});

function validateUsername() {
    var username = document.getElementById("username").value;
    var error = document.getElementById("username-error");

    if (username.trim() === "") {
        error.textContent = "Username is required.";
        return false;
    } else {
        error.textContent = "";
        return true;
    }
}

function validatePassword() {
    var password = document.getElementById("password").value;
    var error = document.getElementById("password-error");

    if (password.trim() === "") {
        error.textContent = "Password is required.";
        return false;
    } else {
        error.textContent = "";
        return true;
    }
}

function clearError(event) {
    var errorElement = document.getElementById(event.target.id + "-error");
    errorElement.textContent = "";
}

function validateForm() {
    var isValidUsername = validateUsername();
    var isValidPassword = validatePassword();

    return isValidUsername && isValidPassword;
}
