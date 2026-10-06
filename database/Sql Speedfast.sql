CREATE DATABASE IF NOT EXISTS speedfast_db;

USE speedfast_db;

-- Tabla de repartidores
CREATE TABLE repartidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla de pedidos
CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(100) NOT NULL,
    tipo ENUM('COMIDA', 'ENCOMIENDA', 'EXPRESS') NOT NULL,
    estado ENUM('PENDIENTE', 'EN_REPARTO', 'ENTREGADO')
        NOT NULL DEFAULT 'PENDIENTE'
);

-- Tabla de entregas
CREATE TABLE entregas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_entrega_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES pedidos(id),

    CONSTRAINT fk_entrega_repartidor
        FOREIGN KEY (id_repartidor)
        REFERENCES repartidores(id)
);
