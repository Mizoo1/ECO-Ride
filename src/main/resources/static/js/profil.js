document.getElementById('saveButton').addEventListener('click', saveChanges);

function saveChanges() {
    var titel = document.getElementById('titelInput').value;
    var userName = document.getElementById('userNameInput').value;
    var email = document.getElementById('emailInput').value;
    var adresse = document.getElementById('adresseInput').value;
    var plz = document.getElementById('plzInput').value;
    var stadt = document.getElementById('stadtInput').value;
    var telefonnummer = document.getElementById('telefonnummerInput').value;
    var fuehrerscheinnummer = document.getElementById('fuehrerscheinnummerInput').value;
    var ablaufdatum = document.getElementById('ablaufdatumInput').value;
    var tarif = document.getElementById('tarifInput').value;
    var userId = document.getElementById('id').textContent;

    // AJAX-Aufruf oder andere Methoden, um die Daten zu speichern
    $.ajax({
        url: '/updateUser/' + userId,
        type: 'PUT',
        data: JSON.stringify({
            titel: titel,
            userName: userName,
            email: email,
            adresse: adresse,
            plz: plz,
            stadt: stadt,
            telefonnummer: telefonnummer,
            fuehrerscheinnummer: fuehrerscheinnummer,
            ablaufdatum: ablaufdatum,
            tarif: tarif
        }),
        contentType: 'application/json',
        success: function (data) {
            // Erfolgsbehandlung
            // Aktualisiere die angezeigten Benutzerdaten
            document.getElementById('titel').innerText = titel;
            document.getElementById('userName').innerText = userName;
            document.getElementById('email').innerText = email;
            document.getElementById('adresse').innerText = adresse;
            document.getElementById('plz').innerText = plz;
            document.getElementById('stadt').innerText = stadt;
            document.getElementById('telefonnummer').innerText = telefonnummer;
            document.getElementById('fuehrerscheinnummer').innerText = fuehrerscheinnummer;
            document.getElementById('ablaufdatum').innerText = ablaufdatum;
            document.getElementById('tarif').innerText = tarif;

            $('#editModal').modal('hide');
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
}

document.getElementById('confirmDeleteButton').addEventListener('click', deleteAccount);

function deleteAccount() {
    var userId = document.getElementById('id').textContent;
    $.ajax({
        url: '/deleteUser/' + userId,
        type: 'DELETE',
        success: function (data) {
            window.location.href = "/login";
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
}