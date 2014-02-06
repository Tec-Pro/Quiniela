InSERT INTO usuarios (nombre,pass,admin,visible) values ('juan','asd',1,1);

INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2012-04-05', 5994.00,1);
INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2012-06-10', 3000.00,1);
INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2013-01-15', 3000.00,1);
INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2013-01-16', 3000.00,0);
INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2013-01-14', 3000.00,0);
INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2014-04-14', 3000.00,1);
INSERT INTO cajas (fecha,  saldo, visible) VALUES ('2014-01-14', 7000.00,1);

INSERT INTO clientes(nombre, apellido, deber, haber, visible) values ('Pablo','Sanchez',100,0,1);
INSERT INTO clientes(nombre, apellido, deber, haber, visible) values ('Ricardo','Sanchez',500,0,1);
INSERT INTO clientes(nombre, apellido, deber, haber, visible) values ('Juan','Perez',0,50,1);
INSERT INTO clientes(nombre, apellido, deber, haber, visible) values ('Roberto','Gomez',0,290,1);
INSERT INTO clientes(nombre, apellido, deber, haber, visible) values ('Pablo','Sanchez',0,0,0);

INSERT INTO productos(nombre, precio, comision, visible, hayStock) values ('Quini 6',5.50,15,1,0);
INSERT INTO productos(nombre, precio, comision, visible, hayStock) values ('Loto',6.50,15,1,0);
INSERT INTO productos(nombre, precio, comision, visible, hayStock,stock) values ('Raspadita',3.50,12,1,1,50);
INSERT INTO productos(nombre, precio, comision, visible, hayStock) values ('Nulo',0.50,15,0,0);

insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,4,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x2','Venta',11,1,4,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x1','Venta',5.50,1,7,2);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x2','Venta',11,1,6,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,10,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,10,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,10,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,10,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,5,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,5,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,5,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id) values ('Quini 6 x3','Venta',16.50,1,5,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id, cliente_id) values ('Quini 6 x3','Venta',16.50,1,5,1,2);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id, cliente_id) values ('Quini 6 x3','Venta',16.50,1,5,1,1);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id, cliente_id) values ('Quini 6 x3','Venta',16.50,1,5,1,5);
insert into transaccions(motivo, tipo, monto, visible, caja_id, usuario_id, cliente_id) values ('Quini 6 x3','Venta',16.50,1,5,1,4);

INSERT INTO fechas(diaDeposito, producto_id) VALUES ( 'Lunes',3);
INSERT INTO fechas(diaDeposito, producto_id) VALUES ('Jueves',3);

insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,1,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,2,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,3,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,4,2);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,5,1);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,6,2);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,7,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,8,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,9,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,10,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,11,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,12,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,13,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,14,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,15,3);
insert into productos_transaccions (producto_id, transaccion_id, cantidad) values (1,16,3);
