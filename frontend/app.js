// 1. Initializare Harta
var map = L.map('map').setView([45.9432, 24.9668], 7);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

// 2. Functia care aduce datele reale din Java
function incarcaFlota() {
    fetch('http://localhost:8080/api/vehicles')
        .then(response => response.json())
        .then(vehicule => {
            // AICI ESTE CHEIA: Iteram prin lista venita din Java
            vehicule.forEach(v => {
                if (v.latitude && v.longitude) {
                    L.marker([v.latitude, v.longitude])
                        .addTo(map)
                        // Folosim v.licensePlate (exact cum e in Java/Swagger)
                        .bindPopup(`
                            <b>${v.licensePlate}</b><br>
                            Status: ${v.status}
                        `);
                }
            });
        })
        .catch(err => console.error("Eroare API:", err));
}

// Pornim incarcarea
incarcaFlota();