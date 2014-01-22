/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCliente;
import abm.ABMTransaccion;
import interfaz.ClienteGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Cliente;
import models.Transaccion;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author max
 */
public class ClienteControlador implements ActionListener {
    
    private DefaultTableModel tablaClientes;
    private DefaultTableModel tablaTransacciones;
    private ClienteGUI view;
    private ABMCliente abmC;
    private ABMTransaccion abmT;
    private List<Cliente> listaClientes;
    private List<Transaccion> listaTransacciones;

    public ClienteControlador(ClienteGUI clienteGui,ABMCliente abmC){
        this.view = clienteGui;
        this.abmC = abmC;
        iniciar();
    }
    
    public void iniciar(){
        view.getButtonAgregar().addActionListener(this);
        view.getButtonBorrar().addActionListener(this);
        view.getButtonTransacciones().addActionListener(this);
        view.getTablaCliente().addMouseListener(new MouseAdapter() {});
        
        tablaClientes = view.getTablaClienteDef();
        listaClientes = Cliente.findAll();
        cargarClientes();
    }
    
    

    private void cargarClientes() {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        tablaClientes.setRowCount(0);
        Iterator<Cliente> it = listaClientes.iterator();
        while(it.hasNext()){
            Cliente c = it.next();
            Object row[] = new Object[6];
            row[0] = c.get("id");
            row[1] = c.getString("nombre");
            row[2] = c.get("apellido");
            row[3] = c.get("deber");
            row[4] = c.get("saldo");
            row[5] = c.get("haber");
            tablaClientes.addRow(row);
        }
        Base.close();
    }
    
    public void cargarTransacciones(int id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        tablaTransacciones.setRowCount(0);
        listaTransacciones = Transaccion.find(" cliente_id = ?", id);
        System.out.println(listaTransacciones);
        System.out.println(Quiniela.id_caja);
        Iterator<Transaccion> it = listaTransacciones.iterator();
        while(it.hasNext()){
            Transaccion t = it.next();
            Object row[] = new Object[4];
            row[0] = t.get("id");
            row[1] = t.getString("motivo");
            row[2] = t.getString("tipo");
            row[3] = Double.parseDouble(t.getString("monto"));
            tablaTransacciones.addRow(row);
        }
        Base.close();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
