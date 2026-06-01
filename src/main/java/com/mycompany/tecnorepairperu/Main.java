package com.mycompany.tecnorepairperu;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static List<Cliente> clientes;
    private static List<Equipo> equipos;
    private static List<OrdenServicio> ordenes;
    private static List<Usuario> usuarios;
    private static Scanner scanner = new Scanner(System.in);
    private static int nextIdCliente, nextIdEquipo, nextIdOS, nextIdUsuario;
    private static Usuario usuarioActual = null;

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        cargarDatos();

        while (usuarioActual == null) {
            mostrarMenuAutenticacion();
            int opcion = leerInt("Opción: ");
            switch (opcion) {
                case 1: login(); break;
                case 2: registrarUsuario(); break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    guardarDatos();
                    System.exit(0);
                default: System.out.println("Opción inválida.");
            }
        }

        while (true) {
            mostrarMenu();
            int opcion = leerInt("Opción: ");
            switch (opcion) {
                case 1: registrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: registrarEquipo(); break;
                case 4: crearOrden(); break;
                case 5: actualizarOrden(); break;
                case 6: registrarPago(); break;
                case 7: historialPorCliente(); break;
                case 8: reportePendientes(); break;
                case 9: reporteIngresosTotales(); break;
                case 10: actualizarCliente(); break;
                case 11: historialPorEquipo(); break;
                case 12:
                    guardarDatos();
                    System.out.println("Sesión cerrada. ¡Hasta luego!");
                    System.exit(0);
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private static void mostrarMenuAutenticacion() {
        System.out.println("\n=== TECNOREPAIR PERÚ S.A.C. ===");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse (nuevo usuario)");
        System.out.println("3. Salir");
    }

    private static void login() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        String user = leerString("Usuario: ");
        String pass = leerString("Contraseña: ");
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(user) && u.getPassword().equals(pass)) {
                usuarioActual = u;
                System.out.println("¡Bienvenido " + u.getNombreCompleto() + "! Rol: " + u.getRol());
                return;
            }
        }
        System.out.println("Usuario o contraseña incorrectos.");
    }

    private static void registrarUsuario() {
        System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
        String nombre = leerString("Nombre completo: ");
        String user = leerString("Usuario: ");
        if (usuarios.stream().anyMatch(u -> u.getUsuario().equals(user))) {
            System.out.println("El usuario ya existe. Intente con otro.");
            return;
        }
        String pass = leerString("Contraseña: ");
        String rol = "EMPLEADO";
        System.out.println("Roles disponibles: EMPLEADO, TECNICO, ADMIN");
        String rolInput = leerString("Ingrese rol (por defecto EMPLEADO): ");
        if (!rolInput.trim().isEmpty()) rol = rolInput.toUpperCase();
        Usuario nuevo = new Usuario(nextIdUsuario++, nombre, user, pass, rol);
        usuarios.add(nuevo);
        guardarDatos();
        System.out.println("Usuario registrado exitosamente. Ahora puede iniciar sesión.");
    }

    private static void cargarDatos() {
        clientes = GestorArchivos.cargarClientes();
        equipos = GestorArchivos.cargarEquipos();
        ordenes = GestorArchivos.cargarOrdenes();
        usuarios = GestorArchivos.cargarUsuarios();

        nextIdCliente = clientes.stream().mapToInt(Cliente::getId).max().orElse(0) + 1;
        nextIdEquipo = equipos.stream().mapToInt(Equipo::getId).max().orElse(0) + 1;
        nextIdOS = ordenes.stream().mapToInt(OrdenServicio::getId).max().orElse(0) + 1;
        nextIdUsuario = usuarios.stream().mapToInt(Usuario::getId).max().orElse(0) + 1;

        if (usuarios.isEmpty()) {
            Usuario admin = new Usuario(nextIdUsuario++, "Administrador", "admin", "admin123", "ADMIN");
            usuarios.add(admin);
            guardarDatos();
            System.out.println("Usuario por defecto creado: admin / admin123");
        }
    }

    private static void guardarDatos() {
        GestorArchivos.guardarClientes(clientes);
        GestorArchivos.guardarEquipos(equipos);
        GestorArchivos.guardarOrdenes(ordenes);
        GestorArchivos.guardarUsuarios(usuarios);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA TECNOREPAIR PERÚ S.A.C. ===");
        System.out.println("Usuario: " + usuarioActual.getUsuario() + " (" + usuarioActual.getRol() + ")");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Registrar equipo");
        System.out.println("4. Crear orden de servicio");
        System.out.println("5. Actualizar orden (diagnóstico, repuestos, técnico)");
        System.out.println("6. Registrar pago de orden");
        System.out.println("7. Historial de reparaciones por cliente");
        System.out.println("8. Reporte de órdenes pendientes");
        System.out.println("9. Reporte de ingresos totales acumulados");
        System.out.println("10. Actualizar datos de un cliente");
        System.out.println("11. Historial de reparaciones por equipo");
        System.out.println("12. Cerrar sesión y salir");
    }

    // ========== MÉTODOS DE NEGOCIO (registrarCliente, listarClientes, etc.) ==========
    // Todos estos métodos se mantienen idénticos a la versión anterior.
    // A continuación se incluyen completos.

    private static int leerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número: ");
            scanner.next();
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }

    private static String leerString(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private static double leerDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        double d = scanner.nextDouble();
        scanner.nextLine();
        return d;
    }

    private static void registrarCliente() {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        String nombre = leerString("Nombre: ");
        String dni = leerString("DNI: ");
        String telefono = leerString("Teléfono: ");
        String email = leerString("Email: ");
        Cliente c = new Cliente(nextIdCliente++, nombre, dni, telefono, email);
        clientes.add(c);
        guardarDatos();
        System.out.println("Cliente registrado con ID: " + c.getId());
    }

    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        if (clientes.isEmpty()) { System.out.println("No hay clientes."); return; }
        for (Cliente c : clientes) {
            System.out.printf("ID: %d | %s | DNI: %s | Tel: %s | Email: %s%n",
                    c.getId(), c.getNombre(), c.getDni(), c.getTelefono(), c.getEmail());
        }
    }

    private static void registrarEquipo() {
        if (clientes.isEmpty()) { System.out.println("Primero registre un cliente."); return; }
        System.out.println("\n--- REGISTRAR EQUIPO ---");
        listarClientes();
        int idCliente = leerInt("ID del cliente dueño: ");
        if (clientes.stream().noneMatch(c -> c.getId() == idCliente)) {
            System.out.println("Cliente no existe.");
            return;
        }
        String marca = leerString("Marca: ");
        String modelo = leerString("Modelo: ");
        String serie = leerString("Serie: ");
        String accesorios = leerString("Accesorios entregados (ej: cargador, mouse, funda): ");
        Equipo eq = new Equipo(nextIdEquipo++, marca, modelo, serie, accesorios, idCliente);
        equipos.add(eq);
        guardarDatos();
        System.out.println("Equipo registrado con ID: " + eq.getId());
    }

    private static void crearOrden() {
        if (equipos.isEmpty()) { System.out.println("No hay equipos registrados."); return; }
        System.out.println("\n--- CREAR ORDEN DE SERVICIO ---");
        System.out.println("Equipos disponibles:");
        for (Equipo e : equipos) {
            Cliente c = buscarClientePorId(e.getIdCliente());
            String clienteNombre = (c != null) ? c.getNombre() : "Desconocido";
            System.out.printf("ID: %d | %s %s (Cliente: %s)%n", e.getId(), e.getMarca(), e.getModelo(), clienteNombre);
        }
        int idEquipo = leerInt("ID del equipo a reparar: ");
        if (equipos.stream().noneMatch(e -> e.getId() == idEquipo)) {
            System.out.println("Equipo no existe.");
            return;
        }
        String problema = leerString("Descripción del problema: ");
        OrdenServicio os = new OrdenServicio(nextIdOS++, idEquipo, problema);
        ordenes.add(os);
        guardarDatos();
        System.out.println("Orden creada con ID: " + os.getId() + " - Estado: PENDIENTE");
    }

    private static void actualizarOrden() {
        if (ordenes.isEmpty()) { System.out.println("No hay órdenes."); return; }
        System.out.println("\n--- ACTUALIZAR ORDEN ---");
        for (OrdenServicio o : ordenes) {
            System.out.printf("ID: %d | Cliente: %s | Estado: %s%n",
                    o.getId(), obtenerClientePorEquipo(o.getIdEquipo()), o.getEstado());
        }
        int idOS = leerInt("ID de la orden: ");
        OrdenServicio os = ordenes.stream().filter(o -> o.getId() == idOS).findFirst().orElse(null);
        if (os == null) { System.out.println("Orden no encontrada."); return; }

        System.out.println("Estados: PENDIENTE, EN_DIAGNOSTICO, EN_REPARACION, LISTO, ENTREGADO");
        String nuevoEstado = leerString("Nuevo estado: ").toUpperCase();
        try {
            os.setEstado(EstadoOS.valueOf(nuevoEstado));
            if (os.getEstado() == EstadoOS.EN_DIAGNOSTICO || os.getEstado() == EstadoOS.EN_REPARACION) {
                String diag = leerString("Diagnóstico (falla encontrada): ");
                os.setDiagnostico(diag);
                System.out.println("Ingrese repuestos necesarios (formato: 'nombre:precio;nombre:precio')");
                System.out.println("Ejemplo: 'Batería:150.00;Fuente de poder:80.00'");
                String repuestos = leerString("Repuestos: ");
                os.setRepuestos(repuestos);
                double manoObra = leerDouble("Costo de mano de obra (S/): ");
                os.setManoObra(manoObra);
                String tecnico = leerString("Nombre del técnico asignado: ");
                os.setTecnico(tecnico);
                System.out.printf("Presupuesto calculado automáticamente: S/%.2f%n", os.getPresupuesto());
            }
            guardarDatos();
            System.out.println("Orden actualizada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido.");
        }
    }

    private static void registrarPago() {
        if (ordenes.isEmpty()) { System.out.println("No hay órdenes."); return; }
        System.out.println("\n--- REGISTRAR PAGO ---");
        for (OrdenServicio o : ordenes) {
            double saldo = o.getPresupuesto() - o.getPagoTotal();
            System.out.printf("ID: %d | Cliente: %s | Presupuesto: S/%.2f | Pagado: S/%.2f | Saldo: S/%.2f%n",
                    o.getId(), obtenerClientePorEquipo(o.getIdEquipo()), o.getPresupuesto(), o.getPagoTotal(), saldo);
        }
        int idOS = leerInt("ID de la orden a pagar: ");
        OrdenServicio os = ordenes.stream().filter(o -> o.getId() == idOS).findFirst().orElse(null);
        if (os == null) { System.out.println("Orden no encontrada."); return; }
        double monto = leerDouble("Monto pagado (S/): ");
        os.setPagoTotal(os.getPagoTotal() + monto);
        guardarDatos();
        System.out.printf("Pago registrado. Total pagado hasta ahora: S/%.2f%n", os.getPagoTotal());
        emitirComprobante(os, monto);
        if (os.getPagoTotal() >= os.getPresupuesto() && os.getEstado() != EstadoOS.ENTREGADO) {
            System.out.println("¡Pago completado! Puede cambiar estado a ENTREGADO cuando corresponda.");
        }
    }

    private static void emitirComprobante(OrdenServicio os, double montoPagado) {
        Equipo eq = equipos.stream().filter(e -> e.getId() == os.getIdEquipo()).findFirst().orElse(null);
        Cliente cli = (eq != null) ? buscarClientePorId(eq.getIdCliente()) : null;
        String nombreCliente = (cli != null) ? cli.getNombre() : "Desconocido";
        String dniCliente = (cli != null) ? cli.getDni() : "00000000";
        String equipoDesc = (eq != null) ? eq.getMarca() + " " + eq.getModelo() + " (Serie: " + eq.getSerie() + ")" : "Equipo no encontrado";
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String numeroComprobante = "COMP-" + os.getId() + "-" + System.currentTimeMillis();

        StringBuilder comprobante = new StringBuilder();
        comprobante.append("=======================================\n");
        comprobante.append("       TECNOREPAIR PERÚ S.A.C.\n");
        comprobante.append("   Av. Argentina 2345, Cercado de Lima\n");
        comprobante.append("=======================================\n");
        comprobante.append("COMPROBANTE DE PAGO\n");
        comprobante.append("N°: ").append(numeroComprobante).append("\n");
        comprobante.append("Fecha: ").append(fechaHora).append("\n");
        comprobante.append("---------------------------------------\n");
        comprobante.append("Cliente: ").append(nombreCliente).append("\n");
        comprobante.append("DNI: ").append(dniCliente).append("\n");
        comprobante.append("Equipo: ").append(equipoDesc).append("\n");
        comprobante.append("Orden de Servicio N°: ").append(os.getId()).append("\n");
        comprobante.append("---------------------------------------\n");
        comprobante.append("Presupuesto total: S/ ").append(String.format("%.2f", os.getPresupuesto())).append("\n");
        comprobante.append("Monto pagado hoy: S/ ").append(String.format("%.2f", montoPagado)).append("\n");
        comprobante.append("Total pagado acumulado: S/ ").append(String.format("%.2f", os.getPagoTotal())).append("\n");
        double saldo = os.getPresupuesto() - os.getPagoTotal();
        comprobante.append("Saldo pendiente: S/ ").append(String.format("%.2f", saldo)).append("\n");
        comprobante.append("=======================================\n");
        comprobante.append("       ¡Gracias por preferirnos!\n");
        comprobante.append("=======================================\n");

        System.out.println("\n" + comprobante.toString());
        GestorArchivos.guardarComprobante(comprobante.toString(), numeroComprobante + ".txt");
    }

    private static void historialPorCliente() {
        if (clientes.isEmpty()) { System.out.println("No hay clientes."); return; }
        listarClientes();
        int idCliente = leerInt("ID del cliente: ");
        List<Equipo> equiposCliente = equipos.stream().filter(e -> e.getIdCliente() == idCliente).collect(Collectors.toList());
        if (equiposCliente.isEmpty()) {
            System.out.println("El cliente no tiene equipos registrados.");
            return;
        }
        System.out.println("\n=== HISTORIAL DE REPARACIONES ===");
        for (Equipo eq : equiposCliente) {
            List<OrdenServicio> ordenesEq = ordenes.stream().filter(o -> o.getIdEquipo() == eq.getId()).collect(Collectors.toList());
            if (ordenesEq.isEmpty()) continue;
            System.out.printf("Equipo: %s %s (Serie: %s) - Accesorios: %s%n",
                    eq.getMarca(), eq.getModelo(), eq.getSerie(), eq.getAccesorios());
            for (OrdenServicio os : ordenesEq) {
                System.out.printf("  OS %d | %s | Problema: %s | Estado: %s | Técnico: %s | Presupuesto: S/%.2f | Pagado: S/%.2f%n",
                        os.getId(), os.getFechaCreacion(), os.getProblema(), os.getEstado(),
                        os.getTecnico(), os.getPresupuesto(), os.getPagoTotal());
                if (!os.getDiagnostico().isEmpty()) System.out.printf("    Diagnóstico: %s%n", os.getDiagnostico());
                if (!os.getRepuestos().isEmpty()) System.out.printf("    Repuestos: %s%n", os.getRepuestos());
            }
        }
    }

    private static void historialPorEquipo() {
        if (equipos.isEmpty()) { System.out.println("No hay equipos registrados."); return; }
        System.out.println("\n--- EQUIPOS DISPONIBLES ---");
        for (Equipo e : equipos) {
            Cliente c = buscarClientePorId(e.getIdCliente());
            String clienteNombre = (c != null) ? c.getNombre() : "Desconocido";
            System.out.printf("ID: %d | %s %s (Serie: %s) - Cliente: %s%n",
                    e.getId(), e.getMarca(), e.getModelo(), e.getSerie(), clienteNombre);
        }
        int idEquipo = leerInt("ID del equipo para ver historial: ");
        Equipo eq = equipos.stream().filter(e -> e.getId() == idEquipo).findFirst().orElse(null);
        if (eq == null) { System.out.println("Equipo no encontrado."); return; }

        List<OrdenServicio> ordenesEq = ordenes.stream().filter(o -> o.getIdEquipo() == eq.getId()).collect(Collectors.toList());
        if (ordenesEq.isEmpty()) {
            System.out.println("Este equipo no tiene órdenes de servicio registradas.");
            return;
        }
        System.out.println("\n=== HISTORIAL DEL EQUIPO ===");
        System.out.printf("Equipo: %s %s (Serie: %s) - Accesorios entregados: %s%n",
                eq.getMarca(), eq.getModelo(), eq.getSerie(), eq.getAccesorios());
        for (OrdenServicio os : ordenesEq) {
            System.out.printf("OS %d | Fecha: %s | Estado: %s | Técnico: %s | Presupuesto: S/%.2f | Pagado: S/%.2f%n",
                    os.getId(), os.getFechaCreacion(), os.getEstado(), os.getTecnico(), os.getPresupuesto(), os.getPagoTotal());
            System.out.printf("  Problema: %s%n", os.getProblema());
            if (!os.getDiagnostico().isEmpty()) System.out.printf("  Diagnóstico: %s%n", os.getDiagnostico());
            if (!os.getRepuestos().isEmpty()) System.out.printf("  Repuestos: %s%n", os.getRepuestos());
        }
    }

    private static void reportePendientes() {
        System.out.println("\n=== ÓRDENES PENDIENTES (NO ENTREGADAS) ===");
        List<OrdenServicio> pendientes = ordenes.stream().filter(o -> o.getEstado() != EstadoOS.ENTREGADO).collect(Collectors.toList());
        if (pendientes.isEmpty()) {
            System.out.println("No hay órdenes pendientes.");
            return;
        }
        for (OrdenServicio os : pendientes) {
            System.out.printf("OS %d | Cliente: %s | Estado: %s | Técnico: %s%n",
                    os.getId(), obtenerClientePorEquipo(os.getIdEquipo()), os.getEstado(), os.getTecnico());
        }
    }

    private static void reporteIngresosTotales() {
        double ingresos = ordenes.stream().mapToDouble(OrdenServicio::getPagoTotal).sum();
        System.out.printf("\n=== INGRESOS TOTALES ACUMULADOS ===\n");
        System.out.printf("Total recaudado por pagos: S/ %.2f%n", ingresos);
    }

    private static void actualizarCliente() {
        if (clientes.isEmpty()) { System.out.println("No hay clientes."); return; }
        listarClientes();
        int id = leerInt("ID del cliente a actualizar: ");
        Cliente c = clientes.stream().filter(cl -> cl.getId() == id).findFirst().orElse(null);
        if (c == null) { System.out.println("Cliente no encontrado."); return; }
        System.out.println("Deje en blanco para no modificar.");
        String nuevoNombre = leerString("Nuevo nombre (" + c.getNombre() + "): ");
        if (!nuevoNombre.trim().isEmpty()) c.setNombre(nuevoNombre);
        String nuevoTelefono = leerString("Nuevo teléfono (" + c.getTelefono() + "): ");
        if (!nuevoTelefono.trim().isEmpty()) c.setTelefono(nuevoTelefono);
        String nuevoEmail = leerString("Nuevo email (" + c.getEmail() + "): ");
        if (!nuevoEmail.trim().isEmpty()) c.setEmail(nuevoEmail);
        guardarDatos();
        System.out.println("Cliente actualizado correctamente.");
    }

    private static String obtenerClientePorEquipo(int idEquipo) {
        Equipo eq = equipos.stream().filter(e -> e.getId() == idEquipo).findFirst().orElse(null);
        if (eq == null) return "Desconocido";
        Cliente cli = buscarClientePorId(eq.getIdCliente());
        return (cli != null) ? cli.getNombre() : "Cliente no encontrado";
    }

    private static Cliente buscarClientePorId(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
}
