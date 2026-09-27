package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    /**
     * Guarda un pedido en MySQL
     */
    public boolean guardar(Pedido pedido) {

        String sql = """
                INSERT INTO pedido
                (direccion, tipo, estado, distancia_km)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipo());
            ps.setString(3, pedido.getEstado().name());
            ps.setDouble(4, pedido.getDistanciaKm());

            int filas = ps.executeUpdate();

            if (filas > 0) {

                /**
                 * Obtener el ID generado
                 * automaticamente por MySQL.
                 */
                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {

                        pedido.setIdPedido(rs.getInt(1));
                    }
                }

                System.out.println("Pedido guardado correctamente");

                return true;

            }
        } catch (SQLException e) {

            System.out.println("Error al guardar pedido: " + e.getMessage());
        }

        return false;
    }

    /**
     * Obtiene todos los pedidos de MySQL.
     */
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT id,
                direccion,
                tipo,
                estado,
                distancia_km
                
                FROM pedido
                ORDER BY id
                """;

        try (Connection conexion = ConexionDB.conectar();

             PreparedStatement ps = conexion.prepareStatement(sql);

             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");
                double distancia = rs.getDouble("distancia_km");

                Pedido pedido;

                /**
                 * Reconstruimos el objeto
                 * dependiendo del tipo.
                 */
                switch (tipo.toUpperCase()) {

                    case "COMIDA":

                        pedido = new PedidoComida(id, direccion, distancia);

                        break;

                    case "ENCOMIENDA":

                        pedido = new PedidoEncomienda(id, direccion, distancia);

                        break;

                    case "EXPRESS":

                        pedido = new PedidoExpress(id, direccion, distancia);

                        break;

                        default:

                            System.out.println("Tipo desconocido: " + tipo);

                            continue;
                }

                /**
                 * Recuperar el estado
                 * alamcenado en MySQL.
                 */
                pedido.setEstado(EstadoPedido.valueOf(estado.toUpperCase()));

                pedidos.add(pedido);
            }
        } catch (SQLException e) {

            System.out.println("Error al listar pedidos: " +  e.getMessage());
        }

        return pedidos;
    }

    /**
     * Actualiza el estado de un pedido.
     */
    public boolean actualizarEstado(Pedido pedido) {

        String sql = """
                UPDATE pedido
                SET estado = ?
                WHERE id = ?;
                """;

        try (Connection conexion = ConexionDB.conectar();

             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getEstado().name());
            ps.setInt(2, pedido.getIdPedido());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar estado: " +  e.getMessage());
        }

        return false;
    }

}
