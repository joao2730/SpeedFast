package model;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega);
    }

    @Override
    public String getTipo() {

        return "ENCOMIENDA";
    }
}
