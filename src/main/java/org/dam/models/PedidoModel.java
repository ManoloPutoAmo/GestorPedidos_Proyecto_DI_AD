package org.dam.models;

import java.time.LocalDate;

public class PedidoModel {
    private int id;
    private LocalDate fecha;
    private int cliente_id;
    private int producto_id;
    private int cantidad;
    private String dependiente;
    private double precio_total;
    private String nombre_cliente;
    private String nombre_producto;

    public PedidoModel() {

    }

    public PedidoModel(int id, LocalDate fecha, int cliente_id, int producto_id, int cantidad, String dependiente,
            double precio_total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente_id = cliente_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.dependiente = dependiente;
        this.precio_total = precio_total;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDependiente() {
        return dependiente;
    }

    public void setDependiente(String dependiente) {
        this.dependiente = dependiente;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }
    public String toString(){
        return "PedidoModel{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", cliente_id=" + cliente_id +
                ", producto_id=" + producto_id +
                ", cantidad=" + cantidad +
                ", dependiente='" + dependiente + '\'' +
                ", precio_total=" + precio_total +
                '}';
    }

    public String[] toArray() {
        return new String[] { Integer.toString(id), getNombre_cliente(), getNombre_producto(),
                Integer.toString(cantidad), dependiente, Double.toString(precio_total), fecha.toString() };
    }
}
