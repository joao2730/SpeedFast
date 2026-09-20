package view;

import controller.ControladorPedidos;
import model.EstadoPedido;
import model.Pedido;
import service.Repartidor;
import service.ZonaDeCarga;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VentanaPrincipal extends JFrame {

    private ControladorPedidos controlador;

    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnIniciar;

    public VentanaPrincipal() {

        controlador = new ControladorPedidos();

        setTitle("SpeedFast - Sistema de Entregas");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
    }

    private void crearInterfaz() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("SISTEMA SPEEDFAST", SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();

        panelBotones.setLayout(new GridLayout(3,1,10,10));

        btnRegistrar = new JButton("Registrar pedido");

        btnListar = new JButton("Listar pedidos");

        btnIniciar = new JButton("Asignar repartidor / Iniciar entrega" );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnIniciar);

        add(panelBotones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> {

            VentanaRegistroPedidos ventana = new VentanaRegistroPedidos(controlador);

            ventana.setVisible(true);
        });

        btnListar.addActionListener(e -> {

            VentanaListaPedidos ventana = new VentanaListaPedidos(controlador);

            ventana.setVisible(true);
        });

        btnIniciar.addActionListener(e -> IniciarEntrega());
    }

    private void IniciarEntrega() {

        boolean hayPedidos = false;

        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // Agregar solamente pedidos pendientes
        for (Pedido pedido : controlador.getPedidos()) {

            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {

                zonaDeCarga.agregarPedido(pedido);

                hayPedidos = true;
            }
        }

        if (!hayPedidos) {

            JOptionPane.showMessageDialog(this, "No existen pedidos pendientes.", "SpeedFast", JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        // Ejecutamos la entrega en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {

                ExecutorService executor = Executors.newFixedThreadPool(3);

                Repartidor repartidor1 = new Repartidor("Juan", zonaDeCarga);

                Repartidor repartidor2 = new Repartidor("Pedro", zonaDeCarga);

                Repartidor repartidor3 = new Repartidor("Carlos", zonaDeCarga);

                executor.submit(repartidor1);
                executor.submit(repartidor2);
                executor.submit(repartidor3);

                executor.shutdown();

                while(!executor.isTerminated()) {

                    try {
                        Thread.sleep(200);

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                return null;
            }

            @Override
            protected void done() {

                JOptionPane.showMessageDialog(
                        VentanaPrincipal.this,
                        "Todos los pedidos pendientes fueron procesados.",
                        "SpeedFast",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        };

        worker.execute();
    }
}
