package app;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

public class Main {

    public static void main(String[] args) {

        System.out.println("==== SISTEMA SPEEDFAST ====");

        PedidoComida comida = new PedidoComida(
                1,
                "Av. Los Carrera 123",
                5
        );

        PedidoEncomienda encomienda = new PedidoEncomienda(
                2,
                "Av. Alemania 456",
                6
        );

        PedidoExpress express = new PedidoExpress(
                3,
                "Av. O'Higgins 789",
                8
        );

        System.out.println("\n--- PEDIDO DE COMIDA ---");
        comida.mostrarResumen();
        System.out.println("Tiempo estimado: " + comida.calcularTiempoEntrega() + " minutos");

        System.out.println("\n--- PEDIDO DE ENCOMIENDA ---");
        encomienda.mostrarResumen();
        System.out.println("Tiempo estimado: " + encomienda.calcularTiempoEntrega() + " minutos");

        System.out.println("\n--- PEDIDO DE EXPRESS ---");
        express.mostrarResumen();
        System.out.println("Tiempo estimado: " + express.calcularTiempoEntrega() + " minutos");

        System.out.println("\n==== COMPARACIÓN DE TIEMPOS ====");
        System.out.println("Comida: " + comida.calcularTiempoEntrega() + " minutos");
        System.out.println("Encomienda: " + encomienda.calcularTiempoEntrega() + " minutos");
        System.out.println("Express: " + express.calcularTiempoEntrega() + " minutos");
    }
}
