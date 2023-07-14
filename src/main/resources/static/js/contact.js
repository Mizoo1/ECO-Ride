function countCharacters(element) {
    var charCountElement = document.getElementById("charCount");
    var message = element.value.trim();
    var charCount = message.length;

    charCountElement.innerText = charCount;
}

AOS.init({
    offset: 200,
    duration: 600,
    easing: 'ease-in-sine',
    delay: 100,
});

var map = L.map('map').setView([53.0421, 8.8384], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
}).addTo(map);

var carIcon = L.icon({
    iconUrl: 'https://i.ibb.co/3r7VRXT/Logo.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

var locations = [
    [53.0421, 8.8384], // Werderstraße 73, 28199 Bremen
    [53.0758, 8.8095], // Neustadtswall 30, 28199 Bremen
    [53.0755, 8.8076], // Bürgermeister-Smidt-Straße, 28195 Bremen
    [53.0475, 8.7894]  // Flughafenallee 10, 28199 Bremen
];

for (var i = 0; i < locations.length; i++) {
    L.marker(locations[i], {icon: carIcon}).addTo(map);
}

// Get user's location
map.locate({setView: true, maxZoom: 16});

function onLocationFound(e) {
    L.marker(e.latlng, {icon: carIcon}).addTo(map);
}

map.on('locationfound', onLocationFound);

var userMarker;
var nearestMarker;
var polyline;

document.getElementById('findNearest').addEventListener('click', function() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        alert("Geolocation is not supported by this browser.");
    }
});

function showPosition(position) {
    var userLocation = new L.LatLng(position.coords.latitude, position.coords.longitude);

    if (userMarker) {
        map.removeLayer(userMarker);
    }

    userMarker = L.marker(userLocation).addTo(map);

    var nearestLocation;
    var shortestDistance = Number.MAX_VALUE;

    for (var i = 0; i < locations.length; i++) {
        var distance = map.distance(userLocation, locations[i]);
        if (distance < shortestDistance) {
            shortestDistance = distance;
            nearestLocation = locations[i];
        }
    }

    if (nearestMarker) {
        map.removeLayer(nearestMarker);
    }

    nearestMarker = L.marker(nearestLocation, {icon: carIcon}).addTo(map);

    if (polyline) {
        map.removeLayer(polyline);
    }

    polyline = L.polyline([userLocation, nearestLocation], {color: 'red'}).addTo(map);

    map.fitBounds([userLocation, nearestLocation]);
}