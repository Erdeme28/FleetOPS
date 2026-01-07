#include "../httplib.h" // Libraria descarcata in Dockerfile
#include <iostream>
#include <string>

// Functie simpla care simuleaza calculul complex
// In realitate, aici ai folosi algoritmi gen Dijkstra sau A*
std::string calculeaza_ruta(std::string start, std::string end) {
    // Simulam un raspuns JSON
    // Returnam distanta 15km si timp 25 minute hardcodat momentan
    // Dar logic, primim Start si End, deci am putea face calcule pe baza lor
    return "{ \"distanceKm\": 15.5, \"estimatedTimeMinutes\": 25.0, \"waypoints\": \"45.65,25.60;45.66,25.61\" }";
}

int main() {
    httplib::Server svr;

    std::cout << "Route Service (C++) starting on port 8081..." << std::endl;

    // Definim endpoint-ul GET /calculate
    svr.Get("/calculate", [](const httplib::Request& req, httplib::Response& res) {

        // Citim parametrii din URL (ex: ?start=Gara&end=Centru)
        std::string start = "A";
        std::string end = "B";

        if (req.has_param("start")) start = req.get_param_value("start");
        if (req.has_param("end")) end = req.get_param_value("end");

        std::cout << "Calculating route from " << start << " to " << end << std::endl;

        // Generam raspunsul JSON
        std::string json_response = calculeaza_ruta(start, end);

        res.set_content(json_response, "application/json");
    });

    // Pornim serverul
    svr.listen("0.0.0.0", 8081);

    return 0;
}