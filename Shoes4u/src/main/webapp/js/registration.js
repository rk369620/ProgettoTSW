function validateForm() {
    var firstName = document.getElementById("first-name").value;
    var lastName = document.getElementById("last-name").value;
    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;

    // Reset error messages
    document.getElementById("first-name-error").innerText = "";
    document.getElementById("last-name-error").innerText = "";
    document.getElementById("username-error").innerText = "";
    document.getElementById("email-error").innerText = "";
    document.getElementById("password-error").innerText = "";
    document.getElementById("confirm-password-error").innerText = "";

    var isValid = true;

    // Validate First Name
    if (firstName === "") {
        document.getElementById("first-name-error").innerText = "Please enter your first name.";
        isValid = false;
    }

    // Validate Last Name
    if (lastName === "") {
        document.getElementById("last-name-error").innerText = "Please enter your last name.";
        isValid = false;
    }

    // Validate Username
    if (username === "") {
        document.getElementById("username-error").innerText = "Please enter a username.";
        isValid = false;
    }

    // Validate Email
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        document.getElementById("email-error").innerText = "Please enter a valid email address.";
        isValid = false;
    }

    // Validate Password
    if (password === "") {
        document.getElementById("password-error").innerText = "Please enter a password.";
        isValid = false;
    }

    // Validate Confirm Password
    if (confirmPassword === "") {
        document.getElementById("confirm-password-error").innerText = "Please confirm your password.";
        isValid = false;
    } else if (confirmPassword !== password) {
        document.getElementById("confirm-password-error").innerText = "Passwords do not match.";
        isValid = false;
    }

    return isValid;
}
