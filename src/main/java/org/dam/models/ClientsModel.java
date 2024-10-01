package org.dam.models;

public class ClientsModel {
    private int id;
    private String nombre;
    private int edad;
    private int telefono;

    public ClientsModel() {

    }

    public ClientsModel(int id, String nombre, int edad, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.telefono = telefono;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return getNombre();
    }

    public String[] toArray() {
        return new String[] { String.valueOf(id), getNombre(), String.valueOf(edad),
             String.valueOf(telefono) };
    }

}
