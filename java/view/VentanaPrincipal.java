package view;

import controller.ControladorPedidos;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import model.EstadoPedido;
import model.Pedido;
import model.Repartidor;
import service.ZonaDeCarga;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

       setTitle("SpeedFast - Sistema de Gestion");
       setSize(500, 350);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearComponentes();
    }

    private void crearComponentes() {

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));

        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton btnRegistrarRepartidor = new JButton("Registrar repartidor");
        JButton btnListarRepartidores = new JButton("Administrar Repartidores");
        JButton btnRegistrarPedido = new JButton("Registrar pedido");
        JButton btnListarPedidos = new JButton("Administrar pedidos");
        JButton btnRegistrarEntrega = new JButton("Registrar entrega");
        JButton btnListarEntregas = new JButton("Administrar entregas");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnRegistrarRepartidor);
        panel.add(btnListarRepartidores);
        panel.add(btnRegistrarPedido);
        panel.add(btnListarPedidos);
        panel.add(btnRegistrarEntrega);
        panel.add(btnListarEntregas);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        btnRegistrarRepartidor.addActionListener(e -> new VentanaRegistroRepartidor().setVisible(true));
        btnListarRepartidores.addActionListener(e -> new VentanaListaRepartidores().setVisible(true));
        btnRegistrarPedido.addActionListener(e -> new VentanaRegistroPedido().setVisible(true));
        btnListarPedidos.addActionListener(e -> new  VentanaListaPedidos().setVisible(true));
        btnRegistrarEntrega.addActionListener(e -> new VentanaRegistroEntrega().setVisible(true));
        btnListarEntregas.addActionListener(e -> new VentanaListaEntregas().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));
    }
}
