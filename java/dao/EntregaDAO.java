package dao;

import model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDAO {

    public boolean guardar(Entrega entrega) {

        String sql = """
                INSERT INTO entrega
                (id_pedido, id_repartidor
                VALUES (?, ?)
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;

        try {

            conexion = ConexionDB.conectar();

            if (conexion == null) {
                return false;
            }

            sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, entrega.getIdPedido());
            sentencia.setInt(2, entrega.getIdRepartidor());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error al guardar entrega: " + e.getMessage());

            return false;

        } finally {

            try {
                if (sentencia != null) {
                    sentencia.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

            } catch (SQLException e) {

                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
