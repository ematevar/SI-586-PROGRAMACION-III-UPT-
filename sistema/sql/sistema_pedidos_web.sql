-- =====================================================================
-- Laboratorio 04 - Sistema web de pedidos (Spring Boot + MySQL)
-- Ejecutar en HeidiSQL (o: mysql -u root -p < sql/sistema_pedidos_web.sql)
-- =====================================================================

CREATE DATABASE IF NOT EXISTS sistema_pedidos_web;
USE sistema_pedidos_web;

-- Roles disponibles para clasificar permisos.
CREATE TABLE IF NOT EXISTS rol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);

INSERT IGNORE INTO rol (nombre, descripcion) VALUES
('ROLE_ADMIN', 'Administrador del sistema'),
('ROLE_VENDEDOR', 'Atención de pedidos'),
('ROLE_ALMACEN', 'Gestión de inventario'),
('ROLE_CLIENTE', 'Cliente final');

-- Cuentas de usuario y su rol asociado.
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rol_id BIGINT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

-- Categorías que agrupan productos.
CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

-- Productos y su categoría.
CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoria_id BIGINT,
    nombre VARCHAR(150) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio > 0),
    stock INT NOT NULL CHECK (stock >= 0),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- Cabecera de cada pedido.
CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    vendedor_id BIGINT,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(50) DEFAULT 'PENDIENTE',
    FOREIGN KEY (cliente_id) REFERENCES usuario(id),
    FOREIGN KEY (vendedor_id) REFERENCES usuario(id)
);

-- Productos que forman parte de cada pedido.
CREATE TABLE IF NOT EXISTS detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT,
    producto_id BIGINT,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10,2) NOT NULL CHECK (precio_unitario > 0),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- =====================================================================
-- DATOS DE EJEMPLO (opcional, para ver algo en pantalla)
-- Las contraseñas de estos usuarios son solo de demostración y NO se usan
-- para iniciar sesión: la app usa el usuario en memoria admin/admin123.
-- =====================================================================
INSERT INTO categoria (nombre, descripcion) VALUES
('Bebidas', 'Jugos, agua y gaseosas'),
('Abarrotes', 'Productos de consumo diario'),
('Limpieza', 'Productos para el hogar');

INSERT INTO usuario (rol_id, username, password, nombre, email, telefono) VALUES
((SELECT id FROM rol WHERE nombre='ROLE_CLIENTE'), 'mlopez', '{noop}demo', 'María López', 'maria@example.com', '999111222'),
((SELECT id FROM rol WHERE nombre='ROLE_CLIENTE'), 'jperez', '{noop}demo', 'José Pérez', 'jose@example.com', '999333444'),
((SELECT id FROM rol WHERE nombre='ROLE_CLIENTE'), 'atorres', '{noop}demo', 'Ana Torres', 'ana@example.com', '999555666');

INSERT INTO pedido (cliente_id, fecha, total, estado) VALUES
((SELECT id FROM usuario WHERE username='mlopez'),  '2026-10-23 09:15:00',  85.50, 'PENDIENTE'),
((SELECT id FROM usuario WHERE username='jperez'),  '2026-10-23 10:40:00',  42.00, 'APROBADO'),
((SELECT id FROM usuario WHERE username='atorres'), '2026-10-22 16:05:00', 119.90, 'DESPACHADO');
