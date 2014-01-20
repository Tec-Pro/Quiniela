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
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Cliente;
import models.Producto;
import models.Transaccion;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author joako
 */
public class CajaControlador implements ActionListener {
    private CajaGUI view;
    private ABMCaja abmc;
    private ABMTransaccion model;
    private DefaultTableModel tablaArticulos;
    private DefaultTableModel tablaClientes;
    private DefaultTableModel tablaTrans;
    private List<Producto> listaProd;
    private List<Cliente> listaCliente;
    private List<Transaccion> listaTransaccion;
    int caja = Quiniela.id_caja;
    //JForm hijos
    
    DepoManual depoManual;
    RetManual retManual;
    
    public CajaControlador(CajaGUI caja, ABMTransaccion abmt){
        this.view = caja;
        this.model = abmt;
        iniciar();
    }
    
    private void iniciar(){
        view.enableAll();
        view.getBotTipVenta().addActionListener(this);
        view.getDepManual().addActionListener(this);
        view.getRetManual().addActionListener(this);
        view.getTotalField().addActionListener(this);
        view.getVentaOk().addActionListener(this);
        view.getTablaArticulos().addMouseListener(new MouseAdapter() {});
        view.getTablaCliente().addMouseListener(new MouseAdapter() {});
        tablaArticulos = view.getTablaArtDef();
        tablaClientes = view.getTablaCliDef();
        tablaTrans = view.getTablaTransDef();
        listaProd = Producto.findAll();
        listaCliente = Cliente.findAll();
        
        listaTransaccion = Transaccion.find("caja_id = ?", caja);
        cargarProductos();
        cargarCuentas();
        cargarTransacciones();
    }

    public void cargarProductos(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        tablaArticulos.setRowCount(0);
        Iterator<Producto> it = listaProd.iterator();
        while(it.hasNext()){
            Producto p = it.next();
            Object row[] = new Object[3];
            row[0] = p.get("id");
            row[1] = p.getString("nombre");
            row[2] = p.get("stock");
            tablaArticulos.addRow(row);
        }
        Base.close();
    }
    
    public void cargarCuentas(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        tablaClientes.setRowCount(0);
        Iterator<Cliente> it = listaCliente.listIterator();
        while(it.hasNext()){
            Cliente c = it.next();
            Object row[] = new Object[2];
            row[0] = c.get("id");
            row[1] = c.getString("nombre")+" "+c.getString("apellido");
            tablaClientes.addRow(row);
        }
        Base.close();
        
    }
    
    public void cargarTransacciones(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        tablaTrans.setRowCount(0);
        listaTransaccion = Transaccion.find("caja_id = ?", caja);
        System.out.println(listaTransaccion);
        System.out.println(Quiniela.id_caja);
        Iterator<Transaccion> it = listaTransaccion.iterator();
        while(it.hasNext()){
            Transaccion t = it.next();
            Object row[] = new Object[4];
            row[0] = t.get("id");
            row[1] = t.getString("motivo");
            row[2] = t.getString("tipo");
            row[3] = Double.parseDouble(t.getString("monto"));
            tablaTrans.addRow(row);
        }
        Base.close();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("DEPOSITO MANUAL")){
            depoManual = new DepoManual();
            depoManualControlador dmc = new depoManualControlador(depoManual, model);
        } else if (comando.equals("RETIRO MANUAL")){
            retManual = new RetManual();
            retManualControlador rmc = new retManualControlador(retManual, model);
        } else if (comando.equals("CAMBIA TIPO")){
            if (view.getBotTipVenta().getText().equals("Cuenta Corriente")){
                view.getBotTipVenta().setText("Contado");
                view.getContTabCli().setVisible(true);
            } else {
                view.getBotTipVenta().setText("Cuenta Corriente");
                view.getContTabCli().setVisible(false);
            }
            
            
        }
        System.out.println("Carga la movida");
        cargarTransacciones();
    }
   
}
