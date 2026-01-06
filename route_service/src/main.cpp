#include <iostream>
#include <thread>
#include <chrono>

int main() {
    std::cout << "Route Service started..." << std::endl;
    while (true) {
        std::this_thread::sleep_for(std::chrono::seconds(5));
        std::cout << "Waiting for requests..." << std::endl;
    }
    return 0;
}