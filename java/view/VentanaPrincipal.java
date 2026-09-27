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

    private ControladorPedidos controlador;

    private JButton btnRegistrarPedido;
    private JButton btnListarPedidos;
    private JButton btnIniciarEntrega;
    private JButton btnRegistrarRepartidor;

    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;

    public VentanaPrincipal() {

        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {

        setTitle("SpeedFast - Sistema de Entregas");

        setSize(500, 350);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel("SPEEDFAST", SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));

        btnRegistrarPedido = new JButton("Registrar pedido");

        btnListarPedidos = new JButton("Listar pedidos");

        btnRegistrarRepartidor = new JButton("Registrar repartidor");

        btnIniciarEntrega = new JButton("Asignar repartidor / Iniciar entrega");

        panelBotones.add(btnRegistrarPedido);
        panelBotones.add(btnListarPedidos);
        panelBotones.add(btnRegistrarRepartidor);
        panelBotones.add(btnIniciarEntrega);

        add(panelBotones, BorderLayout.CENTER);

        /**
         * Eventos.
         */

        btnRegistrarPedido.addActionListener(e -> registrarPedido());
        btnListarPedidos.addActionListener(e -> listarPedidos());
        btnRegistrarRepartidor.addActionListener(e -> registrarRepartidor());
        btnIniciarEntrega.addActionListener(e -> iniciarEntrega());
    }

    private void registrarPedido() {

      VentanaRegistroPedido ventana = new VentanaRegistroPedido();

        ventana.setVisible(true);
    }

    private void listarPedidos() {

        VentanaListaPedidos ventana = new VentanaListaPedidos();

        ventana.setVisible(true);
    }

    private void registrarRepartidor() {

        VentanaRegistroRepartidor ventana = new VentanaRegistroRepartidor();

        ventana.setVisible(true);
    }

    /**
     * Inicia la entrega utilizando
     * Runnable y ExecutorService.
     */
    private void iniciarEntrega() {

        /**
         * Obtener pedidos directamente
         * desde MySQL.
         */
        List<Pedido> pedidos = pedidoDAO.listarTodos();

        if (pedidos.isEmpty()) {

            JOptionPane.showMessageDialog(this, "No existen pedidos registrados.");

            return;
        }

        /**
         * Obtener repartidores desde MySQL.
         */
        List<Repartidor> repartidores = repartidorDAO.listarTodos();

        if (repartidores.isEmpty()) {

            JOptionPane.showMessageDialog(this, "No existen repartidores registrados.");

            return;
        }

        /**
         * Crear zona de carga compartida
         */
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        /**
         * Agregar solamente pedidos
         * que esten pendientes
         */
        int cantidad = 0;

        for (Pedido pedido : pedidos) {

            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {

                zonaDeCarga.agregarPedido(pedido);

                cantidad++;
            }
        }

        if (cantidad == 0) {

            JOptionPane.showMessageDialog(this, "No existen pedidos pendientes.");

            return;
        }

        /**
         * Ejecutar repartidores.
         */
        ejecutarRepartidores(zonaDeCarga, repartidores);
    }

    private void ejecutarRepartidores(
            ZonaDeCarga zonaDeCarga,
            List<Repartidor> repartidores) {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {

                ExecutorService executor = Executors.newFixedThreadPool(repartidores.size());

                /**
                 * Cada repartidor
                 * utiliza Runnable.
                 */
                for (Repartidor repartidor : repartidores) {

                    repartidor.setZonaDeCarga(zonaDeCarga);

                    executor.submit(repartidor);
                }

                executor.shutdown();

                /**
                 * Esperar hasta que
                 * terminen los repartidores
                 */
                while (!executor.isTerminated()) {

                    try {

                        Thread.sleep(500);

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();

                        break;
                    }
                }

                return null;
            }

            @Override
            protected void done() {

                /**
                 * Actualizar los estados
                 */
                List<Pedido> pedidosActualizados = pedidoDAO.listarTodos();

                for (Pedido pedido : pedidosActualizados) {

                    pedidoDAO.actualizarEstado(pedido);
                }

                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Todas las entregas finalizaron.");
            }
        };

        worker.execute();
    }
}
