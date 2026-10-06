package dao;

import model.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    /**
     * Registra una entrega.
     */
    public boolean create(Entrega entrega) {

        String sql = "INSERT INTO entregas " + "(id_pedido, id_repartidor, fecha_hora)" + "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setTimestamp(3, Timestamp.valueOf(entrega.getFechaHora()));

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entrega.setId(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar entrega: " + e.getMessage());;
        }

        return false;
    }

    /**
     * Listar todas las entregas.
     */
    public List<Entrega> readAll() {

        List<Entrega> entregas = new ArrayList<>();

        String sql = "SELECT id, id_pedido, id_repartidor, fecha_hora " + "FROM entregas ORDER BY id";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Entrega entrega = new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime()
                );

                entregas.add(entrega);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar entregas: " + e.getMessage());
        }

        return entregas;
    }

    /**
     * Actualiza una entrega.
     */
    public boolean update(Entrega entrega) {

        String sql = "UPDATE entregas " + "SET id_pedido = ?, id_repartidor = ?, fecha_hora = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setTimestamp(3, Timestamp.valueOf(entrega.getFechaHora()));
            ps.setInt(4, entrega.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar entrega: " + e.getMessage());;
        }

        return false;
    }

    /**
     * Elimina una entrega.
     */
    public boolean delete(int id) {

        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar entrega: " + e.getMessage());;
        }

        return false;
    }

    public List<String[]> readAllDetalle() {

        List<String[]> lista = new ArrayList<>();

        String sql =
                "SELECT e.id, " +
                "e.id_pedido, " +
                "p.direccion, " +
                "e.id_repartidor, " +
                "r.nombre, " +
                "e.fecha_hora " +
                "FROM entregas e " +
                "INNER JOIN pedidos p " +
                "ON e.id_pedido = p.id " +
                "INNER JOIN repartidores r " +
                "ON e.id_repartidor = r.id " +
                "ORDER BY e.id";

        try (Connection conexion = ConexionDB.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String[] fila = {

                        String.valueOf(rs.getInt("id")),
                        rs.getInt("id_pedido") + " - " + rs.getString("direccion"),
                        rs.getInt("id_repartidor") + " - " + rs.getString("nombre"),
                        rs.getTimestamp("fecha_hora").toString()
                };

                lista.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar entregas: " + e.getMessage());
        }

        return lista;
    }

    public List<String[]> readAllDetalleFiltrado(
            Integer idPedido,
            Integer idRepartidor) {

        List<String[]> lista = new ArrayList<>();

        String sql =
                "SELECT e.id, e.id_pedido, p.direccion, " +
                        "e.id_repartidor, r.nombre, e.fecha_hora " +
                        "FROM entregas e " +
                        "INNER JOIN pedidos p ON e.id_pedido = p.id " +
                        "INNER JOIN repartidores r ON e.id_repartidor = r.id ";

        if (idPedido != null && idRepartidor != null) {

            sql += "WHERE e.id_pedido = ? AND e.id_repartidor = ? ";

        } else if (idPedido != null) {

            sql += "WHERE e.id_pedido = ? ";

        } else if (idRepartidor != null) {

            sql += "WHERE e.id_repartidor = ? ";
        }

        sql += "ORDER BY e.id";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            int posicion = 1;

            if (idPedido != null) {
                ps.setInt(posicion++, idPedido);
            }

            if (idRepartidor != null) {
                ps.setInt(posicion++, idRepartidor);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String[] fila = {

                            String.valueOf(
                                    rs.getInt("id")
                            ),

                            rs.getInt("id_pedido")
                                    + " - "
                                    + rs.getString("direccion"),

                            rs.getInt("id_repartidor")
                                    + " - "
                                    + rs.getString("nombre"),

                            rs.getTimestamp("fecha_hora")
                                    .toString()
                    };

                    lista.add(fila);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al filtrar entregas: "
                            + e.getMessage()
            );
        }

        return lista;
    }
}
