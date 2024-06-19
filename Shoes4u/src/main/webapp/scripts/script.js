// script.js

$(document).ready(function() {
    $('#categorySelector').change(function() {
        var selectedCategory = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'ProductServlet',
            data: { category: selectedCategory },
            success: function(data) {
                $('#productsContainer').html(data);
            },
            error: function(xhr, status, error) {
                console.error('Errore durante la richiesta AJAX:', status, error);
                alert('Errore durante il recupero dei prodotti.');
            }
        });
    });
});


