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

        System.out.println("Pedido #" + p.getIdPedido() + " agregado a la zona de carga");
    }

    public Pedido retirarPedido() {

        Pedido pedido = pedidos.poll();

        if (pedido != null) {
            pedido.setEstado(EstadoPedido.EN_REPARTO);
        }

        return pedido;
    }
}
