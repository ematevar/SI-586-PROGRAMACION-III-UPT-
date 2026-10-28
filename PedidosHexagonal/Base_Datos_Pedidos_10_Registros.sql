-- ============================================================
-- BASE DE DATOS: Pedidos
-- Aplicativo: PedidosHexagonal (Spring Boot 3 + Spring Data JPA)
-- Incluye 10 registros de prueba en cada tabla de negocio.
-- Credencial admin: la crea AdminInicial.java en la guía:
-- usuario: admin | contraseña: Admin123*
-- ============================================================

CREATE DATABASE IF NOT EXISTS Pedidos
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE Pedidos;

-- Tablas compatibles con las entidades de la guía.
-- Si las tablas ya existen, CREATE TABLE IF NOT EXISTS las conserva.

CREATE TABLE IF NOT EXISTS usuarios_app (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    rol VARCHAR(30) NOT NULL DEFAULT 'EMPLEADO',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS empleados_app (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(150),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS clientes_app (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200),
    correo VARCHAR(150),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Datos de prueba: 10 empleados.
INSERT INTO empleados_app (nombres, apellidos, documento, correo) VALUES
('Ana María', 'Torres Díaz', 'EMP10001', 'ana.torres@demo.com'),
('Luis Alberto', 'García Rojas', 'EMP10002', 'luis.garcia@demo.com'),
('María Fernanda', 'López Ruiz', 'EMP10003', 'maria.lopez@demo.com'),
('Carlos Eduardo', 'Mendoza Silva', 'EMP10004', 'carlos.mendoza@demo.com'),
('Rosa Elena', 'Castillo Vega', 'EMP10005', 'rosa.castillo@demo.com'),
('Jorge Luis', 'Ramírez Soto', 'EMP10006', 'jorge.ramirez@demo.com'),
('Diana Patricia', 'Flores León', 'EMP10007', 'diana.flores@demo.com'),
('Miguel Ángel', 'Vargas Paredes', 'EMP10008', 'miguel.vargas@demo.com'),
('Claudia Isabel', 'Herrera Núñez', 'EMP10009', 'claudia.herrera@demo.com'),
('Pedro José', 'Navarro Campos', 'EMP10010', 'pedro.navarro@demo.com');

-- Datos de prueba: 10 clientes.
INSERT INTO clientes_app (nombre, documento, direccion, correo) VALUES
('Comercial San Martín S.A.C.', 'CLI20001', 'Av. Grau 120, Piura', 'ventas@sanmartin.demo'),
('Juan Pérez Salazar', 'CLI20002', 'Calle Lima 245, Sullana', 'juan.perez@demo.com'),
('Bodega La Esperanza', 'CLI20003', 'Jr. Tacna 510, Piura', 'esperanza@demo.com'),
('María López Córdova', 'CLI20004', 'Av. José de Lama 830, Sullana', 'maria.cordova@demo.com'),
('Distribuidora Norte E.I.R.L.', 'CLI20005', 'Av. Panamericana 105, Tumbes', 'pedidos@norte.demo'),
('Carlos Ruiz Mendoza', 'CLI20006', 'Calle Cusco 440, Talara', 'carlos.ruiz@demo.com'),
('Minimarket El Sol', 'CLI20007', 'Av. Buenos Aires 321, Piura', 'elsol@demo.com'),
('Lucía Fernández Ríos', 'CLI20008', 'Calle San José 178, Sullana', 'lucia.fernandez@demo.com'),
('Restaurante Buen Sabor', 'CLI20009', 'Av. Bolognesi 670, Paita', 'contacto@buensabor.demo'),
('Servicios Andinos S.A.C.', 'CLI20010', 'Urb. Miraflores Mz. B Lt. 8, Piura', 'admin@andinos.demo');

-- Verificación de los registros.
SELECT * FROM empleados_app ORDER BY id;
SELECT * FROM clientes_app ORDER BY id;
SELECT id, username, rol, activo FROM usuarios_app ORDER BY id;

-- IMPORTANTE:
-- La guía crea el usuario admin automáticamente mediante AdminInicial.java
-- y almacena su contraseña con BCrypt. Ejecuta la aplicación una vez para crearlo.
-- No insertes contraseñas en texto plano en usuarios_app.
