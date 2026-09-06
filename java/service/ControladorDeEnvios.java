package service;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;
import model.Pedido;

import java.util.ArrayList;

public class ControladorDeEnvios implements Despachable, Cancelable, Rastreable {

    private ArrayList<Pedido> pedidos;
    private ArrayList<String> historial;

    public ControladorDeEnvios() {
        pedidos = new ArrayList<>();
        historial = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        System.out.println("Pedido #" + pedido.getIdPedido() + " agregado.");
    }

    @Override
    public void cancelar() {

        System.out.println("Cancelando último pedido...");

        if (!pedidos.isEmpty()) {

            Pedido pedido = pedidos.get(pedidos.size() - 1);

            System.out.println("Pedido #" + pedido.getIdPedido() + " cancelado.");

            historial.add("Pedido #" + pedido.getIdPedido() + " cancelado.");

        } else  {
            System.out.println("No existen pedidos para cancelar.");
        }

    }

    @Override
    public void despachar() {

        System.out.println("Despachando pedidos...");

        for (Pedido pedido : pedidos) {
            System.out.println("Pedido #" + pedido.getIdPedido() + " despachando.");
            historial.add("Pedido #" + pedido.getIdPedido() + " despachado.");
        }

    }

    @Override
    public void verHistorial() {

        System.out.println("\n===== HISTORIAL DE ENVÍOS =====");

        if (historial.isEmpty()) {
            System.out.println("No existen registros.");
        }else  {
            for (String registro : historial) {
                System.out.println("- " +  registro);
            }
        }

    }
}
