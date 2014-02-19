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
import javax.swing.JOptionPane;
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
        getView().getQuitar().addActionListener(this);
        getView().getInsertar().addActionListener(this);
        getView().getTablaStockFecha().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    getView().getQuitar().setEnabled(true);
                }
            }
        });
        getView().getProdActualizar().addActionListener(this);
        getView().getProdEliminar().addActionListener(this);
        getView().getProdModificar().addActionListener(this);
        getView().getProdNuevo().addActionListener(this);
        getView().getTablaProductos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    getView().getProdModificar().setEnabled(true);
                    getView().getProdEliminar().setEnabled(true);
                    if (tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(),0)!= null){
                        cargarFecha((int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(),0));
                    }
                }
            }
        });
        tablaProducto = getView().getTablaProductosDef();
        tablaFecha = getView().getTablaStockFechaDef();
        cargarProductos();
    }

    public void cargarProductos() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
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
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
        }
        if (!(tablaProducto.getValueAt(view.getTablaProductos().getSelectedRow(),4).equals(Boolean.FALSE))){
            getView().getInsertar().setEnabled(true);
            getView().getQuitar().setEnabled(true);
            tablaFecha.setRowCount(0);
            listaFS = Fecha.where("producto_id = ?", id);
            Iterator<Fecha> itr = listaFS.iterator();
            while (itr.hasNext()) {
                Fecha p = itr.next();
                Object row[] = new Object[1];
                row[0] = p.getString("diaDeposito");
                tablaFecha.addRow(row);
            }
            tablaFecha.addRow(new Object[1]);
        }
        else{
            tablaFecha.setRowCount(0);
            getView().getInsertar().setEnabled(false);
            getView().getQuitar().setEnabled(false);
        }
        

        if (Base.hasConnection()) {
            Base.close();
        }

    }

    public void setCellEditor() {
        for (int i = 0; i < getView().getTablaProductos().getRowCount(); i++) {
            getView().getTablaProductos().getCellEditor(i, 2).addCellEditorListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
            if (ae.getActionCommand().equals("Actualizar")) { //si presiono actualizar
                cargarProductos(); //actualizo la tabla de productos
            }
            if (ae.getActionCommand().equals("Quitar")) { //si presiono quitar
                abmp.bajaFecha((int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(), 0),
                        (String) tablaFecha.getValueAt(getView().getTablaStockFecha().getSelectedRow(), 0)); 
                cargarFecha((int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(), 0));
            }
            if (ae.getActionCommand().equals("Eliminar")) { //si presiono eliminar
                abmp.bajaProducto((int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(), 0)); //saco el id de la fila en la primer columna
                cargarProductos();
            }
            if (ae.getActionCommand().equals("Nuevo")) { //si presiono nuevo
                Object row[] = new Object[6];//creo una fila nueva vacia
                getView().getProdModificar().setEnabled(true);
                tablaProducto.addRow(row);
            }
            if (ae.getActionCommand().equals("Modificar")) { //si presiono guardar cambios en una fila
                BigDecimal b1, b2;
                String nombre;
                int hayStock;
                int stock;
                for (int j=0;j<tablaProducto.getRowCount(); j++){
                //tomo el nombre de la tabla
                if (tablaProducto.getValueAt(j, 1) != null) {
                    nombre = (String) tablaProducto.getValueAt(j, 1);
                } else {
                    nombre = "";
                    System.out.println("El nombre no puede ser vacio");
                }
                //tomo el precio de la tabla
                if (tablaProducto.getValueAt(j, 2) != null) {
                    b1 = new BigDecimal((Double) tablaProducto.getValueAt(j, 2));
                } else {
                    b1 = new BigDecimal(0);
                }
                //tomo la comision de la tabla
                if (tablaProducto.getValueAt(j, 3) != null) {
                    b2 = new BigDecimal((Double) tablaProducto.getValueAt(j, 3));
                } else {
                    b2 = new BigDecimal(0);
                }
                //tomo el valor del checkbox de la tabla(si hay o no stock)
                if (tablaProducto.getValueAt(j, 4) == null || (boolean) tablaProducto.getValueAt(j, 4) == false) {
                    hayStock = 0;
                } else {
                    hayStock = 1;
                }
                if (tablaProducto.getValueAt(j, 5) != null) {
                    stock = new Integer((Integer)tablaProducto.getValueAt(j, 5));
                } else {
                    stock = new Integer(0);
                }
                //Si el producto es nuevo(no tiene id)
                if (tablaProducto.getValueAt(j, 0) == null) {
                    abmp.altaProducto(nombre, b1, b2, hayStock,stock);
                } else { // si el producto existe y solo sera modificado
                    int id = (int) tablaProducto.getValueAt(j, 0);
                    abmp.modificarProducto(id, nombre, b1, b2, hayStock,stock);
                }
                }
                cargarProductos(); //actualizo la tabla de productos
                JOptionPane.showMessageDialog(getView(), "Cambios guardados con exito!");
            }
            if (ae.getActionCommand().equals("Insertar")) { //si presiono insertar
                //cargo stock y fecha de la tabla junto con el id del producto
                String diaDepo=(String) tablaFecha.getValueAt(getView().getTablaStockFecha().getSelectedRow(), 0);
                diaDepo=diaDepo.toLowerCase();
                if ("lunes".equals(diaDepo) || "martes".equals(diaDepo) || "miercoles".equals(diaDepo) || "jueves".equals(diaDepo) || "viernes".equals(diaDepo) ||  "sabado".equals(diaDepo) || "domingo".equals(diaDepo)  ){
                        abmp.altaFecha(
                            (int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(), 0),
                            (String) tablaFecha.getValueAt(getView().getTablaStockFecha().getSelectedRow(), 0));
                        cargarFecha((int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(), 0));
                        JOptionPane.showMessageDialog(getView(), "Dia cargado con exito!");
                }
                else {
                    cargarFecha((int) tablaProducto.getValueAt(getView().getTablaProductos().getSelectedRow(), 0));
                    JOptionPane.showMessageDialog(getView(), "El dia esta mal escrito!");
                }
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

    /**
     * @return the view
     */
    public ProductoGUI getView() {
        return view;
    }


}