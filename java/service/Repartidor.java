package service;

import model.Pedido;

import java.util.ArrayList;
import java.util.Random;

public class Repartidor implements Runnable {

    private String nombre;
    private ArrayList<Pedido> pedidos;

    public Repartidor(String nombre) {
        this.nombre = nombre;
        this.pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public void run() {

        Random random = new Random();

        System.out.println("\n" + nombre + " comenzó sus entregas.");

        for (Pedido pedido : pedidos) {

            System.out.println("\n" + nombre + " está entregando el pedido #" + pedido.getIdPedido() + " | Direccion: " + pedido.getDireccionEntrega());

            try {

                // Tiempo aleatorio entre 1 y 3 segundos
                int tiempo = random.nextInt(3000) + 1000;

                Thread.sleep(tiempo);

            } catch (InterruptedException e) {

                System.out.println(nombre + " fue interrumpido.");

                Thread.currentThread().interrupt();
                return;
            }

            System.out.println("\n" + nombre + " terminó la entrega del pedido #" + pedido.getIdPedido());
        }

        System.out.println(nombre + " terminó todas sus entregas.");

    }
}
