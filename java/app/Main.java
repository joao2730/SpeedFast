package app;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import service.ControladorDeEnvios;

public class Main {

    public static void main(String[] args) {

        System.out.println("============================");
        System.out.println("==== SISTEMA SPEEDFAST ====");
        System.out.println("============================");

        PedidoComida comida = new PedidoComida(
                1,
                "Av. Los Carrera 123",
                5,
                true
        );

        PedidoEncomienda encomienda = new PedidoEncomienda(
                2,
                "Av. Alemania 456",
                6,
                10,
                true
        );

        PedidoExpress express = new PedidoExpress(
                3,
                "Av. O'Higgins 789",
                8,
                true
        );

        // Mostrar Informacion
        System.out.println("\n--- PEDIDOS ---");

        comida.mostrarResumen();
        System.out.println("Tiempo: " + comida.calcularTiempoEntrega() + " minutos");

        System.out.println();

        encomienda.mostrarResumen();
        System.out.println("Tiempo: " + encomienda.calcularTiempoEntrega() + " minutos");

        System.out.println();

        express.mostrarResumen();
        System.out.println("Tiempo: " + express.calcularTiempoEntrega() + " minutos");

        // Asignación automática
        System.out.println("\n--- ASIGNACIÓN AUTOMÁTICA ---");

        comida.asignarRepartidor();
        encomienda.asignarRepartidor();
        express.asignarRepartidor();

        // Asignación manual
        System.out.println("\n--- ASIGNACIÓN MANUAL");

        comida.asignarRepartidor("Carlos");
        encomienda.asignarRepartidor("Pedro");
        express.asignarRepartidor("Juan");

        // Crear controlador
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        controlador.agregarPedido(comida);
        controlador.agregarPedido(encomienda);
        controlador.agregarPedido(express);

        // Despachar
        System.out.println("\n--- DESPACHO ---");

        controlador.despachar();

        // Cancelar
        System.out.println("\n--- CANCELACIÓN ---");

        controlador.cancelar();

        // Historial
        controlador.verHistorial();
    }
}
