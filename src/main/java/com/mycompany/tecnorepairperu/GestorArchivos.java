package com.mycompany.tecnorepairperu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {
    private static final String CARPETA = "data/";
    private static final String CARPETA_COMPROBANTES = CARPETA + "comprobantes/";
    private static final String ARCH_CLIENTES = CARPETA + "clientes.csv";
    private static final String ARCH_EQUIPOS = CARPETA + "equipos.csv";
    private static final String ARCH_ORDENES = CARPETA + "ordenes.csv";
    private static final String ARCH_USUARIOS = CARPETA + "usuarios.csv";

    static {
        new File(CARPETA).mkdirs();
        new File(CARPETA_COMPROBANTES).mkdirs();
    }

    // CLIENTES
    public static void guardarClientes(List<Cliente> clientes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCH_CLIENTES))) {
            for (Cliente c : clientes) pw.println(c.toString());
        } catch (IOException e) { System.err.println("Error guardando clientes: " + e.getMessage()); }
    }

    public static List<Cliente> cargarClientes() {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCH_CLIENTES))) {
            String linea;
            while ((linea = br.readLine()) != null)
                if (!linea.trim().isEmpty()) lista.add(Cliente.fromString(linea));
        } catch (FileNotFoundException e) { }
        catch (IOException e) { System.err.println("Error cargando clientes: " + e.getMessage()); }
        return lista;
    }

    // EQUIPOS
    public static void guardarEquipos(List<Equipo> equipos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCH_EQUIPOS))) {
            for (Equipo e : equipos) pw.println(e.toString());
        } catch (IOException e) { System.err.println("Error guardando equipos: " + e.getMessage()); }
    }

    public static List<Equipo> cargarEquipos() {
        List<Equipo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCH_EQUIPOS))) {
            String linea;
            while ((linea = br.readLine()) != null)
                if (!linea.trim().isEmpty()) lista.add(Equipo.fromString(linea));
        } catch (FileNotFoundException e) { }
        catch (IOException e) { System.err.println("Error cargando equipos: " + e.getMessage()); }
        return lista;
    }

    // ORDENES
    public static void guardarOrdenes(List<OrdenServicio> ordenes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCH_ORDENES))) {
            for (OrdenServicio o : ordenes) pw.println(o.toString());
        } catch (IOException e) { System.err.println("Error guardando órdenes: " + e.getMessage()); }
    }

    public static List<OrdenServicio> cargarOrdenes() {
        List<OrdenServicio> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCH_ORDENES))) {
            String linea;
            while ((linea = br.readLine()) != null)
                if (!linea.trim().isEmpty()) lista.add(OrdenServicio.fromString(linea));
        } catch (FileNotFoundException e) { }
        catch (IOException e) { System.err.println("Error cargando órdenes: " + e.getMessage()); }
        return lista;
    }

    // USUARIOS
    public static void guardarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCH_USUARIOS))) {
            for (Usuario u : usuarios) pw.println(u.toString());
        } catch (IOException e) { System.err.println("Error guardando usuarios: " + e.getMessage()); }
    }

    public static List<Usuario> cargarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCH_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null)
                if (!linea.trim().isEmpty()) lista.add(Usuario.fromString(linea));
        } catch (FileNotFoundException e) { }
        catch (IOException e) { System.err.println("Error cargando usuarios: " + e.getMessage()); }
        return lista;
    }

    // COMPROBANTES
    public static void guardarComprobante(String contenido, String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA_COMPROBANTES + nombreArchivo))) {
            pw.println(contenido);
            System.out.println("Comprobante guardado en: " + CARPETA_COMPROBANTES + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error guardando comprobante: " + e.getMessage());
        }
    }
}
