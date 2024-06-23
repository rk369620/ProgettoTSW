
    function validatePaymentForm() {
        var cardNumber = document.getElementById('cardNumber').value.trim();
        var expiryDate = document.getElementById('expiryDate').value.trim();
        var cvv = document.getElementById('cvv').value.trim();

        
        if (cardNumber === '' || expiryDate === '' || cvv === '') {
            alert('Please fill in all payment details.');
            return false; 
        }

       
        var expiryRegex = /^(0[1-9]|1[0-2])\/\d{4}$/;
        if (!expiryRegex.test(expiryDate)) {
            alert('Expiry Date must be in MM/YYYY format.');
            return false;
        }

        
        var cvvRegex = /^[0-9]{3,4}$/;
        if (!cvvRegex.test(cvv)) {
            alert('CVV must be a 3 or 4 digit number.');
            return false;
        }

      
        return true;
    }

