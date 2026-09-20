package model;

public class PedidoComida extends Pedido {


    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + 2 * getDistanciaKm());
    }

    @Override
    public String getTipo() {

        return "Comida";
    }

    @Override
    public void asignarRepartidor() {

        setRepartidor("Repartidor con mochila termica");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        setRepartidor(nombreRepartidor + " - Mochila termica");
    }
}
