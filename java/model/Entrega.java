package model;

public class Entrega {

    private int id;
    private int idPedido;
    private int idRepartidor;

    public Entrega(int idPedido, int idRepartidor) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
    }

    public Entrega(int id, int idPedido, int idRepartidor) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
    }

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }
}
