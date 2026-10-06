package view;

import dao.RepartidorDAO;
import model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaRepartidores extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private RepartidorDAO repartidorDAO;

    public VentanaListaRepartidores() {

        repartidorDAO = new RepartidorDAO();

        setTitle("Administrar Repartidores - SpeedFast");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearComponentes();
        cargarRepartidores();
    }

    private void crearComponentes() {

        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla),  BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones,  BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarRepartidores());
        btnEditar.addActionListener(e -> editarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());
    }

    private void cargarRepartidores() {

        modelo.setRowCount(0);

        List<Repartidor> repartidores = RepartidorDAO.readAll();

        for (Repartidor repartidor : repartidores) {
            modelo.addRow(new Object[]{repartidor.getId(), repartidor.getNombre()});
        }

    }

    private void editarRepartidor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla.");

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        String nombreActual = (String) modelo.getValueAt(fila, 1);

        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre: ", nombreActual);

        if (nuevoNombre == null) {
            return;
        }

        nuevoNombre = nuevoNombre.trim();

        if (nuevoNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");

            return;
        }

        Repartidor repartidor = new Repartidor(id, nuevoNombre);

        if (repartidorDAO.update(repartidor)) {
            JOptionPane.showMessageDialog(this, "Repartidor actualizado correctamente.");

            cargarRepartidores();

        } else  {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el repartidor.");
        }
    }

    private void eliminarRepartidor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un repartidor de la tabla.");

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                this, "¿Deseas eliminar este repartidor?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            if (repartidorDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Repartidor eliminado correctamente.");

                cargarRepartidores();

            } else  {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar. Comprueba si tiene " + "entregas asociadas.");
            }
        }
    }






}
