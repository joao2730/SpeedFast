package app;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import service.ControladorDeEnvios;
import service.Repartidor;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        System.out.println("============================");
        System.out.println("======== SPEEDFAST =========");
        System.out.println("== SIMULACIÓN DE ENTREGAS ==");
        System.out.println("============================");

        // Crear pedidos

        Pedido comida1 = new PedidoComida(
                1,
                "Av. Los Carrera 123",
                5,
                true
        );

        Pedido comida2 = new PedidoComida(
                2,
                "Av. Alemania 456",
                3,
                true
        );

        Pedido encomienda1 = new PedidoEncomienda(
                3,
                "Av. O'Higgins 789",
                6,
                10,
                true
        );

        Pedido encomienda2 = new PedidoEncomienda(
                4,
                "Calle Prat 321",
                4,
                8,
                true
        );

        Pedido express1 = new PedidoExpress(
                5,
                "Av. Pedro Montt 111",
                8,
                true
        );

        Pedido express2 = new PedidoExpress(
                6,
                "Calle Independencia 222",
                3,
                true
        );

        // Crear Repartidores

        Repartidor repartidor1 = new Repartidor("Carlos");
        Repartidor repartidor2 = new Repartidor("Pedro");
        Repartidor repartidor3 = new Repartidor("Juan");

        // Asignar Pedidos

        repartidor1.agregarPedido(comida1);
        repartidor2.agregarPedido(encomienda1);

        repartidor2.agregarPedido(comida2);
        repartidor2.agregarPedido(express1);

        repartidor3.agregarPedido(encomienda2);
        repartidor3.agregarPedido(express2);

        // Crear Ejecutor

        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("\nIniciando repartidores...\n");

        // Ejecutar los tres repartidores
        executor.submit(repartidor1);
        executor.submit(repartidor2);
        executor.submit(repartidor3);

        // No aceptar nuevas tareas
        executor.shutdown();

        // Esperar a que terminen
        while (!executor.isTerminated()) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("=================================");
        System.out.println("TODOS LOS REPARTIDORES TERMINARON");
        System.out.println("=================================");

    }
}
