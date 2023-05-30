// Das Event 'click' wird dem Submit-Button zugeordnet
document.querySelector('.password-btn').addEventListener('click', function() {
    var passwordInput = document.getElementById('passwordInput').value;
    var password = "0000"; // passwort

    if (passwordInput === password) {
        document.querySelector('.password-form').classList.add('hidden');
        document.querySelector('table').classList.remove('hidden');
    } else {
        //wird eine Meldung angezeigt bei falsches Passwort
        alert('Invalid password!');
    }
});