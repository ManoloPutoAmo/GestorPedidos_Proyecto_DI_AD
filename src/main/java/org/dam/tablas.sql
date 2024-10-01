create database gestor_pedidos;

create table clientes(
                         cliente_id SERIAL PRIMARY KEY,
                         nombre VARCHAR(60) NOT NULL,
                         edad int not null check ( edad >=18 ),
                         telefono int UNIQUE not null check ( telefono BETWEEN 100000000 AND 999999999)

);
create table productos(
                          producto_id SERIAL PRIMARY KEY,
                          nombre VARCHAR(60) not null unique,
                          alcohol boolean not null default false,
                          precio REAL NOT NULL
);

create table pedidos(
                        pedido_id SERIAL PRIMARY KEY,
                        fecha_creado DATE NOT NULL,
                        cliente_id int REFERENCES clientes(cliente_id),
                        producto_id int REFERENCES productos(producto_id),
                        cantidad_producto int not null
)