package org.dam.models;

public class ProductsModel {
    private int id;
    private String nombre;
    private boolean alcohol;
    private double precio;

    public ProductsModel() {

    }

    public ProductsModel(int id, String nombre, boolean alcohol, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.alcohol = alcohol;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAlcohol() {
        return alcohol;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public String[] toArray() {
        return new String[]{String.valueOf(getId()), getNombre(), String.valueOf(isAlcohol()), String.valueOf(getPrecio())};
    }
}
