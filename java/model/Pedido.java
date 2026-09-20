package model;

public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private EstadoPedido estado;
    private String repartidor;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.repartidor = "Sin asignar";
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

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repatidor) {
        this.repartidor = repatidor;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    // Metodo que puede ser sobrescrito
    public void asignarRepartidor() {
        this.repartidor = "Repartidor disponible";
    }

    // Sobrecarga del metodo anterior
    public void asignarRepartidor(String nombreRepartidor) {
        this.repartidor = nombreRepartidor;
    }

    public void mostrarResumen() {
        System.out.println("ID: " + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + estado);
        System.out.println("Repartidor: " + repartidor);
    }

    public abstract int calcularTiempoEntrega();

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Pedido " + idPedido +
                " - " + getTipo() +
                " - " + direccionEntrega +
                " - " + estado;
    }


}
