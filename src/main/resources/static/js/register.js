$(document).ready(function() {
    $('form').submit(function(event) {
        var form = $(this);
        var inputs = form.find('input[type="text"], input[type="date"], input[type="email"], input[type="password"], select');
        var checkboxes = form.find('input[type="checkbox"]');
        var filled = true;

        inputs.each(function() {
            if ($(this).val() === '') {
                filled = false;
                return false; // Exit the loop if an empty field is found
            }
        });

        checkboxes.each(function() {
            if (!$(this).is(':checked')) {
                filled = false;
                return false; // Exit the loop if a checkbox is not checked
            }
        });

        if (!filled) {
            event.preventDefault(); // Prevent form submission

            // Show warning modal
            $('#warningModal').modal('show');
            return false;
        }
    });
});