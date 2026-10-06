package model;

public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private EstadoPedido estado;
    private String nombreRepartidor;

    public Pedido(int idPedido, String direccionEntrega) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
        this.nombreRepartidor = "Sin asignar";
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    public void setNombreRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public abstract String getTipo();

    public void mostrarResumen() {
        System.out.println("Pedido: " + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Tipo: " + getTipo());
        System.out.println("Estado: " + estado);
    }

    @Override
    public String toString() {
        return idPedido + " - " + direccionEntrega;
    }
}