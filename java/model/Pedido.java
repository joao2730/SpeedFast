package model;

public class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private EstadoPedido estado;

    public Pedido(int idPedido, String direccionEntrega, EstadoPedido estado) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
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

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @Override
    public String toString() {
        return "Pedido {" + "id = " + idPedido + ", direccion Entrega = " + direccionEntrega + '\'' + "estado = " + estado + '}';
    }
}
