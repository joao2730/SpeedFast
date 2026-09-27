package view;

import controller.ControladorPedidos;
import dao.PedidoDAO;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private JTextField txtDireccion;
    private JTextField txtDistancia;

    private JComboBox<String> comboTipo;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private PedidoDAO pedidoDAO;

    public VentanaRegistroPedido() {

        pedidoDAO = new PedidoDAO();

        configurarVentana();
        crearComponentes();
    }

    /**
     * Configurar la ventana
     */
    private void configurarVentana() {

        setTitle("Registrar Pedido - SpeedFast");

        setSize(450, 300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Crea los componentes de la ventana.
     */
    private void crearComponentes() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Direccion

        JLabel lblDireccion = new JLabel("Direccion:");

        txtDireccion = new JTextField();

        // Distancia
        JLabel lblDistancia = new JLabel("Distancia (km):");
        txtDistancia = new JTextField();

        // Tipo

        JLabel lblTipo = new JLabel("Tipo de pedido:");

        comboTipo = new JComboBox<>();

        comboTipo.addItem("COMIDA");
        comboTipo.addItem("ENCOMIENDA");
        comboTipo.addItem("EXPRESS");

        // Botones

        btnGuardar = new JButton("Guardar Pedidos");

        btnCancelar = new JButton("Cancelar");

        panel.add(lblDireccion);
        panel.add(txtDireccion);

        panel.add(lblDistancia);
        panel.add(txtDistancia);

        panel.add(lblTipo);
        panel.add(comboTipo);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        /**
         * Eventos de los botones.
         */

        btnGuardar.addActionListener(e -> guardarPedido());
        btnCancelar.addActionListener(e -> dispose());
    }

    /**
     * Valida los datos y guarda el pedido
     * en MySQL mediante PedidoDAO.
     */
    private void guardarPedido() {

        String direccion = txtDireccion.getText().trim();
        String distanciaTexto = txtDistancia.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Debe ingresar una direccion.", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }
        /**
         * Validar distancia
         */
        if (distanciaTexto.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Debe ingresar la distancia.", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        double distancia;

        try {

            distancia = Double.parseDouble(distanciaTexto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "la distancia debe ser un numero.", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        /**
         * La distancia no puede ser negativa.
         */
        if (distancia < 0) {

            JOptionPane.showMessageDialog(this, "La distancia no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        /**
         * Crear el tipo de pedido
         * correspondiente.
         */
        Pedido pedido;

        switch (tipo) {

            case "COMIDA":

                pedido = new PedidoComida(0, direccion, distancia);

                break;

            case "ENCOMIENDA":

                pedido = new PedidoEncomienda(0, direccion, distancia);

                break;

            case "EXPRESS":

                pedido = new PedidoExpress(0, direccion, distancia);

                break;

            default:

                JOptionPane.showMessageDialog(this, "Tipo de pedido no valido.", "Error", JOptionPane.ERROR_MESSAGE);

                return;
        }

        /**
         * Guarda en MySQL
         */
        boolean guardado = pedidoDAO.guardar(pedido);

        if (guardado) {

            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente. \n" + "ID generado:" + pedido.getIdPedido(), "SpeedFast", JOptionPane.INFORMATION_MESSAGE);

        limpiarCampos();

    } else {

        JOptionPane.showMessageDialog(this, "No se puede guardar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void limpiarCampos() {

    txtDireccion.setText("");
    txtDistancia.setText("");

    comboTipo.setSelectedIndex(0);
}
}
