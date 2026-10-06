package view;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import model.Entrega;
import model.Pedido;
import model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaListaEntregas extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JComboBox<Object> cmbPedido;
    private JComboBox<Object> cmbRepartidor;

    private JButton btnActualizar;
    private JButton btnEditar;
    private JButton btnEliminar;

    private EntregaDAO entregaDAO;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;

    private DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public VentanaListaEntregas() {

        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        setTitle("Administrar Entregas - SpeedFast");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearComponentes();

        cargarPedidos();
        cargarRepartidores();
        cargarEntregas();
    }

    private void crearComponentes() {

        setLayout(new BorderLayout());

        // =========================
        // PANEL DE FILTROS
        // =========================

        JPanel panelFiltros = new JPanel(
                new GridLayout(1, 4, 10, 10)
        );

        panelFiltros.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JLabel lblPedido = new JLabel("Pedido:");

        cmbPedido = new JComboBox<>();

        JLabel lblRepartidor = new JLabel("Repartidor:");

        cmbRepartidor = new JComboBox<>();

        panelFiltros.add(lblPedido);
        panelFiltros.add(cmbPedido);
        panelFiltros.add(lblRepartidor);
        panelFiltros.add(cmbRepartidor);

        add(panelFiltros, BorderLayout.NORTH);

        // =========================
        // TABLA
        // =========================

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Pedido",
                        "Repartidor",
                        "Fecha y hora"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        // =========================
        // BOTONES
        // =========================

        JPanel panelBotones = new JPanel();

        btnActualizar = new JButton("Actualizar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // =========================
        // EVENTOS
        // =========================

        btnActualizar.addActionListener(
                e -> cargarEntregas()
        );

        btnEditar.addActionListener(
                e -> editarEntrega()
        );

        btnEliminar.addActionListener(
                e -> eliminarEntrega()
        );

        cmbPedido.addActionListener(
                e -> cargarEntregas()
        );

        cmbRepartidor.addActionListener(
                e -> cargarEntregas()
        );
    }

    // =====================================
    // CARGAR PEDIDOS EN EL COMBO
    // =====================================

    private void cargarPedidos() {

        cmbPedido.removeAllItems();

        // Primera opción: todos
        cmbPedido.addItem("Todos");

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        for (Pedido pedido : pedidos) {

            cmbPedido.addItem(pedido);
        }
    }

    // =====================================
    // CARGAR REPARTIDORES EN EL COMBO
    // =====================================

    private void cargarRepartidores() {

        cmbRepartidor.removeAllItems();

        // Primera opción: todos
        cmbRepartidor.addItem("Todos");

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        for (Repartidor repartidor : repartidores) {

            cmbRepartidor.addItem(repartidor);
        }
    }

    // =====================================
    // CARGAR ENTREGAS
    // =====================================

    private void cargarEntregas() {

        modeloTabla.setRowCount(0);

        Object pedidoSeleccionado =
                (Pedido) cmbPedido.getSelectedItem();

        Object repartidorSeleccionado =
                (Repartidor) cmbRepartidor.getSelectedItem();

        Integer idPedido = null;
        Integer idRepartidor = null;

        if (pedidoSeleccionado instanceof Pedido) {

            Pedido pedido = (Pedido) pedidoSeleccionado;

            idPedido =
                    pedido.getIdPedido();
        }

        if (repartidorSeleccionado instanceof Repartidor) {

            Repartidor repartidor = (Repartidor) repartidorSeleccionado;

            idRepartidor =
                    repartidor.getId();
        }

        List<String[]> entregas =
                entregaDAO.readAllDetalleFiltrado(
                        idPedido,
                        idRepartidor
                );

        for (String[] entrega : entregas) {

            modeloTabla.addRow(
                    new Object[]{
                            entrega[0],
                            entrega[1],
                            entrega[2],
                            entrega[3]
                    }
            );
        }
    }

    // =====================================
    // EDITAR ENTREGA
    // =====================================

    private void editarEntrega() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una entrega."
            );

            return;
        }

        int idEntrega =
                Integer.parseInt(
                        modeloTabla
                                .getValueAt(fila, 0)
                                .toString()
                );

        String fechaActual =
                modeloTabla
                        .getValueAt(fila, 3)
                        .toString();

        JComboBox<Pedido> comboPedido =
                new JComboBox<>();

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        for (Pedido pedido : pedidos) {

            comboPedido.addItem(pedido);
        }

        JComboBox<Repartidor> comboRepartidor =
                new JComboBox<>();

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        for (Repartidor repartidor : repartidores) {

            comboRepartidor.addItem(repartidor);
        }

        JTextField txtFecha =
                new JTextField(fechaActual);

        JPanel panel =
                new JPanel(
                        new GridLayout(3, 2, 10, 10)
                );

        panel.add(
                new JLabel("Pedido:")
        );

        panel.add(comboPedido);

        panel.add(
                new JLabel("Repartidor:")
        );

        panel.add(comboRepartidor);

        panel.add(
                new JLabel("Fecha y hora:")
        );

        panel.add(txtFecha);

        int resultado =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Editar entrega",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resultado != JOptionPane.OK_OPTION) {

            return;
        }

        Pedido pedido =
                (Pedido) comboPedido.getSelectedItem();

        Repartidor repartidor =
                (Repartidor)
                        comboRepartidor
                                .getSelectedItem();

        if (pedido == null ||
                repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un pedido y un repartidor."
            );

            return;
        }

        try {

            LocalDateTime fechaHora =
                    LocalDateTime.parse(
                            txtFecha.getText(),
                            formato
                    );

            Entrega entrega =
                    new Entrega(
                            idEntrega,
                            pedido.getIdPedido(),
                            repartidor.getId(),
                            fechaHora
                    );

            if (entregaDAO.update(entrega)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Entrega actualizada correctamente."
                );

                cargarEntregas();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo actualizar la entrega.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe tener el formato:\n" +
                            "yyyy-MM-dd HH:mm",
                    "Fecha incorrecta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================
    // ELIMINAR ENTREGA
    // =====================================

    private void eliminarEntrega() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una entrega."
            );

            return;
        }

        int idEntrega =
                Integer.parseInt(
                        modeloTabla
                                .getValueAt(fila, 0)
                                .toString()
                );

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Seguro que deseas eliminar esta entrega?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta == JOptionPane.YES_OPTION) {

            if (entregaDAO.delete(idEntrega)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Entrega eliminada correctamente."
                );

                cargarEntregas();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar la entrega.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}