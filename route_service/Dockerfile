FROM ubuntu:22.04 AS build
RUN apt-get update && apt-get install -y build-essential cmake
WORKDIR /app
COPY . .
RUN mkdir build && cd build && cmake .. && make

FROM ubuntu:22.04
WORKDIR /app
COPY --from=build /app/build/route_service .
CMD ["./route_service"]