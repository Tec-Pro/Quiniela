/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCliente;
import abm.ABMTransaccion;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import models.Cliente;
import models.Transaccion;
import org.javalite.activejdbc.Base;

/**
 *
 * @author max
 */
public class ClienteControlador implements ActionListener {
    
    private DefaultTableModel tablaClientes;
    private ClienteGUI view;
    private ABMCliente abmC;
    private ABMTransaccion abmT;
    private CajaControlador cc;
    private List<Cliente> listaClientes;
    private DefaultTableModel tablaTransacciones;
    private List<Transaccion> listaTransacciones;
    private ClienteTransaccion clienteT;
    private CrearCliente crearC;
    
    public ClienteControlador(ClienteGUI clienteGUI){
        this.view = clienteGUI;
        this.abmC = new ABMCliente();
    }
    
    public ClienteControlador(ClienteGUI clienteGUI, CajaControlador cc) {
        this.view = clienteGUI;
        this.abmC = new ABMCliente();
        this.cc = cc;
        iniciar();
    }
    
    private void iniciar(){
        view.setVisible(true);
        view.getBotonAgregar().addActionListener(this);
        view.getBotonEliminar().addActionListener(this);
        view.getBotonTransacciones().addActionListener(this);
        view.getButtonGuardar().addActionListener(this);
        view.getTablaClientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    if (target.getSelectedColumn() > 3){
                        setCellEditor();
                    }
                }
            }
        });
        tablaClientes = view.getTablaClienteDef();
        cargarClientes();
        
        //Ventana ClienteTransaccion
        clienteT = new ClienteTransaccion();
        clienteT.setVisible(false);
        
        clienteT.getButtonAceptar().addActionListener(this);
        tablaTransacciones = clienteT.getTablaTransaccionDef();
        
         //Ventana CrearCliente
        crearC = new CrearCliente();
        crearC.setVisible(false);
        
        crearC.getButtonCancelar().addActionListener(this);
        crearC.getButtonConfirmar().addActionListener(this);
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
            row[4] = c.get("haber");
            row[5] = c.get("saldo");
            tablaClientes.addRow(row);
        }
        Base.close();
    }
    
    private void cargarTransacciones(int id){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        tablaTransacciones.setRowCount(0);
        listaTransacciones = Transaccion.find(" cliente_id = ?", id);
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
    
    public void guardarCambios(DefaultTableModel tabla){
        if (!Base.hasConnection()) {
                abrirBase();
        }
        int i = 0;
        while(i<tabla.getColumnCount()){
            
        int id = (int) tabla.getValueAt(i, 0);
        BigDecimal deber = (BigDecimal) tabla.getValueAt(i, 4);
        BigDecimal saldo = (BigDecimal) tabla.getValueAt(i, 5);
        BigDecimal haber = (BigDecimal) tabla.getValueAt(i, 6);
        
        abmC.modificarCliente(id, deber, saldo, haber);
        }
        
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Agregar":
                crearC.setVisible(true);
                break;                
            case "Eliminar":
                if (view.getTablaClientes().getSelectedRow() > 0){
                    int confirmarBorrar;
                    confirmarBorrar = JOptionPane.showConfirmDialog(null,"¿Esta seguro que quiere borrar este cliente?","Confirmar",JOptionPane.YES_NO_OPTION);
                    if (confirmarBorrar == JOptionPane.YES_OPTION){
                        int idCliente =(int) view.getTablaClientes().getValueAt(view.getTablaClientes().getSelectedRow(), 0); 
                        if (!Base.hasConnection()) {
                            abrirBase();
                        }
                        abmC.bajaCliente(idCliente);
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }   
                }
                cargarClientes();
                break;
            case "Transacciones":
                if (view.getTablaClientes().getSelectedRow() > 0){
                    String nombre = (String) tablaClientes.getValueAt(view.getTablaClientes().getSelectedRow(), 1)+" "+tablaClientes.getValueAt(view.getTablaClientes().getSelectedRow(), 1);
                    int idCliente = (int) view.getTablaClientes().getValueAt(view.getTablaClientes().getSelectedRow(), 0);
                    clienteT.setVisible(true);
                    clienteT.setTitle("Transacciones del cliente "+nombre);
                    cargarTransacciones(idCliente);
                }
                break;
            case "Aceptar": //clienteTransaccion
                clienteT.setVisible(false);
                break;
            case "Confirmar":
                if (crearC.getTextNombre() == null || crearC.getTextApellido() == null){
                    JOptionPane.showInputDialog("Error: Uno de los Campos Obligatorios esta vacío");
                }else{
                    if (!Base.hasConnection()) {
                        abrirBase();
                    }
        
                    String nombre = crearC.getTextNombre().getText().toString();
                    String apellido = crearC.getTextApellido().getText().toString();              
                    BigDecimal deber = new BigDecimal(crearC.getTextDeber().getText().toString());
                    BigDecimal saldo = new BigDecimal(crearC.getTextSaldo().getText().toString());
                    BigDecimal haber = new BigDecimal(crearC.getTextHaber().getText().toString());
        
                    abmC.altaCliente(nombre, apellido, deber, saldo, haber);
        
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                    crearC.setVisible(false);
                }
                cargarClientes();
                break;
            case "Cancelar":
                crearC.setVisible(false);
                break;
            case "Guardar":
                guardarCambios(tablaClientes);
                break;
        }
        cc.cargarCuentas();
    }
}
