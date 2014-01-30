/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import interfaz.ClienteTransaccion;
import interfaz.CrearCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Transaccion;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author max
 */
public class ClienteTransaccionControlador implements ActionListener {
    
    private DefaultTableModel tablaTransacciones;
    private List<Transaccion> listaTransacciones;
    private ClienteTransaccion clienteT;
    
    public ClienteTransaccionControlador(ClienteTransaccion ct,int id){
        clienteT = ct;
        clienteT.getButtonAceptar().addActionListener(this);
        tablaTransacciones = clienteT.getTablaTransaccionDef();
        cargarTransacciones(id);
    }
    
    public void run(String nombre){
        clienteT.setTitle("Transacciones del cliente "+nombre);
        clienteT.setVisible(true);       
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Aceptar":
                clienteT.dispose();        
        }
        
    }
}
