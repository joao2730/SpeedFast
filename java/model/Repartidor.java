package model;

import service.ZonaDeCarga;

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

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            // No quedan pedidos
            if (pedido == null) {
                break;
            }

            // Asignamos este repartidor al pedido
            pedido.asignarRepartidor(nombre);

            System.out.println(nombre + " esta entregando el pedido " + pedido.getIdPedido());

            try {

                Random random = new Random();

                int tiempo = 1000 + random.nextInt(2000);

                Thread.sleep(tiempo);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(nombre + " fue interrumpido.");

                break;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println(nombre + " entrego el pedido " + pedido.getIdPedido());
        }

    }
}







