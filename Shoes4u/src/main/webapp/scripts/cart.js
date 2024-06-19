document.addEventListener("DOMContentLoaded", function() {
    // Seleziona tutti i pulsanti "Aggiungi al carrello"
    var addToCartButtons = document.querySelectorAll('.product button');

    // Aggiungi un event listener a ciascun pulsante
    addToCartButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            // Ottieni il prodotto associato al pulsante
            var productContainer = button.closest('.product');
            var productName = productContainer.querySelector('h3').innerText;
            var productPrice = productContainer.querySelector('p.price').innerText;
            var productImage = productContainer.querySelector('img').src;

            // Esegui una richiesta AJAX per aggiungere il prodotto al carrello
            // Assumi che ci sia un servlet 'AggiungiAlCarrelloServlet' che gestisce questa operazione
            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'AggiungiAlCarrelloServlet');
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Prodotto aggiunto con successo al carrello
                    alert('Prodotto aggiunto al carrello!');
                } else {
                    // Si è verificato un errore durante l'aggiunta al carrello
                    alert('Errore durante l\'aggiunta al carrello. Si prega di riprovare.');
                }
            };
            xhr.send(JSON.stringify({
                productName: productName,
                productPrice: productPrice,
                productImage: productImage
            }));
        });
    });
});
