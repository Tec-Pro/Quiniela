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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private DefaultTableModel tablaFecha;
    private List<Producto> listaProd;
    private List<Fecha> listaFS;
    private CajaControlador cc;

    public ProductoControlador(ProductoGUI producto) {
        this.view = producto;
        iniciar();
    }

    ProductoControlador(ProductoGUI productoGUI, CajaControlador cajaControlador) {
        this.view = productoGUI;
        this.cc = cajaControlador;
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
                if (e.getClickCount() == 1) {
                    view.getProdModificar().setEnabled(true);
                    view.getProdEliminar().setEnabled(true);
                    cargarFecha((int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(),0));
                }
            }
        });
        tablaProducto = view.getTablaProductosDef();
        tablaFecha = view.getTablaStockFechaDef();
        cargarProductos();
    }

    public void cargarProductos() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
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
                row[5] = p.getInteger("stock");
            }
            tablaProducto.addRow(row);

        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    //no funciona
    public void cargarFecha(int id) {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
        if (!(tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(),4).equals(Boolean.FALSE))){
            view.getInsertar().setEnabled(true);
            view.getQuitar().setEnabled(true);
            tablaFecha.setRowCount(0);
            listaFS = Fecha.where("producto_id = ?", id);
            Iterator<Fecha> itr = listaFS.iterator();
            while (itr.hasNext()) {
                Fecha p = itr.next();
                Object row[] = new Object[1];
                row[0] = p.getString("diaSorteo");
                tablaFecha.addRow(row);
            }
            tablaFecha.addRow(new Object[1]);
        }
        else{
            view.getInsertar().setEnabled(false);
            view.getQuitar().setEnabled(false);
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
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
            if (ae.getActionCommand().equals("Actualizar")) { //si presiono actualizar
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Quitar")) { //si presiono quitar
                abmp.bajaFecha((int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0),
                        (String) tablaFecha.getValueAt(view.getTablaStockFecha().getSelectedRow(), 1)); 
            }
            if (ae.getActionCommand().equals("Eliminar")) { //si presiono eliminar
                abmp.bajaProducto((int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0)); //saco el id de la fila en la primer columna
                cargarProductos();
            }
            if (ae.getActionCommand().equals("Nuevo")) { //si presiono nuevo
                Object row[] = new Object[6];//creo una fila nueva vacia
                view.getProdModificar().setEnabled(true);
                tablaProducto.addRow(row);
            }
            if (ae.getActionCommand().equals("Modificar")) { //si presiono guardar cambios en una fila
                BigDecimal b1, b2;
                String nombre;
                int hayStock;
                int stock;
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
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 5) != null) {
                    stock = new Integer((Integer)tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 5));
                } else {
                    stock = new Integer(0);
                }
                //Si el producto es nuevo(no tiene id)
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0) == null) {
                    abmp.altaProducto(nombre, b1, b2, hayStock,stock);
                } else { // si el producto existe y solo sera modificado
                    int id = (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0);
                    abmp.modificarProducto(id, nombre, b1, b2, hayStock,stock);
                }
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Insertar")) { //si presiono insertar
                //cargo stock y fecha de la tabla junto con el id del producto
                abmp.altaFecha(
                        (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0),
                        (String) tablaFecha.getValueAt(view.getTablaStockFecha().getSelectedRow(), 1));
            }
        }
        
        if (Base.hasConnection()) {
            Base.close();
        }
        cc.cargarProductos();
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