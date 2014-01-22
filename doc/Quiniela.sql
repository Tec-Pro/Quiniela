DROP TABLE IF EXISTS cajas;
DROP TABLE IF EXISTS transaccions;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS diaDeposito;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS productos_transaccions;

CREATE TABLE cajas (  id int auto_increment PRIMARY KEY,  fecha DATE NOT NULL,  saldo DECIMAL(18,2), visible SMALLINT);

CREATE TABLE transaccions (  id int auto_increment PRIMARY KEY,  motivo varchar(100) NOT NULL,  tipo varchar(40),  monto DECIMAL(18,2), visible SMALLINT,  caja_id int, usuario_id int);

CREATE TABLE clientes (id int auto_increment PRIMARY KEY, nombre varchar(40) NOT NULL, apellido varchar(40) NOT NULL, deber DECIMAL(18,2), saldo DECIMAL(18,2), haber DECIMAL(18,2), visible SMALLINT);

CREATE TABLE productos (id int auto_increment PRIMARY KEY, nombre varchar(40) NOT NULL, precio DECIMAL(18,2), stock int DEFAULT 0, comision DECIMAL(18,2), diaSorteo DATE NOT NULL DEFAULT '1999-02-02', visible SMALLINT);

CREATE TABLE  diaDeposito (nombre varchar(40), dia_deposito DATE, 
CONSTRAINT pk_diaDeposito PRIMARY KEY (nombre,dia_deposito));

CREATE TABLE usuarios (id int auto_increment PRIMARY KEY, nombre varchar(40), pass varchar(16), visible SMALLINT);

CREATE TABLE productos_transaccions (id int auto_increment PRIMARY KEY, producto_id int, transaccion_id int);
