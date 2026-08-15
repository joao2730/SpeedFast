package model;

public class PedidoExpress extends Pedido {

    private double distancia;
    private boolean disponible;

    public PedidoExpress(int idPedido, String direccionEntrega, double distancia, boolean disponible) {
        super(idPedido, direccionEntrega, "Compra Express");
        this.distancia = distancia;
        this.disponible = disponible;
    }

    // Sobrescritura
    @Override
    public void asignarRepartidor() {
        System.out.println("Pedido express #" + getIdPedido());

        if (disponible && distancia <= 5) {
            System.out.println("Repartidor asignado correctamente.");
            System.out.println("Repartidor disponible y cercano.");
        } else {
            System.out.println("No se puede asignar el repartidor.");
            System.out.println("No existe un repartidor disponible y cercano.");
        }
    }

    // Sobrecarga
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido express #" + getIdPedido());

        if (disponible && distancia <= 5) {
            System.out.println("Repartidor: " + nombreRepartidor);
            System.out.println("Distancia: " + distancia + " Km");
            System.out.println("El repartidor está disponible.");
        } else  {
            System.out.println("No se puede asignar a " + nombreRepartidor);
            System.out.println("No cumple las condiciones de disponibilidad o distancia.");
        }
    }
}
