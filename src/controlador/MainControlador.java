/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import abm.ABMTransaccion;
import interfaz.MainGUI;
import interfaz.ProductoGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.principal.getAbrirCaja().setActionCommand("ABRIR CAJA");
        
        //Se pone a escuchar las acciones del usuario
        principal.getAbrirCaja().addActionListener(this);
        principal.getCerrarCaja().addActionListener(this);
        principal.getVentanaProductos().addActionListener(this);
        
    }
    
    public void run(){
        principal.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        //Obtengo el ActionCommand, es decir la acción realizada
        String comando = ae.getActionCommand();
        //Acciones
        switch (comando) {
            case "ABRIR CAJA":
                if (!Base.hasConnection()){
                    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
                }
                ABMTransaccion abmt = new ABMTransaccion();
                ABMCaja abmc = new ABMCaja();
                Date date = new Date();
                abmc.altaCaja(date);
                principal.getAbrirCaja().setEnabled(false);
                if (Base.hasConnection()){
                    Base.close();
                }
                CajaControlador cc = new CajaControlador(principal.getCaja(),abmt);
                break;
            case "Cerrar e Imprimir":
                principal.dispose();
                System.out.println("Movida de imprimir");
                break;
            case "VentanaProductos":
                ProductoControlador pc = new ProductoControlador(new ProductoGUI());
        }
        
    }
    
}
