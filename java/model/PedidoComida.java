package model;

public class PedidoComida extends Pedido {

    private boolean mochilaTermica;

    public PedidoComida(int idPedido, String direccionEntrega, boolean mochilaTermica) {
        super(idPedido, direccionEntrega, "Comida");
        this.mochilaTermica = mochilaTermica;
    }

    // Sobrescritura
    @Override
    public void asignarRepartidor(){
        if(mochilaTermica){
            System.out.println("Pedido de comida #" + getIdPedido());
            System.out.println("Repartidor asignado correctamente.");
            System.out.println("El repartidor tiene mochila térmica.");
        } else {
            System.out.println("No se puede asignar el pedido.");
            System.out.println("El repartido no tiene mochila térmica.");
        }
    }

    // Sobrecarga
    @Override
    public void asignarRepartidor(String nombreRepartidor){
        System.out.println("Pedido de comida #" + getIdPedido());

        if(mochilaTermica){
            System.out.println("Repartidor: " + nombreRepartidor);
            System.out.println("Validación correcta: tiene mochila térmica.");
        } else  {
            System.out.println("No se puede asignar a " + nombreRepartidor);
            System.out.println("No tiene mochila térmica.");
        }
    }
}
