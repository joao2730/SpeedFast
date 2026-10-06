package model;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega);
    }

    @Override
    public String getTipo() {

        return "EXPRESS";
    }
}
