package app;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

public class Main {

    public static void main(String[] args) {

        System.out.println("==== SISTEMA SPEEDFAST ====");

        // Crear pedido de comida
        PedidoComida comida = new PedidoComida(
                1,
                "Av. Los Carrera 123",
                true
        );

        // Crear pedido de encomienda
        PedidoEncomienda encomienda = new PedidoEncomienda(
                2,
                "Av. Alemania 456",
                10,
                true
        );

        // Crear pedido express
        PedidoExpress express = new PedidoExpress(
                3,
                "Av. O'Higgins 789",
                3.5,
                true
        );

        System.out.println("\n--- ASIGNACION SIN NOMBRE ---");

        comida.asignarRepartidor();

        System.out.println();

        encomienda.asignarRepartidor();

        System.out.println();

        express.asignarRepartidor();

        System.out.println("\n--- ASIGNACION CON NOMBRE ---");

        comida.asignarRepartidor("Juan Pérez");

        System.out.println();

        encomienda.asignarRepartidor("Camila Soto");

        System.out.println();

        express.asignarRepartidor("Luis Díaz");
    }
}
