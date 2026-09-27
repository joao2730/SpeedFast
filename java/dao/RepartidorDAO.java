package dao;

import model.Repartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public boolean guardar(Repartidor repartidor) {

        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (
                Connection conexion = ConexionDB.conectar();

                PreparedStatement sentencia = conexion.prepareStatement(sql)

        ) {

            sentencia.setString(1, repartidor.getNombre());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error al guardar repartidor: " + e.getMessage());

            return false;
        }
    }

    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidor";

        try (
                Connection conexion = ConexionDB.conectar();

                PreparedStatement sentencia = conexion.prepareStatement(sql);

                ResultSet resultado = sentencia.executeQuery()

                ) {

            while (resultado.next()) {

                int id = resultado.getInt("id");

                String nombre = resultado.getString("nombre");

                Repartidor repartidor = new Repartidor(id, nombre);

                repartidores.add(repartidor);
            }
        } catch (SQLException e) {

            System.out.println("Error al listar repartidores: " + e.getMessage());
        }

        return repartidores;
    }
}
