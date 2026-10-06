package view;

import dao.RepartidorDAO;
import model.Repartidor;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {

    private JTextField txtNombre;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    private RepartidorDAO repartidorDAO;

    public VentanaRegistroRepartidor() {

        repartidorDAO = new RepartidorDAO();

        setTitle("Registrar Repartidor - SpeedFast");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearComponentes();
    }

    private void crearComponentes() {

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblNombre = new JLabel("Nombre del repartidor:");
        txtNombre = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(btnGuardar);
        panel.add(btnLimpiar);

        add(panel);

        btnGuardar.addActionListener(e -> guardarRepartidor());
        btnLimpiar.addActionListener(e -> txtNombre.setText(""));
    }

    private void guardarRepartidor() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Debes ingresar el nombre del repartidor");

            return;
        }

        Repartidor repartidor = new Repartidor(nombre);

        if (repartidorDAO.create(repartidor)) {

            JOptionPane.showMessageDialog(this, "Repartidor registrado correctamente. ID: " + repartidor.getId());

            txtNombre.setText("");

        } else {

            JOptionPane.showMessageDialog(this, "No se pudo guardar el repartidor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
