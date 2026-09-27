package view;

import dao.RepartidorDAO;
import model.Repartidor;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {

    private JTextField txtNombre;
    private JButton btnGuardar;

    public VentanaRegistroRepartidor() {

        setTitle("Registrar Repartidor");
        setSize(350, 180);
        setLocationRelativeTo(null);

        crearInterfaz();
    }

    private void crearInterfaz() {

        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Nombre:"));

        txtNombre = new JTextField();

        add(txtNombre);

        btnGuardar = new JButton("Guardar");

        add(new JLabel(""));

        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarRepartidor());
    }

    private void guardarRepartidor() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Ingrese el nombre del repartidor.");

            return;
        }

        Repartidor repartidor = new Repartidor(nombre);

        RepartidorDAO dao = new RepartidorDAO();

        if (dao.guardar(repartidor)) {

            JOptionPane.showMessageDialog(this, "Repartidor guardado correctamente.");

            txtNombre.setText("");

        } else {

            JOptionPane.showMessageDialog(this, "No se pudo guardar el repartidor.");
        }
    }
}
