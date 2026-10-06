package controller;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;
import model.EstadoPedido;
import model.Pedido;

import java.util.ArrayList;

public class ControladorPedidos implements Despachable, Rastreable {

    private ArrayList<Pedido> pedidos;

    public ControladorPedidos() {

        pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {

        pedidos.add(pedido);
    }

    public ArrayList<Pedido> getPedidos() {

        return pedidos;
    }

    public boolean existePedido(int id) {

        for (Pedido pedido : pedidos) {

            if (pedido.getIdPedido() == id) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void despachar(Pedido pedido) {

        if (pedido.getEstado() == EstadoPedido.PENDIENTE) {

            pedido.setEstado(EstadoPedido.EN_REPARTO);
        }

    }

    @Override
    public String rastrear(Pedido pedido) {
        return "Pedido #" + pedido.getIdPedido() +
                " - Estado: " + pedido.getEstado();
    }
}
