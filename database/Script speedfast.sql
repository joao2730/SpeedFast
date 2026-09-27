CREATE DATABASE IF NOT EXISTS speedfast_db;

USE speedfast_db;

CREATE TABLE repartidor (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    distancia_km DECIMAL(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    
    FOREIGN KEY (id_pedido)
        REFERENCES pedido(id),
        
	FOREIGN KEY (id_repartidor)
        REFERENCES repartidor(id)
);