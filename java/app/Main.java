package app;

import dao.ConexionDB;
import view.VentanaPrincipal;

import javax.swing.*;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventana = new VentanaPrincipal();

            ventana.setVisible(true);
        });
    }
}
