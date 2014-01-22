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

            if (ae.getActionCommand().equals("Eliminar")) { //si presiono eliminar
                abmp.bajaProducto((int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0)); //saco el id de la fila en la primer columna
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Nuevo")) { //si presiono nuevo
                Object row[] = new Object[6];
                tablaProducto.addRow(row);
            }
            if (ae.getActionCommand().equals("Modificar")) { //si presiono guardar cambios
                if (tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0) == null) {
                    abmp.altaProducto((String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 1), //Saco los valores nuevos de cada columna en esa fila para crear nuevo
                            (Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 2),
                            (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 3),
                            (Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 4),
                            (String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 5));
                } else {
                    abmp.modificarProducto((int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 0), //Saco los valores nuevos de cada columna en esa fila para modificar
                            (String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 1),
                            (Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 2),
                            (int) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 3),
                            (Double) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 4),
                            (String) tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(), 5));
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
