package view;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import model.Entrega;
import model.Pedido;
import model.Repartidor;
import model.EstadoPedido;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class VentanaRegistroEntrega extends JFrame {

    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;
    private EntregaDAO entregaDAO;

    public VentanaRegistroEntrega() {

        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();
        entregaDAO = new EntregaDAO();

        setTitle("Registrar Entrega - SpeedFast");
        setSize(500, 250);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearComponentes();

        cargarPedidos();
        cargarRepartidores();
    }

    private void crearComponentes() {

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblPedido = new JLabel("Pedido:");
        JLabel lblRepartidor = new JLabel("Repartidor:");

        cmbPedido = new JComboBox<>();
        cmbRepartidor = new JComboBox<>();

        btnGuardar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        panel.add(lblPedido);
        panel.add(cmbPedido);

        panel.add(lblRepartidor);
        panel.add(cmbRepartidor);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> guardarEntrega());
        btnCancelar.addActionListener(e -> dispose());
    }

    /**
     * Carga los pedidos existentes desde MySQL.
     */
    private void cargarPedidos() {

        cmbPedido.removeAllItems();

        List<Pedido> pedidos = pedidoDAO.readAll();

        for (Pedido pedido : pedidos) {

            cmbPedido.addItem(pedido);
        }
    }

    /**
     * Carga los repartidores existentes desde MySQL.
     */
    private void cargarRepartidores() {

        cmbRepartidor.removeAllItems();

        List<Repartidor> repartidores = repartidorDAO.readAll();

        for (Repartidor repartidor : repartidores) {

            cmbRepartidor.addItem(repartidor);

        }
    }

    /**
     * Registra la entrega.
     */
    private void guardarEntrega() {

        Pedido pedido = (Pedido) cmbPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidor.getSelectedItem();

        if (pedido == null) {

            JOptionPane.showMessageDialog(this, "Debes seleccionar un pedido");

            return;
        }

        if (repartidor == null) {

            JOptionPane.showMessageDialog(this, "Debes seleccionar un repartidor");

            return;
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {

            JOptionPane.showMessageDialog(this, "El pedido seleccionado no esta PENDIENTE.\n" + "Estado actual: " + pedido.getEstado());

            return;
        }

        LocalDateTime fechaHora = LocalDateTime.now();

        Entrega entrega = new Entrega(0, pedido.getIdPedido(), repartidor.getId(), fechaHora);

        if (entregaDAO.create(entrega)) {

            PedidoDAO pedidoDAO = new PedidoDAO();

            pedidoDAO.actualizarEstado(
                    pedido.getIdPedido(),
                    model.EstadoPedido.EN_REPARTO
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega registrada correctamente.\n" +
                            "El pedido ahora está EN_REPARTO.\n\n" +
                            "ID entrega: " + entrega.getId()
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
