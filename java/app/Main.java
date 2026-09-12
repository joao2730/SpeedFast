package app;

import model.EstadoPedido;
import model.Pedido;
import service.Repartidor;
import service.ZonaDeCarga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("     SISTEMA SPEEDFAST     ");
        System.out.println(" ZONA DE CARGA CONCURRENTE ");
        System.out.println("===========================");

        // Crear zona de carga compartida
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // Crear pedidos
        Pedido pedido1 = new Pedido(
                1,
                "Av. Alemania 123",
                EstadoPedido.PENDIENTE
        );

        Pedido pedido2 = new Pedido(
                2,
                "Av. Los Carrera 456",
                EstadoPedido.PENDIENTE
        );

        Pedido pedido3 = new Pedido(
                3,
                "Av. O'Higgins 789",
                EstadoPedido.PENDIENTE
        );

        Pedido pedido4 = new Pedido(
                4,
                "Calle Prat 321",
                EstadoPedido.PENDIENTE
        );

        Pedido pedido5 = new Pedido(
                5,
                "Av. Pedro Montt 654",
                EstadoPedido.PENDIENTE
        );

        // Agregar pedidos a la zona de carga
        zonaDeCarga.agregarPedido(pedido1);
        zonaDeCarga.agregarPedido(pedido2);
        zonaDeCarga.agregarPedido(pedido3);
        zonaDeCarga.agregarPedido(pedido4);
        zonaDeCarga.agregarPedido(pedido5);

        // Crear repartidores
        Repartidor repartidor1 = new Repartidor("Camila" , zonaDeCarga);

        Repartidor repartidor2 = new Repartidor("Luis" , zonaDeCarga);

        Repartidor repartidor3 = new Repartidor("Pedro" , zonaDeCarga);

        // Crear grupos de 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("\n--- INICIANDO ENTREGAS ---\n");

        // Ejecutar repartidores
        executor.submit(repartidor1);
        executor.submit(repartidor2);
        executor.submit(repartidor3);

        // No aceptar nuevas tareas
        executor.shutdown();

        // Esperar a que todos terminen
        while (!executor.isTerminated()) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                break;
            }
        }

        System.out.println("\n===================================================");
        System.out.println("Todos los pedidos han sido entregador correctamente");
        System.out.println("===================================================");
    }

}
