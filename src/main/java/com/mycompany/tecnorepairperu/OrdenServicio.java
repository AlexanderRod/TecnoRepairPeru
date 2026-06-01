package com.mycompany.tecnorepairperu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrdenServicio {
    private int id;
    private int idEquipo;
    private String problema;
    private String diagnostico;
    private String repuestos;
    private double manoObra;
    private double presupuesto;
    private EstadoOS estado;
    private double pagoTotal;
    private String fechaCreacion;
    private String tecnico;

    public OrdenServicio(int id, int idEquipo, String problema, String diagnostico,
                         String repuestos, double manoObra, double presupuesto,
                         EstadoOS estado, double pagoTotal, String fechaCreacion, String tecnico) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.problema = problema;
        this.diagnostico = diagnostico;
        this.repuestos = repuestos;
        this.manoObra = manoObra;
        this.presupuesto = presupuesto;
        this.estado = estado;
        this.pagoTotal = pagoTotal;
        this.fechaCreacion = fechaCreacion;
        this.tecnico = tecnico;
    }

    // Constructor para nueva orden (sin diagnóstico ni repuestos)
    public OrdenServicio(int id, int idEquipo, String problema) {
        this(id, idEquipo, problema, "", "", 0.0, 0.0,
             EstadoOS.PENDIENTE, 0.0,
             LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "");
    }

    // Cálculo automático del presupuesto
    public static double calcularPresupuesto(String repuestosStr, double manoObra) {
        double sumaRepuestos = 0.0;
        if (repuestosStr != null && !repuestosStr.trim().isEmpty()) {
            String[] items = repuestosStr.split(";");
            for (String item : items) {
                String[] parts = item.split(":");
                if (parts.length == 2) {
                    try {
                        sumaRepuestos += Double.parseDouble(parts[1]);
                    } catch (NumberFormatException e) { }
                }
            }
        }
        return sumaRepuestos + manoObra;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }
    public String getProblema() { return problema; }
    public void setProblema(String problema) { this.problema = problema; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getRepuestos() { return repuestos; }
    public void setRepuestos(String repuestos) {
        this.repuestos = repuestos;
        this.presupuesto = calcularPresupuesto(this.repuestos, this.manoObra);
    }
    public double getManoObra() { return manoObra; }
    public void setManoObra(double manoObra) {
        this.manoObra = manoObra;
        this.presupuesto = calcularPresupuesto(this.repuestos, this.manoObra);
    }
    public double getPresupuesto() { return presupuesto; }
    public EstadoOS getEstado() { return estado; }
    public void setEstado(EstadoOS estado) { this.estado = estado; }
    public double getPagoTotal() { return pagoTotal; }
    public void setPagoTotal(double pagoTotal) { this.pagoTotal = pagoTotal; }
    public String getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    @Override
    public String toString() {
        return id + "|" + idEquipo + "|" + problema + "|" + diagnostico + "|" +
               repuestos + "|" + manoObra + "|" + presupuesto + "|" +
               estado + "|" + pagoTotal + "|" + fechaCreacion + "|" + tecnico;
    }

    public static OrdenServicio fromString(String linea) {
        String[] partes = linea.split("\\|");
        EstadoOS est = EstadoOS.valueOf(partes[7]);
        return new OrdenServicio(
            Integer.parseInt(partes[0]), Integer.parseInt(partes[1]),
            partes[2], partes[3], partes[4], Double.parseDouble(partes[5]),
            Double.parseDouble(partes[6]), est, Double.parseDouble(partes[8]),
            partes[9], partes[10]
        );
    }
}
