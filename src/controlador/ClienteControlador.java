/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import abm.ABMCliente;
import abm.ABMTransaccion;
import interfaz.ClienteGUI;
import interfaz.ClienteTransaccion;
import interfaz.CrearCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
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
    private final ClienteGUI view;
    private final ABMCliente abmC;
    private ABMTransaccion abmT;
    private ABMCaja abmCaja;
    private CajaControlador cc;
    private List<Cliente> listaClientes;
    private DefaultTableModel tablaTransacciones;
    private List<Transaccion> listaTransacciones;
    private ClienteTransaccion clienteT;
    private CrearCliente crearC;

    public ClienteControlador(ClienteGUI clienteGUI) {
        this.view = clienteGUI;
        this.abmC = new ABMCliente();
    }

    public ClienteControlador(ClienteGUI clienteGUI, CajaControlador cc) {
        this.view = clienteGUI;
        this.abmC = new ABMCliente();
        this.abmCaja = new ABMCaja();
        this.abmT = new ABMTransaccion();
        this.cc = cc;
        iniciar();
    }

    private void iniciar() {
        getView().getBotonAgregar().addActionListener(this);
        getView().getBotonEliminar().addActionListener(this);
        getView().getBotonTransacciones().addActionListener(this);
        getView().getButtonGuardar().addActionListener(this);
        tablaClientes = getView().getTablaClienteDef();
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

    public void cargarClientes() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
        tablaClientes.setRowCount(0);
        listaClientes = Cliente.where("visible = ?", 1);
        Iterator<Cliente> it = listaClientes.iterator();
        while (it.hasNext()) {
            Cliente c = it.next();
            Object row[] = new Object[6];
            row[0] = c.get("id");
            row[1] = c.getString("nombre");
            row[2] = c.get("apellido");
            row[3] = c.get("deber");
            row[4] = c.get("haber");
            tablaClientes.addRow(row);
        }
        Base.close();
    }

    private void cargarTransacciones(int id) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        tablaTransacciones.setRowCount(0);
        listaTransacciones = Transaccion.find(" cliente_id = ?", id);
        Iterator<Transaccion> it = listaTransacciones.iterator();
        while (it.hasNext()) {
            Transaccion t = it.next();
            Object row[] = new Object[4];
            row[0] = t.get("id");
            row[1] = t.getString("motivo");
            row[2] = t.getString("tipo");
            row[3] = new BigDecimal(t.getString("monto"));
            tablaTransacciones.addRow(row);
        }
        Base.close();
    }

    public void guardarCambios(DefaultTableModel tabla) {
        abrirBase();
        int i = 0;
        while (i < tabla.getRowCount()) {

            int id = (int) tabla.getValueAt(i, 0);
            BigDecimal deber = new BigDecimal(tabla.getValueAt(i, 3).toString());
            BigDecimal haber = new BigDecimal(tabla.getValueAt(i, 4).toString());
            if (haber.signum() == -1 || deber.signum() == -1) {
                JOptionPane.showMessageDialog(view, "El haber o el deber no pueden ser negativos.");
            } else {
                if (!Cliente.findById(id).get("haber").equals(haber)) {
                    abmT.altaTransaccion("Depósito de cliente: " + Cliente.findById(id).getString("nombre") + " " + Cliente.findById(id).getString("apellido"), "Dep. Cuenta", haber.subtract(Cliente.findById(id).getBigDecimal("haber")), 1, abmCaja.getLastCaja(), id, Quiniela.id_usuario);
                }
                abmC.modificarCliente(id, deber, haber);
                i++;
            }
        }
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
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
                if (getView().getTablaClientes().getSelectedRow() >= 0) {
                    int confirmarBorrar;
                    confirmarBorrar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que quiere borrar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirmarBorrar == JOptionPane.YES_OPTION) {
                        int idCliente = (int) getView().getTablaClientes().getValueAt(getView().getTablaClientes().getSelectedRow(), 0);
                        abrirBase();
                        abmC.bajaCliente(idCliente);
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }
                }
                cargarClientes();
                break;
            case "Transacciones":
                if (getView().getTablaClientes().getSelectedRow() >= 0) {
                    String nombre = (String) tablaClientes.getValueAt(getView().getTablaClientes().getSelectedRow(), 1) + " " + tablaClientes.getValueAt(getView().getTablaClientes().getSelectedRow(), 1);
                    int idCliente = (int) getView().getTablaClientes().getValueAt(getView().getTablaClientes().getSelectedRow(), 0);
                    clienteT.setVisible(true);
                    clienteT.setTitle("Transacciones del cliente " + nombre);
                    cargarTransacciones(idCliente);
                }
                break;
            case "Aceptar": //clienteTransaccion
                clienteT.dispose();
                break;
            case "Confirmar":
                if (crearC.getTextNombre().getText().trim().length() == 0 || crearC.getTextApellido().getText().trim().length() == 0) {
                    JOptionPane.showInputDialog("Error: Uno de los Campos Obligatorios esta vacío");
                } else {
                    abrirBase();
                    String nombre = crearC.getTextNombre().getText().toString();
                    String apellido = crearC.getTextApellido().getText().toString();
                    abmC.altaCliente(nombre, apellido);
                    if (Base.hasConnection()) {
                        Base.close();
                    }
                    crearC.dispose();
                }
                cargarClientes();
                break;
            case "Cancelar":
                crearC.dispose();
                break;
            case "Guardar":
                guardarCambios(tablaClientes);
                cargarClientes();
                break;
        }
        cc.cargarCuentas();
        cc.cargarTransacciones();
    }

    /**
     * @return the view
     */
    public ClienteGUI getView() {
        return view;
    }
}
