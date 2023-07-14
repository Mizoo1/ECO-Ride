function submitForm() {
    // Überprüfen, ob alle erforderlichen Felder ausgefüllt sind
    var zeit = document.getElementById("zeit").value;
    var datum = document.getElementById("datum").value;
    var abholort = document.getElementById("abholort").value;
    var ort = document.getElementById("ort").value;
    var fahrzeug = document.getElementById("fahrzeug").value;

    if (zeit && datum && abholort && ort && fahrzeug) {
        // Schaltfläche deaktivieren
        document.getElementById("reservierenButton").disabled = true;

        var formData = $("#myForm").serializeArray();
        $.ajax({
            type: "POST",
            url: "/reservierung",
            data: formData,
            success: function(data) {
                // Zeigt das Bootstrap-Modal mit Erfolgsmeldung an
                $('#successModal').modal('show');
                location.reload();
            },
            complete: function() {
                // Schaltfläche aktivieren
                document.getElementById("reservierenButton").disabled = false;
            }
        });
    } else {
        alert("Bitte füllen Sie alle Felder aus, bevor Sie die Reservierung vornehmen.");
    }
}
function showFieldsByVehicleType() {
    var selectedVehicle = document.getElementById("fahrzeug").value;


    document.getElementById("lkwFields").style.display = "none";
    document.getElementById("pkwFields").style.display = "none";
    document.getElementById("motoradFields").style.display = "none";
    document.getElementById("anhaengerFields").style.display = "none";

    if (selectedVehicle === "LKW") {
        document.getElementById("lkwFields").style.display = "block";
    } else if (selectedVehicle === "PKW") {
        document.getElementById("pkwFields").style.display = "block";
    } else if (selectedVehicle === "MOTORAD") {
        document.getElementById("motoradFields").style.display = "block";
    } else if (selectedVehicle === "ANHÄNGER") {
        document.getElementById("anhaengerFields").style.display = "block";
    }
}
function populateModels() {
    var brandSelect = document.getElementById("marke");
    var doorsSelect = document.getElementById("anzahlDerTuren");
    var modelSelect = document.getElementById("modelPkw");
    var fuelSelect = document.getElementById("sprit");

    modelSelect.innerHTML = "<option value='' disabled selected>Bitte wählen</option>";

    // musse später vom mitarbeiter im seite bearbeitet werden
    var models = {
        "Audi": {
            "2": {
                "Diesel": ["Audi A1", "Audi A3"],
                "Benzin": ["Audi A2", "Audi A4"],
                "Gas": ["Audi A3 g-tron", "Audi A5 g-tron"],
                "Elektro": ["Audi e-tron", "Audi Q4 e-tron"]
            },
            "4": {
                "Diesel": ["Audi A5", "Audi A6"],
                "Benzin": ["Audi A4", "Audi A7"],
                "Gas": ["Audi A6 g-tron", "Audi A8 g-tron"],
                "Elektro": ["Audi e-tron GT", "Audi Q7 e-tron"]
            }
        },
        "BMW": {
            "2": {
                "Diesel": ["BMW 1 Series", "BMW 2 Series"],
                "Benzin": ["BMW 2 Series", "BMW 3 Series"],
                "Gas": ["BMW 2 Series g-tron", "BMW 5 Series g-tron"],
                "Elektro": ["BMW i3", "BMW i4"]
            },
            "4": {
                "Diesel": ["BMW 3 Series", "BMW 4 Series"],
                "Benzin": ["BMW 5 Series", "BMW 7 Series"],
                "Gas": ["BMW 5 Series g-tron", "BMW X5 g-tron"],
                "Elektro": ["BMW i4", "BMW iX3"]
            }
        },
        "Mercedes-Benz": {
            "2": {
                "Diesel": ["Mercedes-Benz A-Class", "Mercedes-Benz C-Class"],
                "Benzin": ["Mercedes-Benz CLA", "Mercedes-Benz E-Class"],
                "Gas": ["Mercedes-Benz GLC g-tron", "Mercedes-Benz GLA g-tron"],
                "Elektro": ["Mercedes-Benz EQA", "Mercedes-Benz EQC"]
            },
            "4": {
                "Diesel": ["Mercedes-Benz C-Class", "Mercedes-Benz E-Class"],
                "Benzin": ["Mercedes-Benz E-Class", "Mercedes-Benz S-Class"],
                "Gas": ["Mercedes-Benz GLC g-tron", "Mercedes-Benz GLE g-tron"],
                "Elektro": ["Mercedes-Benz EQC", "Mercedes-Benz EQS"]
            }
        }
    };

    var selectedBrand = brandSelect.value;
    var selectedDoors = doorsSelect.value;
    var selectedFuel = fuelSelect.value;
    var brandModels = models[selectedBrand];

    if (brandModels && brandModels[selectedDoors] && brandModels[selectedDoors][selectedFuel]) {
        brandModels[selectedDoors][selectedFuel].forEach(function (model) {
            var option = document.createElement("option");
            option.value = model;
            option.text = model;
            modelSelect.appendChild(option);
        });
    }
}
function populateLKWModels() {
    var lkwSizeSelect = document.getElementById("lkwSize");
    var modelLkwSelect = document.getElementById("modelLkw");

    modelLkwSelect.innerHTML = "<option value='' disabled selected>Bitte wählen</option>";

    var lkwModels = {
        "Klein": ["LKW Modell 1", "LKW Modell 2", "LKW Modell 3"],
        "Mittel": ["LKW Modell 4", "LKW Modell 5", "LKW Modell 6"],
        "Groß": ["LKW Modell 7", "LKW Modell 8", "LKW Modell 9"]
    };

    var selectedSize = lkwSizeSelect.value;
    var sizeModels = lkwModels[selectedSize];

    if (sizeModels) {
        sizeModels.forEach(function (model) {
            var option = document.createElement("option");
            option.value = model;
            option.text = model;
            modelLkwSelect.appendChild(option);
        });
    }
}
function populateMotoradModels() {
    var motoradSizeSelect = document.getElementById("motoradSize");
    var motoradMarkeSelect = document.getElementById("motoradMarke");
    var modelMotoradSelect = document.getElementById("modelMotorad");

    modelMotoradSelect.innerHTML = "<option value='' disabled selected>Bitte wählen</option>";

    var motoradModels = {
        "Small": {
            "Harley-Davidson": ["Modell 1", "Modell 2", "Modell 3"],
            "Honda": ["Modell 4", "Modell 5", "Modell 6"],
            "Yamaha": ["Modell 7", "Modell 8", "Modell 9"]
        },
        "Medium": {
            "Harley-Davidson": ["Modell 10", "Modell 11", "Modell 12"],
            "Honda": ["Modell 13", "Modell 14", "Modell 15"],
            "Yamaha": ["Modell 16", "Modell 17", "Modell 18"]
        },
        "Large": {
            "Harley-Davidson": ["Modell 19", "Modell 20", "Modell 21"],
            "Honda": ["Modell 22", "Modell 23", "Modell 24"],
            "Yamaha": ["Modell 25", "Modell 26", "Modell 27"]
        }
    };

    var selectedSize = motoradSizeSelect.value;
    var selectedMarke = motoradMarkeSelect.value;
    var sizeModels = motoradModels[selectedSize];
    var brandModels = sizeModels ? sizeModels[selectedMarke] : null;

    if (brandModels) {
        brandModels.forEach(function (model) {
            var option = document.createElement("option");
            option.value = model;
            option.text = model;
            modelMotoradSelect.appendChild(option);
        });
    }
}
document.addEventListener('DOMContentLoaded', function() {
    var stornierenButton = document.getElementById('stornierenButton');

    stornierenButton.addEventListener('click', function(event) {

        event.preventDefault();

        // Deaktiviere die Schaltfläche nach dem Klicken
        stornierenButton.disabled = true;

        // Sende das Formular
        var form = stornierenButton.closest('form');
        form.submit();
    });
});