// 1. Inițializare Hartă (Centrată pe Brașov/România de exemplu)
var map = L.map('map').setView([45.65, 25.60], 13);

// Adăugăm stratul vizual (OpenStreetMap - gratuit)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

// 2. Adăugăm un marker de test (Vehicul simulat)
var vehicleMarker = L.marker([45.65, 25.60]).addTo(map)
    .bindPopup('Vehicul #1')
    .openPopup();

// 3. Funcția pentru buton
function trimiteComanda() {
    const start = document.getElementById('start').value;
    const end = document.getElementById('end').value;

    // Aici vom face apelul catre Spring Boot mai tarziu
    console.log("Comanda trimisa: " + start + " -> " + end);
    document.getElementById('status-log').innerHTML += `<p>Calculare ruta: ${start} -> ${end}</p>`;
}

// 4. Configurare WebSocket (Schelet)
// const socket = new WebSocket("ws://localhost:8080/ws-fleet");
// socket.onmessage = function(event) {
//    // Cand primim coordonate noi de la Java, mutam markerul
//    // var data = JSON.parse(event.data);
//    // vehicleMarker.setLatLng([data.lat, data.lng]);
// };