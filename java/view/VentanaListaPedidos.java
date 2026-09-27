package view;

import controller.ControladorPedidos;
import dao.PedidoDAO;
import model.Pedido;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private JTable tabla;

    private DefaultTableModel modelo;

    private JButton btnActualizar;
    private JButton btnCerrar;

    private PedidoDAO pedidoDAO;

    public VentanaListaPedidos() {

        pedidoDAO = new PedidoDAO();

        configurarVentana();
        crearComponentes();
        cargarPedidos();

    }

    /**
     * Configura la ventana.
     */
    private void configurarVentana() {

        setTitle("Lista de Pedidos - SpeedFast");

        setSize(800, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    /**
     * Crea la tabla y los botones.
     */
    private void crearComponentes() {

        /**
         * Columnas de la tabla.
         */
        String[] columnas = {
                "ID",
                "Direccion",
                "Tipo",
                "Distancia (km)",
                "Tiempo (min)",
                "Estado",
        };

        /**
         * Modelo de la tabla.
         */
        modelo = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int fila, int column) {

                return false;
            }
        };

        /**
         * Crear tabla.
         */
        tabla = new JTable(modelo);

        /**
         * Permitir desplazamiento.
         */
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);

        /**
         * Panel inferior.
         */
        JPanel panelBotones = new JPanel();

        btnActualizar = new JButton("Actualizar");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        /**
         * Eventos
         */
        btnActualizar.addActionListener(e -> cargarPedidos());
        btnCerrar.addActionListener(e -> dispose());
    }

    /**
     * Obtiene los pedidos desde MySQL
     * y los muestra en la tabla.
     */
    private void cargarPedidos() {

        /**
         * Limpiar la tabla antes de cargar
         * nuevamente los datos.
         */
        modelo.setRowCount(0);

        /**
         * Obtener pedidos de MySQL.
         */
        List<Pedido> pedidos = pedidoDAO.listarTodos();

        /**
         * Recorrer los pedidos.
         */
        for (Pedido pedido : pedidos) {

            Object[] fila = {

                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipo(),
                    pedido.getDistanciaKm(),
                    pedido.calcularTiempoEntrega(),
                    pedido.getEstado(),
            };

            modelo.addRow(fila);
        }
    }
}
