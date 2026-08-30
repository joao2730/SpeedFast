package model;

import java.security.spec.RSAOtherPrimeInfo;

public class PedidoEncomienda extends Pedido{

    private double peso;
    private boolean embalajeValido;

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm, double peso, boolean embalajeValido) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.peso = peso;
        this.embalajeValido = embalajeValido;
    }

    // Sobreescritura
    @Override
    public void asignarRepartidor() {

        if (peso <= 20 && embalajeValido) {
            System.out.println("Pedido de encomienda #" + getIdPedido());
            System.out.println("Repartidor asignado.");
            System.out.println("Peso y embalaje validados.");
        } else {
            System.out.println("No se puede asignar el repartidor.");
            System.out.println("Revisar peso o embalaje.");
        }

    }

    // Sobrecarga
    @Override
    public void asignarRepartidor(String nombre) {

        System.out.println("Pedido de encomienda #" + getIdPedido());

        if (peso <= 20 && embalajeValido) {
            System.out.println("Repartidor: " +  nombre);
            System.out.println("Peso: " + peso + " kg");
            System.out.println("Embalaje validado.");
        }else  {
            System.out.println("No se puede asignar a " +  nombre);
            System.out.println("La encomienda no cumple las condiciones.");
        }
    }

    // Cálculo del tiempo
    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + 1.5 * getDistanciaKm());
    }
}
