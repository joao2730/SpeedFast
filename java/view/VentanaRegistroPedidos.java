package view;

import controller.ControladorPedidos;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedidos extends JFrame {

    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;

    private JComboBox<String> comboTipo;

    private JButton btnGuardar;

    private ControladorPedidos controlador;

    public VentanaRegistroPedidos(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("Registrar Pedido");
        setSize(400, 350);
        setLocationRelativeTo(null);

        crearInterfaz();
    }

    private void crearInterfaz() {

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("ID:"));

        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Direccion:"));

        txtDireccion = new JTextField();
        add(txtDireccion);

        add(new JLabel("Distancia (km):"));

        txtDistancia = new JTextField();
        add(txtDistancia);

        add(new JLabel("Tipo:"));

        comboTipo = new JComboBox<>();

        comboTipo.addItem("Comida");
        comboTipo.addItem("Encomienda");
        comboTipo.addItem("Express");

        add(comboTipo);

        btnGuardar = new JButton("Guardar pedido");

        add(new JLabel(""));
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarPedido());
    }

    private void guardarPedido() {

        try {

            String idTexto = txtId.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String distanciaTexto = txtDistancia.getText().trim();

            if (idTexto.isEmpty() || direccion.isEmpty() || distanciaTexto.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos.");

            return;
        }

            int id = Integer.parseInt(idTexto);

            double distancia = Double.parseDouble(distanciaTexto);

            if (id <= 0) {

            JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0.");

            return;
        }

            if (distancia < 0) {

                JOptionPane.showMessageDialog(this, "La distancia no puede ser negativa.");

                return;
            }

            if (controlador.existePedido(id)) {

                JOptionPane.showMessageDialog(this, "Ya existe un pedido con ese ID.");

                return;
            }

            String tipo = comboTipo.getSelectedItem().toString();

            Pedido pedido;

            if (tipo.equals("Comida")) {

                pedido = new PedidoComida(
                        id,
                        direccion,
                        distancia
                );
            } else if (tipo.equals("Encomienda")) {

                pedido = new PedidoEncomienda(
                        id,
                        direccion,
                        distancia
                );
            } else {

                pedido = new PedidoExpress(
                        id,
                        direccion,
                        distancia
                );
            }

            controlador.agregarPedido(pedido);

            JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");

            limpiarCampos();
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "ID y distancia deben ser numeros validos.");
        }
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtDireccion.setText("");
        txtDistancia.setText("");

        comboTipo.setSelectedIndex(0);
    }
}
