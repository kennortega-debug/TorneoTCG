CREATE TABLE jugador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE mazo (
    id_mazo BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    descripcion VARCHAR(50),
    fecha_creacion DATE,
    estado VARCHAR(10),
    id_jugador INT NOT NULL,
    CONSTRAINT fk_mazo_jugador FOREIGN KEY (id_jugador) REFERENCES jugador(id)
);

CREATE TABLE carta (
    id_carta BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    descripcion VARCHAR(50),
    rareza VARCHAR(20),
    costo INT
);

CREATE TABLE carta_mazo (
    id_carta_mazo BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    id_mazo BIGINT NOT NULL,
    id_carta BIGINT NOT NULL,
    CONSTRAINT fk_carta_mazo_mazo FOREIGN KEY (id_mazo) REFERENCES mazo(id_mazo),
    CONSTRAINT fk_carta_mazo_carta FOREIGN KEY (id_carta) REFERENCES carta(id_carta)
);
