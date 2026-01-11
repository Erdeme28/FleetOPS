#include "../httplib.h"
#include <iostream>
#include <string>

std::string calculate_route(std::string start, std::string end) {
    return "{ \"distanceKm\": 15.5, \"estimatedTimeMinutes\": 25.0, \"waypoints\": \"45.65,25.60;45.66,25.61\" }";
}

int main() {
    httplib::Server svr;

    std::cout << "Route Service (C++) starting on port 8081..." << std::endl;

    svr.Get("/health", [](const httplib::Request&, httplib::Response& res) {
        res.set_content("{\"status\":\"UP\"}", "application/json");
        res.status = 200;
    });

    // /calculate
    svr.Get("/calculate", [](const httplib::Request& req, httplib::Response& res) {
        std::string start = req.has_param("start") ? req.get_param_value("start") : "A";
        std::string end   = req.has_param("end")   ? req.get_param_value("end")   : "B";

        std::cout << "Calculating route from " << start << " to " << end << std::endl;

        res.set_content(calculate_route(start, end), "application/json");
    });

    svr.listen("0.0.0.0", 8081);
    return 0;
}
