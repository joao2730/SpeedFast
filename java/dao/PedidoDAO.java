package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    /**
     * Registra un pedido.
     */
    public boolean create(Pedido pedido) {

        String sql = "INSERT INTO pedidos " + "(direccion, tipo, estado) " + "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipo());
            ps.setString(3, pedido.getEstado().name());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setIdPedido(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar pedido: " + e.getMessage());

        }

        return false;
    }

    /**
     * Obtiene todos los pedidos.
     */
    public List<Pedido> readAll() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, estado " + "FROM pedidos" + "ORDER BY id";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");

                Pedido pedido;

                switch (tipo) {
                    case "COMIDA":
                        pedido = new PedidoComida(id, direccion);
                        break;
                    case "ENCOMIENDA":
                        pedido = new PedidoEncomienda(id, direccion);
                        break;
                    case "EXPRESS":
                        pedido = new PedidoExpress(id, direccion);
                        break;

                    default:
                        continue;
                }

                pedido.setEstado(EstadoPedido.valueOf(estado));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
            ;
        }

        return pedidos;
    }

    /**
     * Actualiza un pedido.
     */
    public boolean update(Pedido pedido) {

        String sql = "UPDATE pedidos" + "SET direccion = ?, "+ "tipo = ?, " + "estado = ? " + "WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipo());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getIdPedido());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            ;
        }

        return false;
    }

    /**
     * Eliminar un pedido
     */
    public boolean delete(int id) {

        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());

        }

        return false;
    }

    public boolean actualizarEstado(int idPedido, EstadoPedido estado) {

        String sql =
                "UPDATE pedidos SET estado = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, estado.name());
            ps.setInt(2, idPedido);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar estado del pedido: "
                            + e.getMessage()
            );
        }

        return false;
    }

}
