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

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {

        while (true) {

            // Retirar un pedido de la zona de carga
            Pedido pedido = zonaDeCarga.retirarPedido();

            // Si no quedan pedidos, termina el repartidor
            if (pedido == null) {
                break;
            }

            // Asignamos el repartidor al pedido
            pedido.asignarRepartidor(nombre);

            System.out.println(nombre + " esta entregando el pedido #" + pedido.getIdPedido());

            try {

                Random random = new Random();

                // Simula el tiempo de entrega
                int tiempo = 1000 + random.nextInt(2000);

                Thread.sleep(tiempo);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                return;
            }

            // Marcar pedido como entregado
            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println(nombre + " entrego el pedido #" + pedido.getIdPedido());
        }
    }
}
