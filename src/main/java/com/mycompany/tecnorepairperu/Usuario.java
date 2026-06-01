package com.mycompany.tecnorepairperu;

public class Usuario {
    private int id;
    private String nombreCompleto;
    private String usuario;
    private String password;
    private String rol;

    public Usuario(int id, String nombreCompleto, String usuario, String password, String rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return id + "|" + nombreCompleto + "|" + usuario + "|" + password + "|" + rol;
    }

    public static Usuario fromString(String linea) {
        String[] partes = linea.split("\\|");
        return new Usuario(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3], partes[4]);
    }
}
