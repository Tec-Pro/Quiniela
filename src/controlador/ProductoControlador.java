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
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import models.Producto;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author eze
 */
public class ProductoControlador implements ActionListener, CellEditorListener {

    private ProductoGUI view;
    private ABMProducto abmp;
    private DefaultTableModel tablaProducto;
    private List<Producto> listaProd;

    public ProductoControlador(ProductoGUI producto) {
        this.view = producto;
        iniciar();
    }

    private void iniciar() {
        abmp = new ABMProducto();
        view.setVisible(true);
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
            row[2] = p.get("precio");
            row[3] = p.get("stock");
            row[4] = p.get("comision");
            row[5] = p.getString("diaSorteo");
            tablaProducto.addRow(row);
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
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Nuevo")) { //si presiono nuevo
                Object row[] = new Object[6];
                tablaProducto.addRow(row);
            }
            if (ae.getActionCommand().equals("Modificar")) { //si presiono guardar cambios en una fila
                BigDecimal b1,b2;
                String nombre, fecha;
                int stock;
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 2) != null){
                  b1 = new BigDecimal((Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 2));
                }
                else {
                    b1 = new BigDecimal(0);
                }
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 4) != null){
                    b2 = new BigDecimal((Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 4));
                }
                else{
                    b2 = new BigDecimal(0);
                }
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 3) != null){
                    stock = (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 3);
                }
                else {
                    stock = 0;
                }
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 1) != null){
                    nombre = (String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 1);
                }
                else {
                    nombre = "";
                }
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 5) != null){
                    fecha = (String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 5);
                }
                else {
                    fecha = "";
                }
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0) == null) {
                    abmp.altaProducto(nombre, b1, stock, b2, fecha);
                } else {
                    int id = (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0);
                    abmp.modificarProducto(id, nombre,
                            b1,stock, 
                            b2,fecha);
                }
                cargarProductos(); //actualizo la tabla de productos
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
