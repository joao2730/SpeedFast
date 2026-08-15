package model;

public class PedidoEncomienda extends Pedido{

    private double peso;
    private boolean embalajeValido;

    public PedidoEncomienda(int idPedido, String direccionEntrega, double peso, boolean embalajeValido) {
        super(idPedido, direccionEntrega, "Encomienda");
        this.peso = peso;
        this.embalajeValido = embalajeValido;
    }

    // Sobrescritura
    @Override
    public void asignarRepartidor(){
        System.out.println("Pedido de encomienda #" + getIdPedido());

        if (peso <= 20 && embalajeValido) {
            System.out.println("Repartidor asignado correctamente.");
            System.out.println("Peso y embalaje validados.");
        } else  {
            System.out.println("No se puede asignar el repartidor.");
            System.out.println("Se debe reviasr el peso o el embalaje.");
        }
    }

    // Sobrecarga
    @Override
    public void asignarRepartidor(String nombreRepartidor){
        System.out.println("Pedido de encomienda #" + getIdPedido());

        if (peso <= 20 && embalajeValido) {
            System.out.println("Repartidor: " + nombreRepartidor);
            System.out.println("Peso: " + peso + " Kg");
            System.out.println("Embalaje validado correctamente.");
        } else   {
            System.out.println("No se puede asignar a " + nombreRepartidor);
            System.out.println("La encomienda no cumple las condiciones.");
        }
    }
}
