package com.mycompany.tecnorepairperu;

public class Cliente {
    private int id;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;

    public Cliente(int id, String nombre, String dni, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return id + "|" + nombre + "|" + dni + "|" + telefono + "|" + email;
    }

    public static Cliente fromString(String linea) {
        String[] partes = linea.split("\\|"); // escapar pipe
        return new Cliente(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3], partes[4]);
    }
}
