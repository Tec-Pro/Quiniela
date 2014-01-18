DROP TABLE IF EXISTS cajas;
DROP TABLE IF EXISTS transaccions;
DROP TABLE IF EXISTS ctactes;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS diaDeposito;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS productos_transaccions;

CREATE TABLE cajas (  id int auto_increment PRIMARY KEY,  fecha DATE NOT NULL,  saldo DECIMAL(18,2), visible SMALLINT);

CREATE TABLE transaccions (  id int auto_increment PRIMARY KEY,  motivo varchar(100) NOT NULL,  tipo varchar(40),  monto DECIMAL(18,2), visible SMALLINT,  caja_id int, usuario_id int);

CREATE TABLE ctactes (id int auto_increment PRIMARY KEY, nombre varchar(40) NOT NULL, apellido varchar(40) NOT NULL, deber DECIMAL(18,2), saldo DECIMAL(18,2), haber DECIMAL(18,2), visible int);

CREATE TABLE productos (nombre varchar(40) PRIMARY KEY, precio DECIMAL(18,2), stock int DEFAULT 0, comision DECIMAL(18,2), diaSorteo DATE NOT NULL, visible int);

CREATE TABLE  diaDeposito (nombre varchar(40), dia_deposito DATE PRIMARY KEY);

CREATE TABLE usuarios (id int auto_increment PRIMARY KEY, nombre varchar(40), pass varchar(16), visible int);

CREATE TABLE productos_transaccions (id int auto_increment PRIMARY KEY, producto_id int, transaccion_id int);