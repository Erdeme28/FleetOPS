FROM ubuntu:22.04 AS build

RUN apt-get update && apt-get install -y \
    build-essential \
    cmake \
    git \
    libssl-dev \
    wget

WORKDIR /app
RUN wget https://raw.githubusercontent.com/yhirose/cpp-httplib/master/httplib.h

COPY . .
RUN mkdir build && cd build && cmake .. && make

FROM ubuntu:22.04

RUN apt-get update && apt-get install -y \
    libssl-dev \
    curl \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /app/build/route_service .

EXPOSE 8081
CMD ["./route_service"]
