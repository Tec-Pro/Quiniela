/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCliente;
import interfaz.BorrarCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.javalite.activejdbc.Base;

    
    
/**
 *
 * @author max
 */
class BorrarClienteControlador implements ActionListener{

    private BorrarCliente borrarC;
    private int idCliente;
    private ABMCliente abmC;
        
    BorrarClienteControlador(BorrarCliente bc, ABMCliente abmCliente, int id) {
       borrarC = bc;
       idCliente = id;
       abmC = abmCliente;
       
       borrarC.getButtonNo().addActionListener(this);
       borrarC.getButtonSi().addActionListener(this);     
    }

    public void eliminar(){
        if (!Base.hasConnection()) {
                abrirBase();
        }
        abmC.bajaCliente(idCliente);
        
        if (Base.hasConnection()) {
            Base.close();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Aceptar":
                eliminar();
                borrarC.dispose();
            case "No":
                borrarC.dispose();
        }   
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }
    
}
