package model;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + 1.5 * getDistanciaKm());
    }

    @Override
    public String getTipo() {

        return "Encomienda";
    }

    @Override
    public void asignarRepartidor() {

        setRepartidor("Repartidor de encomiendas");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        setRepartidor(nombreRepartidor);
    }
}
