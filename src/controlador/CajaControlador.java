/*
 * To change this template, choose Tools | Templates
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
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import models.Caja;
import models.Cliente;
import models.Producto;
import models.ProductosTransaccions;
import models.Transaccion;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author joako
 */
public class CajaControlador implements ActionListener, CellEditorListener {

    private final CajaGUI view;
    private ABMCaja abmc;
    private final ABMTransaccion model;
    private final ABMCliente cliente;
    private DefaultTableModel tablaArticulos;
    private DefaultTableModel tablaClientes;
    private DefaultTableModel tablaTrans;
    private DefaultTableModel tablaDetalles;
    private List<Producto> listaProd;
    private List<Cliente> listaCliente;
    private List<Transaccion> listaTransaccion;
    private List<Caja> cajas;
    private int id_caja;
    private int id_cliente;
    private Cliente c;

    //JForm hijos
    DepoManual depoManual;
    RetManual retManual;
    ListaCajas listaCajas;

    public CajaControlador(CajaGUI caja) {
        this.view = caja;
        this.model = new ABMTransaccion();
        this.cliente = new ABMCliente();
        iniciar();
    }

    private void iniciar() {
        view.getDepManual().addActionListener(this);
        view.getRetManual().addActionListener(this);
        view.getTotalField().addActionListener(this);
        view.getVentaOk().addActionListener(this);
        tablaDetalles = view.getTablaDetDef();
        view.getTablaArticulos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (tablaDetalles.getRowCount() == 0) {
                        tablaDetalles.setRowCount(0);
                    }
                    JTable target = (JTable) e.getSource();
                    Object row[] = new Object[4];
                    row[0] = target.getValueAt(target.getSelectedRow(), 0);
                    row[1] = target.getValueAt(target.getSelectedRow(), 1);
                    row[2] = 1;
                    abrirBase();
                    Producto p = Producto.findById(target.getValueAt(target.getSelectedRow(), 0));
                    Double precio = Double.parseDouble(p.getString("precio"));
                    row[3] = (int) row[2] * precio;
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                    tablaDetalles.addRow(row);
                    view.getVentaOk().setEnabled(true);
                    setCellEditor();
                    actualizarPrecio();
                }
            }

        });
        view.getTablaDetalles().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable t = (JTable) e.getSource();
                    tablaDetalles.removeRow(t.getSelectedRow());
                    actualizarPrecio();
                }
                if (tablaDetalles.getRowCount() == 0) {
                    view.getVentaOk().setEnabled(false);
                }

            }
        });
        view.getTablaCliente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirBase();
                if (e.getClickCount() == 2) {
                    JTable t = (JTable) e.getSource();
                    c = Cliente.findById(t.getValueAt(t.getSelectedRow(), 0));
                    view.getClienteSel().setText(c.getString("nombre") + " " + c.getString("apellido"));
                    id_cliente = (int) t.getValueAt(t.getSelectedRow(), 0);
                }
                if (Base.hasConnection()) {
                    Base.close();
                }
            }

        });
        tablaArticulos = view.getTablaArtDef();
        tablaClientes = view.getTablaCliDef();
        tablaTrans = view.getTablaTransDef();
        cargarProductos();
        cargarCuentas();
        cargarTransacciones();
    }

    public void cargarProductos() {
        abrirBase();
        listaProd = Producto.findAll();
        tablaArticulos.setRowCount(0);
        Iterator<Producto> it = listaProd.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.get("visible").equals(1)) {
                Object row[] = new Object[3];
                row[0] = p.get("id");
                row[1] = p.getString("nombre");
                if (p.get("hayStock").equals(1)) {
                    row[2] = p.get("stock");
                }

                tablaArticulos.addRow(row);
            }
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    public void cargarCuentas() {
        abrirBase();
        tablaClientes.setRowCount(0);
        listaCliente = Cliente.findAll();
        Iterator<Cliente> it = listaCliente.iterator();
        while (it.hasNext()) {
            Cliente cli = it.next();
            Object row[] = new Object[2];
            row[0] = cli.get("id");
            row[1] = cli.getString("nombre") + " " + cli.getString("apellido");
            if ((cli.get("visible").equals(1))) {
                tablaClientes.addRow(row);
            }
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    public void cargarTransacciones() {
        abrirBase();
        System.out.println("Aca si");
        cajas = Caja.findAll();
        
            System.out.println(cajas);
        tablaTrans.setRowCount(0);
        if (cajas.size() > 0) {
            System.out.println(cajas);
            if (cajas.size()==1){
                id_caja = 1;
            } else {
                id_caja = cajas.get(cajas.size() - 1).getInteger("id");
            }listaTransaccion = Transaccion.where("caja_id = ?", id_caja);
            Iterator<Transaccion> it = listaTransaccion.iterator();
            view.getTotalVentas().setText("0.0");
            view.getTotalOtros().setText("0.0");
            while (it.hasNext()) {
                Transaccion t = it.next();
                String motivo[] = t.getString("motivo").split("; ");
                for (String mot : motivo) {
                    Object row[] = new Object[4];
                    row[0] = t.get("id");
                    row[1] = mot;
                    row[2] = t.getString("tipo");
                    row[3] = Double.parseDouble(t.getString("monto"));
                    tablaTrans.addRow(row);
                    if (row[2].equals("Venta") && t.get("cliente_id") == null) {
                        Double ventas = Double.parseDouble(view.getTotalVentas().getText()) + (Double) row[3];
                        view.getTotalVentas().setText(ventas.toString());
                    } else if (!row[2].equals("Venta") && t.get("cliente_id") != null || !row[2].equals("Venta")) {
                        Double otros = Double.parseDouble(view.getTotalOtros().getText()) + (Double) row[3];
                        view.getTotalOtros().setText(otros.toString());
                    }
                }
            }
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "DEPOSITO MANUAL":
                depoManual = new DepoManual();
                depoManualControlador dmc = new depoManualControlador(depoManual, model, this);
                break;
            case "RETIRO MANUAL":
                retManual = new RetManual();
                retManualControlador rmc = new retManualControlador(retManual, this);
                break;
            case "OK":
                if (tablaDetalles.getRowCount() > 0) {
                    abrirBase();
                    String motivo = "";
                    int rows = tablaDetalles.getRowCount();
                    Producto pr;
                    for (int i = 0; i < rows; i++) {
                        pr = Producto.findById(tablaDetalles.getValueAt(i, 0));
                        if (pr.get("hayStock").equals(1)) {
                            pr.set("stock", pr.getInteger("stock") - (Integer) tablaDetalles.getValueAt(i, 2));
                            pr.save();
                        }
                        motivo = motivo + tablaDetalles.getValueAt(i, 1) + " x" + tablaDetalles.getValueAt(i, 2) + "; ";
                    }
                    BigDecimal monto = BigDecimal.valueOf(Double.parseDouble(view.getTotalField().getText()));
                    if (!view.getClienteSel().getText().trim().isEmpty()) {
                        model.altaTransaccion(motivo, "Venta", monto, 1, id_caja, id_cliente, Quiniela.id_usuario);
                        Cliente cl = Cliente.findById(id_cliente);
                        cliente.modificarCliente(id_cliente, cl.getBigDecimal("deber").add(monto), cl.getBigDecimal("haber"));
                        view.getClienteSel().setText("");
                    } else {
                        model.altaTransaccion(motivo, "Venta", monto, 1, id_caja, Quiniela.id_usuario);
                    }
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                    Transaccion t = model.getLastTransaccion();
                    for (int j = 0; j < rows; j++) {
                        Producto p = Producto.findById(tablaDetalles.getValueAt(j, 0));
                        t.add(p);
                        ProductosTransaccions pt = model.getLastProdTrans();
                        pt.set("cantidad", tablaDetalles.getValueAt(j, 2));
                        pt.set("precio", p.get("precio"));
                        pt.set("comision", p.get("comision"));
                        pt.saveIt();
                    }
                    tablaDetalles.setRowCount(0);
                    actualizarPrecio();
                    cargarProductos();
                    view.getVentaOk().setEnabled(false);
                }
                break;
        }
        cargarTransacciones();
    }

    public void setCellEditor() {
        for (int i = 0; i < view.getTablaDetalles().getRowCount(); i++) {
            view.getTablaDetalles().getCellEditor(i, 2).addCellEditorListener(this);
        }
    }

    public void actualizarPrecio() {
        Double importe;
        Double total = 0.0;
        Double precio;
        for (int i = 0; i < view.getTablaDetalles().getRowCount(); i++) {
            abrirBase();
            precio = Double.parseDouble(Producto.findById(view.getTablaDetalles().getValueAt(i, 0)).getString("precio"));
            importe = Double.valueOf((Integer) view.getTablaDetalles().getValueAt(i, 2)) * precio;
            view.getTablaDetalles().setValueAt(importe, i, 3);
        }
        for (int i = 0; i < view.getTablaDetalles().getRowCount(); i++) {
            total = total + ((Double) view.getTablaDetalles().getValueAt(i, 3));
        }
        total = Math.round(total * 1000.00) / 1000.00;
        view.getTotalField().setText(total.toString());
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    @Override
    public void editingStopped(ChangeEvent ce) {
        actualizarPrecio();
    }

    @Override
    public void editingCanceled(ChangeEvent ce) {
        actualizarPrecio();
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
        }
    }
}
