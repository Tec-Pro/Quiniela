/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import abm.ABMTransaccion;
import interfaz.MainGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import org.javalite.activejdbc.Base;

/**
 *
 * @author joako
 */
public class MainControlador implements ActionListener {
    
    private MainGUI principal;
    
    //Formularios Hijos
    
    public MainControlador(MainGUI main){
        this.principal = main;
        iniciar();
    }
    
    private void iniciar(){
        principal.setTitle("Gestión de Quiniela");
        principal.setLocationRelativeTo(null);//centrado en pantalla
        principal.setExtendedState(principal.MAXIMIZED_BOTH);//estado maximizado
        //se añade las acciones a los controles del formulario padre
        this.principal.abrirCaja.setActionCommand("ABRIR CAJA");
        
        //Se pone a escuchar las acciones del usuario
        principal.abrirCaja.addActionListener(this);
        principal.cerrarCaja.addActionListener(this);
    }
    
    public void run(){
        principal.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        //Obtengo el ActionCommand, es decir la acción realizada
        String comando = ae.getActionCommand();
        //Acciones
        if (comando.equals("ABRIR CAJA")){
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
            ABMTransaccion abmt = new ABMTransaccion();
            ABMCaja abmc = new ABMCaja();
            Date date = new Date();
            abmc.altaCaja(date);
            principal.abrirCaja.setEnabled(false);
            Base.close();
            CajaControlador cc = new CajaControlador(principal.getCaja(),abmt);
        } else if(comando.equals("Cerrar e Imprimir")){
            principal.dispose();
            System.out.println("Movida de imprimir");
        }
        
    }
    
}
