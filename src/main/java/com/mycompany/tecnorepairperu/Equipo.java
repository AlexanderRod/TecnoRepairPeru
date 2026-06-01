package com.mycompany.tecnorepairperu;

public class Equipo {
    private int id;
    private String marca;
    private String modelo;
    private String serie;
    private String accesorios;
    private int idCliente;

    public Equipo(int id, String marca, String modelo, String serie, String accesorios, int idCliente) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.accesorios = accesorios;
        this.idCliente = idCliente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public String getAccesorios() { return accesorios; }
    public void setAccesorios(String accesorios) { this.accesorios = accesorios; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return id + "|" + marca + "|" + modelo + "|" + serie + "|" + accesorios + "|" + idCliente;
    }

    public static Equipo fromString(String linea) {
        String[] partes = linea.split("\\|");
        return new Equipo(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3], partes[4], Integer.parseInt(partes[5]));
    }
}
