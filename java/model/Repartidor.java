package model;

import service.ZonaDeCarga;
import dao.PedidoDAO;

import java.util.Random;

public class Repartidor implements Runnable {

    private int id;
    private String nombre;

    // Se utiliza para la concurrencia
    private ZonaDeCarga zonaDeCarga;

    // Constructor para registrar desde la interfaz
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    // Constructor para recuperar desde MySQL
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Constructor utilizado para ejecutar el Runnable
    public Repartidor(int id, String nombre, ZonaDeCarga zonaDeCarga) {
        this.id = id;
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonaDeCarga getZonaDeCarga() {
        return zonaDeCarga;
    }

    public void setZonaDeCarga(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }

    // Aqui mantenemos la concurrencia
    @Override
    public void run() {

        if (zonaDeCarga == null) {
            System.out.println("El repartidor no tiene zona de carga.");

            return;
        }

        Random random = new Random();

        while (!Thread.currentThread().isInterrupted()) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            // No quedan pedidos
            if (pedido == null) {
                break;
            }

            pedido.setNombreRepartidor(nombre);

            System.out.println(nombre + " esta entregando el pedido " + pedido.getIdPedido());

            try {

                int tiempo = 1000 + random.nextInt(2000);

                Thread.sleep(tiempo);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                break;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);

            PedidoDAO pedidoDAO = new PedidoDAO();

            pedidoDAO.actualizarEstado(pedido.getIdPedido(), EstadoPedido.ENTREGADO);

            System.out.println(nombre + " entrego el pedido " + pedido.getIdPedido());
        }

    }
}







