CREATE DATABASE IF NOT EXISTS caso3 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE caso3;
CREATE TABLE IF NOT EXISTS empleados_caso3 (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, codigo VARCHAR(255) NOT NULL UNIQUE,
 nombre VARCHAR(255) NOT NULL, correo VARCHAR(255) NOT NULL, cargo VARCHAR(255), ingreso VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS envios_caso3 (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, empleado_id BIGINT,
 empleado VARCHAR(255), codigo VARCHAR(255), correo VARCHAR(255), tipo VARCHAR(255), plantilla VARCHAR(255),
 contenido VARCHAR(10000), token VARCHAR(255) NOT NULL UNIQUE,
 fecha_envio DATETIME(6), fecha_apertura DATETIME(6), correo_estado VARCHAR(255), correo_error VARCHAR(500), version BIGINT
);
-- El inicializador Java crea cuatro empleados si la tabla está vacía.
-- Las tablas del módulo 2FA original se crean automáticamente mediante JPA.
-- Consultas de evidencia:
SELECT codigo,nombre,correo FROM empleados_caso3;
SELECT id,codigo,tipo,fecha_envio,fecha_apertura,
 CASE WHEN fecha_apertura IS NULL THEN 'PENDIENTE' ELSE 'VISTO' END AS estado,correo_estado
 FROM envios_caso3 ORDER BY id DESC;
