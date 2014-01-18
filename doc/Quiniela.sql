DROP TABLE IF EXISTS cajas;
DROP TABLE IF EXISTS transaccions;
DROP TABLE IF EXISTS ctacte;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS diaDeposito;
DROP TABLE IF EXISTS usuario;

CREATE TABLE cajas (  id int auto_increment PRIMARY KEY,  fecha DATE NOT NULL,  saldo DECIMAL(18,2), visible SMALLINT);

CREATE TABLE transaccions (  id int auto_increment PRIMARY KEY,  motivo varchar(100) NOT NULL,  tipo varchar(40),  monto DECIMAL(18,2), visible SMALLINT,  caja_id int, usuario_id int);

CREATE TABLE ctactes (nombre varchar(40) PRIMARY KEY, apellido varchar(40) PRIMARY KEY, deber DECIMAL(18,2), saldo DECIMAL(18,2), haber DECIMAL(18,2));

CREATE TABLE productos (nombre varchar(40) PRIMARY KEY, precio DECIMAL(18,2), stock int, comision DECIMAL(18,2), diaSorteo DATE NOT NULL);

CREATE TABLE  diaDeposito (nombre varchar(40) FOREIGN KEY REFERENCES productos(nombre), diaDesposito DATE PRIMARY KEY);

CREATE TABLE usuario (id int auto_increment, nombre varchar(40) PRIMARY KEY, pass varchar(16));

