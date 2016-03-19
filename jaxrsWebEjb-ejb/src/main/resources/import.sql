--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into proveedor(id, nombre, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212')
insert into producto(id, nombre, precio, proveedor_id,descripcion) values (0, 'Ayudin', 7000, 0, 'lavandina no toxica')
insert into compra(id, proveedor_id,total,descripcion) values (0,0,14000,'Relleno stock')
insert into compra_detalle(id, compra_id, producto_id, cantidad) values (0,0,0,2)

insert into cliente(id, name, email, phone_number) values (0, 'Jesus Aguilar', 'chuchosoft.239@gmail.com', '0984172961')
--insert into pago(id, observacion, cliente_id) values (0, 'Pago atrasado por factura', 0)
insert into venta(id,total,descripcion) values (0,7000,'venta de prueba')
insert into venta_detalle(id, venta_id, producto_id, cantidad) values (0,0,0,1)

