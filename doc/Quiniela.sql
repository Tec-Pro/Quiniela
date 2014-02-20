DROP DATABASE IF EXISTS quiniela;
CREATE DATABASE quiniela;

USE quiniela;

CREATE TABLE cajas (
    id int auto_increment PRIMARY KEY,
    fecha DATE NOT NULL,
    saldo DECIMAL(18 , 2 ),
    visible SMALLINT
);

CREATE TABLE transaccions (
    id int auto_increment PRIMARY KEY,
    motivo varchar(140) NOT NULL,
    tipo varchar(40),
    monto DECIMAL(18 , 2 ),
    visible SMALLINT,
    caja_id int,
    usuario_id int,
    cliente_id int
);

CREATE TABLE clientes (
    id int auto_increment PRIMARY KEY,
    nombre varchar(40) NOT NULL,
    apellido varchar(40) NOT NULL,
    deber DECIMAL(18 , 2 ) DEFAULT 0,
    haber DECIMAL(18 , 2 ) DEFAULT 0,
    visible SMALLINT
);

CREATE TABLE productos (
    id int auto_increment PRIMARY KEY,
    nombre varchar(40) NOT NULL UNIQUE,
    precio DECIMAL(18 , 2 ),
    comision DECIMAL(18 , 2 ),
    visible SMALLINT,
    hayStock SMALLINT,
    stock int not null default 0
);

CREATE TABLE diaDeposito (
    nombre varchar(40),
    dia_deposito DATE,
    CONSTRAINT pk_diaDeposito PRIMARY KEY (nombre , dia_deposito)
);

CREATE TABLE usuarios (
    id int auto_increment PRIMARY KEY,
    nombre varchar(40) UNIQUE,
    pass varchar(16),
    admin int NOT NULL DEFAULT 0,
    visible SMALLINT
);

CREATE TABLE productos_transaccions (
    id int auto_increment PRIMARY KEY,
    producto_id int,
    transaccion_id int,
    cantidad int,
    precio DECIMAL(18,2),
    comision DECIMAL(18,2)
);

CREATE TABLE fechas (
    id int auto_increment PRIMARY KEY,
    diaDeposito varchar(12) NOT NULL,
    producto_id int
);

INSERT INTO usuarios(nombre,pass,admin,visible) VALUES ('Quiniela','Quiniela',1,1);

CREATE USER 'tecpro'@'localhost' IDENTIFIED BY 'tecpro';
GRANT ALL PRIVILEGES ON *.* TO 'tecpro'@'localhost' IDENTIFIED BY 'tecpro' WITH GRANT OPTION
