package com.alura.jdbc.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.controller.CategoriaController;
import com.alura.jdbc.controller.ProductoController;

public class ReporteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private DefaultTableModel modelo;

    private CategoriaController categoriaController;

    //private ProductoController productoController;

    public ReporteFrame(ControlDeStockFrame controlDeStockFrame) {
        super("Reporte de produtos del stock");

        this.categoriaController = new CategoriaController();
        //this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);

        tablaReporte = new JTable();
        tablaReporte.setBounds(0, 0, 600, 400);
        container.add(tablaReporte);

        modelo = (DefaultTableModel) tablaReporte.getModel();
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");

        cargaReporte();

        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(controlDeStockFrame);
    }

    //Método en ReporteFrae para generar el reporte
    private void cargaReporte() {
        var contenido = categoriaController.cargaReporte();

        //En cada fila agregamos una propia fila
        //Para cada categoría que tenemos nosotros vamos a hacer la búsqueda de los productos en el productoController para poder agregar acá en el listado también como una nueva fila
        contenido.forEach(categoria -> {
            modelo.addRow(new Object[]{categoria}); //fila con titulo categoria

            //var productos = this.productoController.listar(categoria); //listado de productos // tenemos acá la parte en donde vamos a productoController que va a productoDAO para buscar al listado de productos por la categoría
            //sacamos el productoController de acá del escenario y agregamos el listado de productos de la categoría.
            var productos = categoria.getProductos(); //tenemos acá la parte en donde vamos a productoController que va a productoDAO para buscar al listado de productos por la categoría

            productos.forEach(producto -> modelo.addRow(
                    new Object[]{
                            "",/*fila vacia*/
                            producto.getNombre(), //Columna para nombre
                            producto.getCantidad() //Columna para cantidad
                    }
            ));
        });
    }
}
