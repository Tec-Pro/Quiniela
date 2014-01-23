/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCliente;
import interfaz.CrearCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import org.javalite.activejdbc.Base;

/**
 *
 * @author max
 */
public class CrearClienteControlador implements ActionListener {

    private CrearCliente crearC;
    private ABMCliente abmC;
    
    public CrearClienteControlador(CrearCliente cc, ABMCliente abmCliente) {
        crearC = cc;
        abmC = abmCliente;
        
        crearC.getButtonCancelar().addActionListener(this);
        crearC.getButtonConfirmar().addActionListener(this);
    }
    
    public void agregar(){
        if (!Base.hasConnection()) {
                abrirBase();
        }
        
        String nombre = crearC.getTextNombre().toString();
        String apellido = crearC.getTextApellido().toString();
        BigDecimal deber = new BigDecimal(crearC.getTextDeber().toString());
        BigDecimal saldo = new BigDecimal(crearC.getTextSaldo().toString());
        BigDecimal haber = new BigDecimal(crearC.getTextHaber().toString());
        
        abmC.altaCliente(nombre, apellido, deber, saldo, haber);
        
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Confirmar":
                if (crearC.getTextNombre() == null || crearC.getTextApellido() == null){
                    agregar();
                    crearC.dispose();
                }
            case "Cancelar":
                crearC.dispose();
        }  
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }  
}
