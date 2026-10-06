package model;

public class PedidoComida extends Pedido {


    public PedidoComida(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega);
    }

    @Override
    public String getTipo() {

        return "COMIDA";
    }
}
