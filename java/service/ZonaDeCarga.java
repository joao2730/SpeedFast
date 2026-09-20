package service;

import model.EstadoPedido;
import model.Pedido;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ZonaDeCarga {

    private BlockingQueue<Pedido> pedidos;

    public ZonaDeCarga() { pedidos = new LinkedBlockingQueue<>(); }

    public void agregarPedido(Pedido p) {
        pedidos.offer(p);
    }

    public Pedido retirarPedido() {

        Pedido pedido = pedidos.poll();

        if (pedido != null) {
            pedido.setEstado(EstadoPedido.EN_REPARTO);
        }

        return pedido;
    }

    public boolean estaVacia() {
        return pedidos.isEmpty();
    }
}
