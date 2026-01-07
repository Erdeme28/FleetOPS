FROM ubuntu:22.04 AS build

# Instalam unelte + OpenSSL (necesar pt librarii web)
RUN apt-get update && apt-get install -y \
    build-essential \
    cmake \
    git \
    libssl-dev \
    wget

WORKDIR /app

# Descarcam libraria httplib.h direct de pe GitHub (single header file)
RUN wget https://raw.githubusercontent.com/yhirose/cpp-httplib/master/httplib.h

COPY . .

RUN mkdir build && cd build && \
    cmake .. && \
    make

# ETAPA DE RUNTIME
FROM ubuntu:22.04

# Instalam runtime-ul de SSL
RUN apt-get update && apt-get install -y libssl-dev

WORKDIR /app
COPY --from=build /app/build/route_service .

EXPOSE 8081

CMD ["./route_service"]