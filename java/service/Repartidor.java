package service;

import model.EstadoPedido;
import model.Pedido;

import java.util.ArrayList;
import java.util.Random;

public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {

        Random random = new Random();

        while (true) {

            // Retirar un pedido de la zona de carga
            Pedido pedido = zonaDeCarga.retirarPedido();

            // Si no quedan pedidos, termina el repartidor
            if (pedido == null) {
                break;
            }

            System.out.println(nombre + " retiro el pedido #" + pedido.getIdPedido() + " - Estado: " + pedido.getEstado());

            System.out.println(nombre + " esta entregando el pedido #" + pedido.getIdPedido() + " en: " + pedido.getDireccionEntrega());

            try {
                // Simula el tiempo de entrega
                // entre 1 y 3 segundos
                int tiempo = random.nextInt(3000) + 1000;

                Thread.sleep(tiempo);

            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido.");

                Thread.currentThread().interrupt();

                return;
            }

            // Marcar pedido como entregado
            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println(nombre + " entrego correctamente el pedido #" + pedido.getIdPedido() + " - Estado: " + pedido.getEstado());
        }

        System.out.println(nombre + " terminó todas sus entregas.");

    }
}
