package app;

import model.EstadoPedido;
import model.Pedido;
import service.Repartidor;
import service.ZonaDeCarga;
import view.VentanaPrincipal;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventana = new VentanaPrincipal();
            
            ventana.setVisible(true);
        });
    }
}
