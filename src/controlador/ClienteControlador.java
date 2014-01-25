/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCliente;
import abm.ABMTransaccion;
import interfaz.BorrarCliente;
import interfaz.ClienteGUI;
import interfaz.ClienteTransaccion;
import interfaz.CrearCliente;
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
    private ClienteGUI view;
    private ABMCliente abmC;
    private ABMTransaccion abmT;
    private List<Cliente> listaClientes;

    public ClienteControlador(ClienteGUI clienteGui){
        this.view = clienteGui;
        this.abmC = new ABMCliente();
        iniciar();
    }
    
    private void iniciar(){
        view.setVisible(true);
        view.getBotonAgregar().addActionListener(this);
        view.getBotonEliminar().addActionListener(this);
        view.getBotonTransacciones().addActionListener(this);
        view.getTablaClientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    if (target.getSelectedColumn() > 3){
                        setCellEditor();
                        actualizarDeberSaldoHaber(target);
                    }
                }
            }
        });
        tablaClientes = view.getTablaClienteDef();
        cargarClientes();
    }
    
    

    private void cargarClientes() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
        tablaClientes.setRowCount(0);
        listaClientes = Cliente.where("visible = ?", 1);
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
    
    
    public void actualizarDeberSaldoHaber(JTable tabla){
        int id = (int) tabla.getValueAt(tabla.getSelectedRow(), 0);
        BigDecimal deber = (BigDecimal) tabla.getValueAt(tabla.getSelectedRow(), 4);
        BigDecimal saldo = (BigDecimal) tabla.getValueAt(tabla.getSelectedRow(), 5);
        BigDecimal haber = (BigDecimal) tabla.getValueAt(tabla.getSelectedRow(), 6);
        if (!Base.hasConnection()) {
                abrirBase();
        }
        abmC.modificarCliente(id, deber, saldo, haber);
        
        if (Base.hasConnection()) {
            Base.close();
        }
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }
    
    
    public void setCellEditor() {
        for (int i = 0; i < view.getTablaClientes().getRowCount(); i++) {
            view.getTablaClientes().getCellEditor(i, 4).addCellEditorListener((CellEditorListener) this);
            view.getTablaClientes().getCellEditor(i, 5).addCellEditorListener((CellEditorListener) this);
            view.getTablaClientes().getCellEditor(i, 6).addCellEditorListener((CellEditorListener) this);
        }
    }
    
    
    public void editingStopped(ChangeEvent ce) {
        JTable target = (JTable) ce.getSource();
        actualizarDeberSaldoHaber(target);      
    }

    
    public void editingCanceled(ChangeEvent ce) {
        JTable target = (JTable) ce.getSource();
        actualizarDeberSaldoHaber(target);      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Agregar":
                CrearCliente cc = new CrearCliente();
                CrearClienteControlador ccc = new CrearClienteControlador(cc, abmC);
                                
            case "Eliminar":
                if (view.getTablaClientes().getSelectedRow() > 0){
                    BorrarCliente bc = new BorrarCliente();
                    BorrarClienteControlador bcc = new BorrarClienteControlador(bc, abmC,(int) view.getTablaClientes().getValueAt(view.getTablaClientes().getSelectedRow(), 0));  
                }
            case "Transacciones":
                if (view.getTablaClientes().getSelectedRow() > 0){
                    ClienteTransaccion ct = new ClienteTransaccion();
                    ClienteTransaccionControlador ctc = new ClienteTransaccionControlador(ct, (int) view.getTablaClientes().getValueAt(view.getTablaClientes().getSelectedRow(), 0));
                    ctc.run((String) tablaClientes.getValueAt(view.getTablaClientes().getSelectedRow(), 1)+" "+tablaClientes.getValueAt(view.getTablaClientes().getSelectedRow(), 1)); 
                }
        }
    }
}
