CREATE TABLE region (
    id_region BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE comuna (
    id_comuna BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_region BIGINT NOT NULL,
    CONSTRAINT fk_comuna_region FOREIGN KEY (id_region) REFERENCES region(id_region)
);

CREATE TABLE organizador (
    id_organizador BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    email VARCHAR(100),
    telefono VARCHAR(20),
    cargo VARCHAR(100)
);

CREATE TABLE recinto (
    id_recinto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    capacidad INT,
    id_comuna BIGINT NOT NULL,
    CONSTRAINT fk_recinto_comuna FOREIGN KEY (id_comuna) REFERENCES comuna(id_comuna)
);
