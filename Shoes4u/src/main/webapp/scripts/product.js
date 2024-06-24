$(document).ready(function() {
    $('#categoryDropdown').on('change', function() {
        var selectedCategory = $(this).val();

        if (selectedCategory === 'all') {
            $('.product-card').show();
        } else {
            $('.product-card').hide();
            $('.product-card[data-category="' + selectedCategory + '"]').show();
        }
    });
});
