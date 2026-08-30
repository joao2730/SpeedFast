package model;

public class PedidoExpress extends Pedido {

    private boolean disponible;

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm, boolean disponible) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.disponible = disponible;
    }

    // Sobreescritura
    @Override
    public void asignarRepartidor() {

        if (disponible && getDistanciaKm() <= 5) {
            System.out.println("Pedido express #" + getIdPedido());
            System.out.println("Repartidor cercano y disponible.");
        } else  if (disponible) {
            System.out.println("Pedido express #" + getIdPedido());
            System.out.println("Repartidor disponible.");
            System.out.println("Se debe considerar la distancia");
        } else  {
            System.out.println("No hay repartidor disponible.");
        }
    }

    // Sobrecarga
    @Override
    public void asignarRepartidor(String nombre) {

        if (disponible) {
            System.out.println("Pedido express #" + getIdPedido());
            System.out.println("Repartidor: " + nombre);
            System.out.println("Repartidor disponible.");
        }else {
            System.out.println("No se puede asignar a " + nombre);
            System.out.println("El repartidor no está disponible.");
        }
    }

    // Cálculo del tiempo
    @Override
    public int calcularTiempoEntrega() {

        int tiempo = 10;

        if (getDistanciaKm() > 5) {
            tiempo +=5;
        }

        return tiempo;
    }
}
