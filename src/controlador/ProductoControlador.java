/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.*;
import interfaz.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import models.Fecha;
import models.Producto;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class ProductoControlador implements ActionListener, CellEditorListener {

    private ProductoGUI view; //ventana ProductoGUI
    private ABMProducto abmp;
    private DefaultTableModel tablaProducto;
    private DefaultTableModel tablaFechaStock;
    private List<Producto> listaProd;
    private List<Fecha> listaFS;

    public ProductoControlador(ProductoGUI producto) {
        this.view = producto;
        iniciar();
    }

    private void iniciar() {
        abmp = new ABMProducto();
        view.setVisible(true);
        view.getQuitar().addActionListener(this);
        view.getInsertar().addActionListener(this);
        view.getTablaStockFecha().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    view.getQuitar().setEnabled(true);
                }
            }
        });
        view.getProdActualizar().addActionListener(this);
        view.getProdEliminar().addActionListener(this);
        view.getProdModificar().addActionListener(this);
        view.getProdNuevo().addActionListener(this);
        view.getTablaProductos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    view.getProdModificar().setEnabled(true);
                }
                if (e.getClickCount() == 1) {
                    view.getProdEliminar().setEnabled(true);
                }
            }
        });
        tablaProducto = view.getTablaProductosDef();
        tablaFechaStock = view.getTablaStockFechaDef();
        cargarProductos();
    }

    public void cargarProductos() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "");
        }
        tablaProducto.setRowCount(0);
        listaProd = Producto.where("visible = ?", 1);
        Iterator<Producto> itr = listaProd.iterator();
        while (itr.hasNext()) {
            Producto p = itr.next();
            Object row[] = new Object[6];
            row[0] = p.get("id");
            row[1] = p.getString("nombre");
            row[2] = p.getBigDecimal("precio").doubleValue();
            row[3] = p.getBigDecimal("comision").doubleValue();
            if (p.getInteger("hayStock") == 0) {
                row[4] = false;
            } else {
                row[4] = true;
            }
            tablaProducto.addRow(row);

        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    //no funciona
    public void cargarStockFecha() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "");
        }
        tablaFechaStock.setRowCount(0);
        tablaProducto.setRowCount(0);
        int id = (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(),0);
        listaFS = Fecha.where("producto_id = ?", id);
        Iterator<Fecha> itr = listaFS.iterator();
        while (itr.hasNext()) {
            Fecha p = itr.next();
            Object row[] = new Object[2];
            row[0] = p.getInteger("stock");
            row[1] = p.getString("diaSorteo");
            tablaFechaStock.addRow(row);
        }
        if (Base.hasConnection()) {
            Base.close();
        }

    }

    public void setCellEditor() {
        for (int i = 0; i < view.getTablaProductos().getRowCount(); i++) {
            view.getTablaProductos().getCellEditor(i, 2).addCellEditorListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "");
            if (ae.getActionCommand().equals("Actualizar")) { //si presiono actualizar
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Eliminar")) { //si presiono eliminar
                abmp.bajaProducto((int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0)); //saco el id de la fila en la primer columna
                cargarProductos();
            }
            if (ae.getActionCommand().equals("Nuevo")) { //si presiono nuevo
                Object row[] = new Object[6];//creo una fila nueva vacia
                tablaProducto.addRow(row);
            }
            if (ae.getActionCommand().equals("Modificar")) { //si presiono guardar cambios en una fila
                BigDecimal b1, b2;
                String nombre;
                int hayStock;
                //tomo el nombre de la tabla
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 1) != null) {
                    nombre = (String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 1);
                } else {
                    nombre = "";
                    System.out.println("El nombre no puede ser vacio");
                }
                //tomo el precio de la tabla
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 2) != null) {
                    b1 = new BigDecimal((Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 2));
                } else {
                    b1 = new BigDecimal(0);
                }
                //tomo la comision de la tabla
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 3) != null) {
                    b2 = new BigDecimal((Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 3));
                } else {
                    b2 = new BigDecimal(0);
                }
                //tomo el valor del checkbox de la tabla(si hay o no stock)
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 4) == null
                        || (boolean) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 4) == false) {
                    hayStock = 0;
                } else {
                    hayStock = 1;
                }
                //Si el producto es nuevo(no tiene id)
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0) == null) {
                    abmp.altaProducto(nombre, b1, b2, hayStock);
                } else { // si el producto existe y solo sera modificado
                    int id = (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0);
                    abmp.modificarProducto(id, nombre, b1, b2, hayStock);
                }
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Insertar")) { //si presiono insertar
                //cargo stock y fecha de la tabla junto con el id del producto
                abmp.altaStockFecha(
                        (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0),
                        (int) tablaFechaStock.getValueAt(view.getTablaStockFecha().getSelectedRow(), 0),
                        (String) tablaFechaStock.getValueAt(view.getTablaStockFecha().getSelectedRow(), 1));
            }
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    @Override
    public void editingStopped(ChangeEvent ce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editingCanceled(ChangeEvent ce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
