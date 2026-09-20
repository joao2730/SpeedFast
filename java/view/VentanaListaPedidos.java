package view;

import controller.ControladorPedidos;
import model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private ControladorPedidos controlador;

    private JTable tabla;

    private DefaultTableModel modelo;

    public VentanaListaPedidos(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("Lista de pedidos");
        setSize(800, 400);
        setLocationRelativeTo(null);

        crearInterfaz();
        cargarPedidos();
    }
    private void crearInterfaz() {

        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Direccion", "Tipo", "Distancia", "Tiempo", "Estado", "Repartidor"};

        modelo = new DefaultTableModel(columnas, 0);

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");

        btnActualizar.addActionListener(e -> cargarPedidos());

        add(btnActualizar, BorderLayout.SOUTH);
    }
    private void cargarPedidos() {

        modelo.setRowCount(0);

        for (Pedido pedido : controlador.getPedidos()) {

            Object[] fila = {

                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipo(),
                    pedido.getDistanciaKm() + " km",
                    pedido.calcularTiempoEntrega() + " min",
                    pedido.getEstado(),
                    pedido.getRepartidor()
            };

            modelo.addRow(fila);
        }
    }
}
