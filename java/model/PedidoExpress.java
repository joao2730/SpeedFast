package model;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        if (getDistanciaKm() > 5) {
            return 15;
        }

        return 10;
    }

    @Override
    public String getTipo() {

        return "Express";
    }

    @Override
    public void asignarRepartidor() {

        setRepartidor("Repartidor mas cercano");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        setRepartidor(nombreRepartidor);
    }
}
