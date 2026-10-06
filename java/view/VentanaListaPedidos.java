package view;

import controller.ControladorPedidos;
import dao.PedidoDAO;
import model.*;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JComboBox<String> cmbFiltroTipo;
    private JComboBox<String> cmbFiltroEstado;

    private PedidoDAO pedidoDAO;

    public VentanaListaPedidos() {

        pedidoDAO = new PedidoDAO();

        setTitle("Administrar Pedidos - SpeedFast");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearComponentes();
        cargarPedidos();

    }

    private void crearComponentes() {

        setLayout(new BorderLayout(10, 10));

        JPanel panelFiltros = new JPanel();

        cmbFiltroTipo = new JComboBox(new String[]{
                "TODOS", "COMIDA", "ENCOMIENDA", "EXPRESS"
        });

        cmbFiltroEstado = new JComboBox(new String[]{
                "TODOS", "PENDIENTE", "EN_REPARTO", "ENTREGADO"
        });

        JButton btnFiltrar = new JButton("Filtrar");

        panelFiltros.add(new JLabel("Tipo: "));
        panelFiltros.add(cmbFiltroTipo);
        panelFiltros.add(new JLabel("Estado: "));
        panelFiltros.add(cmbFiltroEstado);
        panelFiltros.add(btnFiltrar);

        add(panelFiltros, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Direccion", "Tipo", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        btnFiltrar.addActionListener(e -> cargarPedidos());
        btnActualizar.addActionListener(e -> cargarPedidos());
        btnEditar.addActionListener(e -> editarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());

    }

    private void cargarPedidos () {

        modelo.setRowCount(0);

        List<Pedido> pedidos = pedidoDAO.readAll();

        String filtroTipo = (String) cmbFiltroTipo.getSelectedItem();
        String filtroEstado = (String) cmbFiltroEstado.getSelectedItem();

        for (Pedido pedido : pedidos) {

            boolean coincideTipo =
                    "TODOS".equals(filtroTipo) || pedido.getTipo().equals(filtroTipo);

            boolean coincideEstado =
                    "TODOS".equals(filtroEstado) || pedido.getEstado().name().equals(filtroEstado);

            if (coincideTipo && coincideEstado) {

                modelo.addRow(new Object[]{
                        pedido.getIdPedido(),
                        pedido.getDireccionEntrega(),
                        pedido.getTipo(),
                        pedido.getEstado()
                });
            }
        }
    }

    private void editarPedido () {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.");

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        String direccionActual = (String) modelo.getValueAt(fila, 1);
        String tipoActual = (String) modelo.getValueAt(fila, 2);
        String estadoActual = modelo.getValueAt(fila, 3).toString();

        JTextField txtDireccion = new JTextField(direccionActual);

        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{
                "COMIDA", "ENCOMIENDA", "EXPRESS"
        });
        cmbTipo.setSelectedItem(tipoActual);

        JComboBox<EstadoPedido> cmbEstado = new JComboBox<>(EstadoPedido.values());
        cmbEstado.setSelectedItem(EstadoPedido.valueOf(estadoActual));

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Direccion: "));
        panel.add(txtDireccion);
        panel.add(new JLabel("Tipo: "));
        panel.add(cmbTipo);
        panel.add(new JLabel("Estado: "));
        panel.add(cmbEstado);

        int respuesta = JOptionPane.showConfirmDialog(
                this, panel, "Editar Pedido", JOptionPane.OK_CANCEL_OPTION
        );

        if (respuesta == JOptionPane.OK_OPTION) {
            return;
        }

        String nuevaDireccion = txtDireccion.getText().trim();

        if (nuevaDireccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La direccion no puede estar vacia.");

            return;
        }

        String nuevoTipo = (String) cmbTipo.getSelectedItem();
        EstadoPedido nuevoEstado = (EstadoPedido) cmbEstado.getSelectedItem();

        Pedido pedido;

        switch (nuevoTipo) {
            case "COMIDA":
                pedido = new PedidoComida(id, nuevaDireccion);
                break;
            case "ENCOMIENDA":
                pedido = new PedidoEncomienda(id, nuevaDireccion);
                break;
            default:
                pedido = new PedidoExpress(id, nuevaDireccion);
                break;
        }

        pedido.setDireccionEntrega(nuevaDireccion);

        if (pedidoDAO.update(pedido)) {
            JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente.");

            cargarPedidos();

        }else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar pedido.");
        }
    }
    private void eliminarPedido () {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.");

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas eliminar el pedido seleccionado?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            if (pedidoDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");

                cargarPedidos();

            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el pedido." + "Puede tener entregas asociadas.");
            }
        }
    }




}