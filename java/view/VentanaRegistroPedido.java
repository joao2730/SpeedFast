package view;

import controller.ControladorPedidos;
import dao.PedidoDAO;
import model.*;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private JTextField txtDireccion;
    private JComboBox<String> comboTipo;
    private JComboBox<EstadoPedido> comboEstado;

    private PedidoDAO pedidoDAO;

    public VentanaRegistroPedido() {

        pedidoDAO = new PedidoDAO();

        setTitle("Registrar Pedido - SpeedFast");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearComponentes();
    }

    /**
     * Crea los componentes de la ventana.
     */
    private void crearComponentes() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtDireccion = new JTextField();

        comboTipo = new JComboBox<>(new String[]{
                "COMIDA",
                "ENCOMIENDA",
                "EXPRESS"
        });

        comboEstado = new JComboBox<>(EstadoPedido.values());

        JButton btnGuardar = new JButton("Guardar pedido");
        JButton btnLimpiar = new JButton("Limpiar");

        panel.add(new JLabel("Direccion de entrega:"));
        panel.add(txtDireccion);

        panel.add(new JLabel("Tipo de pedido:"));
        panel.add(comboTipo);

        panel.add(new JLabel("Estado:"));
        panel.add(comboEstado);

        panel.add(btnGuardar);
        panel.add(btnLimpiar);

        add(panel);

        btnGuardar.addActionListener(e -> guardarPedido());
        btnLimpiar.addActionListener(e -> {
            txtDireccion.setText("");
            comboTipo.setSelectedIndex(0);
            comboEstado.setSelectedItem(EstadoPedido.PENDIENTE);
        });
    }

    private void guardarPedido() {

        String direccion = txtDireccion.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) comboEstado.getSelectedItem();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Debe ingresar la direccion de entrega.");

            return;
        }

        Pedido pedido;

        switch (tipo) {

            case "COMIDA":

                pedido = new PedidoComida(0, direccion);

                break;

            case "ENCOMIENDA":

                pedido = new PedidoEncomienda(0, direccion);

                break;

            case "EXPRESS":

                pedido = new PedidoExpress(0, direccion);

                break;

            default:

                JOptionPane.showMessageDialog(this, "Selecciona un tipo valido.");

                return;
        }

        pedido.setEstado(estado);

        if (pedidoDAO.create(pedido)) {

            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente. ID:" + pedido.getIdPedido());

            txtDireccion.setText("");
            comboTipo.setSelectedIndex(0);
            comboEstado.setSelectedItem(EstadoPedido.PENDIENTE);

        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
