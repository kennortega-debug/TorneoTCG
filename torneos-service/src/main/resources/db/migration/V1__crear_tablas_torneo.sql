CREATE TABLE torneo (
   id_torneo BIGINT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(50) NOT NULL,
   fecha_inicio DATE NOT NULL,
   fecha_fin DATE NOT NULL,
   estado VARCHAR(255) NOT NULL,
   id_recinto BIGINT NOT NULL
);

CREATE TABLE ronda (
    id_ronda BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_ronda INT NOT NULL,
    fecha DATE NOT NULL,
    id_torneo BIGINT NOT NULL,
    CONSTRAINT fk_ronda_torneo FOREIGN KEY (id_torneo) REFERENCES torneo(id_torneo)
);

CREATE TABLE participacion (
    id_participacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_jugador INT NOT NULL,
    id_torneo BIGINT NOT NULL,
    ronda_inscripcion INT,
    CONSTRAINT fk_participacion_torneo FOREIGN KEY (id_torneo) REFERENCES torneo(id_torneo)
);

CREATE TABLE partida (
    id_partida BIGINT AUTO_INCREMENT PRIMARY KEY,
    mesa VARCHAR(10) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    id_ronda BIGINT NOT NULL,
    cantidad_jugadores INT NOT NULL,
    id_jugador1 INT NOT NULL,
    id_jugador2 INT NOT NULL,
    id_jugador3 INT NOT NULL,
    id_jugador4 INT,
    id_jugador5 INT,
    CONSTRAINT fk_partida_ronda FOREIGN KEY (id_ronda) REFERENCES ronda(id_ronda)
);

CREATE TABLE resultado (
    id_resultado BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_partida BIGINT NOT NULL UNIQUE,
    id_ganador INT NOT NULL,
    puntaje_jugador1 INT,
    puntaje_jugador2 INT,
    puntaje_jugador3 INT,
    puntaje_jugador4 INT,
    puntaje_jugador5 INT,
    posicion_jugador1 INT,
    posicion_jugador2 INT,
    posicion_jugador3 INT,
    posicion_jugador4 INT,
    posicion_jugador5 INT,
    CONSTRAINT fk_resultado_partida FOREIGN KEY (id_partida) REFERENCES partida(id_partida)
);
