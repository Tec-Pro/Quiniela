/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import abm.ABMTransaccion;
import interfaz.AdministradorGUI;
import interfaz.ClienteGUI;
import interfaz.ListaCajas;
import interfaz.MainGUI;
import interfaz.ProductoGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Usuario;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.joda.time.LocalDate;
import quiniela.Quiniela;

/**
 *
 * @author joako
 */
public class MainControlador implements ActionListener {
    
    private final MainGUI principal;
    private ABMCaja abmc;
    private CajaControlador cc;
    private EstadisticasControlador ec;
    
    //Formularios Hijos
    
    public MainControlador(MainGUI main){
        this.principal = main;
        iniciar();
    }
    
    private void iniciar(){
        principal.setTitle("Gestión de Quiniela");
        principal.setVisible(true);
        principal.setLocationRelativeTo(null);//centrado en pantalla
        principal.setExtendedState(MainGUI.MAXIMIZED_BOTH);//estado maximizado
        //se añade las acciones a los controles del formulario padre
        this.principal.getAbrirCaja().setActionCommand("ABRIR CAJA");
        principal.getImprimirParcial().setEnabled(false);
        //Se pone a escuchar las acciones del usuario
        principal.getAbrirCaja().addActionListener(this);
        principal.getCerrarCaja().addActionListener(this);
        principal.getVentanaProductos().addActionListener(this);
        principal.getVentanaClientes().addActionListener(this);
        principal.getImprimirClientes().addActionListener(this);
        principal.getImprimirParcial().addActionListener(this);
        principal.getImprimirProductos().addActionListener(this);
        principal.getCajasAnteriores().addActionListener(this);
        principal.getMenuUsuario().addActionListener(this);
        principal.getProducto().setEnabled(false);
        principal.getCuenta().setEnabled(false);
        principal.getCajasAnteriores().setEnabled(false);
        abmc = new ABMCaja();
        Usuario u = Usuario.findById(Quiniela.id_usuario);
        if (u.get("admin").equals(1)){
            principal.addMenuAdmin();
        }
        cc = null;
        ec = null;
    }
    
    public void run(){
        principal.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            //Obtengo el ActionCommand, es decir la acción realizada
            String comando = ae.getActionCommand();
            //Acciones
            switch (comando) {
                case "ABRIR CAJA":
                    if (!Base.hasConnection()){
                        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
                    }
                    ABMTransaccion abmt = new ABMTransaccion();;
                    LocalDate date = new LocalDate();
                    abmc.altaCaja(date);
                    principal.getAbrirCaja().setEnabled(false);
                    if (Base.hasConnection()){
                        Base.close();
                    }
                    cc = new CajaControlador(principal.getCaja());
                    ec = new EstadisticasControlador(principal.getEstadisticas());
                    principal.getImprimirParcial().setEnabled(true);
                    principal.getCuenta().setEnabled(true);
                    principal.getProducto().setEnabled(true);
                    principal.getCajasAnteriores().setEnabled(true);
                    break;
                case "Cerrar e Imprimir":
                    reporteControlador rc = new reporteControlador("transacciones.jasper");
                    if (!Base.hasConnection()){
                        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
                    }
                    int id_caja = abmc.getLastCaja();
                    rc.mostrarReporte(id_caja, abmc.getTotalVentas(id_caja),abmc.getTotalOtros(id_caja));
                    if (Base.hasConnection()){
                        Base.close();
                    }
                    principal.dispose();
                    break;
                case "cajaParcial":
                    reporteControlador rc2 = new reporteControlador("transacciones.jasper");
                    if (!Base.hasConnection()){
                        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
                    }
                    int id_parcial = abmc.getLastCaja();
                    rc2.mostrarReporte(id_parcial, abmc.getTotalVentas(id_parcial), abmc.getTotalOtros(id_parcial));
                    if (Base.hasConnection()){
                        Base.close();
                    }
                    break;
                case "imprimirClientes":
                    reporteControlador rccliente = new reporteControlador("listaClientes.jasper");
                    rccliente.mostrarLista();
                    break;
                case "imprimirProductos":
                    reporteControlador rcProd = new reporteControlador("listaProductos.jasper");
                    rcProd.mostrarLista();
                    break;
                case "VentanaProductos":
                    ProductoControlador pc = new ProductoControlador(new ProductoGUI(), cc);
                    break;
                case "VentanaClientes":
                    ClienteControlador contCli = new ClienteControlador(new ClienteGUI(), cc);
                    break;
                case "cajasAnteriores":
                    listaCajasControlador lcc = new listaCajasControlador(new ListaCajas());
                    break;
                case "MenuUsuario":
                    AdministradorControlador admCont = new AdministradorControlador(new AdministradorGUI());
                    break;
            }
        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
