package model;

public class PedidoComida extends Pedido {

    private boolean mochilaTermica;

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm, boolean mochilaTermica) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.mochilaTermica = mochilaTermica;
    }

    // Sobreescritura
    @Override
    public void asignarRepartidor() {

        if (mochilaTermica) {
            System.out.println("Pedido de comida #" + getIdPedido());
            System.out.println("Repartidor asignado.");
            System.out.println("El repartido tiene mochila térmica.");
        } else  {
            System.out.println("No se puede asignar el repartidor.");
            System.out.println("Se necesita mochila térmica");
        }
    }

    // Sobrecarga
    @Override
    public void asignarRepartidor(String nombre) {

        System.out.println("Pedido de comida #" + getIdPedido());

        if (mochilaTermica) {
            System.out.println("Repartidor: " + nombre);
            System.out.println("Tiene mochila térmica.");
        } else   {
            System.out.println("No se puede asignar a " + nombre);
            System.out.println("No tiene ochila térmica.");
        }
    }

    // Cálculo del tiempo
    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + 2 * getDistanciaKm());
    }
}
