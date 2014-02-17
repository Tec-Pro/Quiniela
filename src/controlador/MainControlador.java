/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import abm.ABMTransaccion;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import interfaz.AcercaDeGUI;
import interfaz.AdministradorGUI;
import interfaz.ClienteGUI;
import interfaz.ListaCajas;
import interfaz.MainGUI;
import interfaz.ProductoGUI;
import interfaz.UsuarioGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
    //Creo variables de Interfaz.
    private final MainGUI principal;
    private AdministradorGUI administrador;
    private ClienteGUI cliente;
    private ListaCajas listaCajas;
    private ProductoGUI producto;
    private ABMCaja abmc;
    private UsuarioGUI usuario;
    //Creo controladores.
    private CajaControlador cc;
    private EstadisticasControlador ec;
    private ProductoControlador pc;
    private ClienteControlador clic;
    private reporteControlador rc;
    private AdministradorControlador admin;
    private UsuarioControlador uc;
    private listaCajasControlador lcc;
    
    //Formularios Hijos
    
    public MainControlador(MainGUI main){
        this.principal = main;
        iniciar();
    }
    
    private void iniciar(){
        Properties props = new Properties();
        props.put("logoString", "");
        AeroLookAndFeel.setCurrentTheme(props);
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        Quiniela.ventana = 0;
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
        principal.getAcercaDe().addActionListener(this);
        abmc = new ABMCaja();
        administrador = new AdministradorGUI();
        cliente = new ClienteGUI();
        listaCajas = new ListaCajas();
        producto = new ProductoGUI();
        cc = new CajaControlador(principal.getCaja());
        ec = new EstadisticasControlador(principal.getEstadisticas());
        pc = new ProductoControlador(producto,cc);
        clic = new ClienteControlador(cliente,cc);
        lcc = new listaCajasControlador(listaCajas);
        principal.getCaja().disableAll();
        administrador.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                Quiniela.ventana = 0;
            }
        });
        cliente.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                Quiniela.ventana = 0;
            }
        });
        listaCajas.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                Quiniela.ventana = 0;
            }
        });
        producto.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                Quiniela.ventana = 0;
            }
        });
        abrirBase();
        Usuario u = Usuario.findById(Quiniela.id_usuario);
        if (u.get("admin").equals(1)){
            principal.addMenuAdmin();
            admin = new AdministradorControlador(administrador);
        }
        if (Base.hasConnection())
            Base.close();
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
                    principal.getCaja().enableAll();
                    principal.getAbrirCaja().setEnabled(false);
                    if (Base.hasConnection()){
                        Base.close();
                    }
                    principal.getImprimirParcial().setEnabled(true);
                    break;
                case "Cerrar":
                    int imprimir;
                    imprimir = JOptionPane.showConfirmDialog(null,"¿Desea imprimir el reporte de la caja?","Confirmar",JOptionPane.YES_NO_OPTION);
                    if (JOptionPane.YES_OPTION == imprimir){
                        System.out.println("aca");
                        rc = new reporteControlador("transacciones.jasper");
                        if (!Base.hasConnection()){
                            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
                        }
                        int id_caja = abmc.getLastCaja();
                        rc.mostrarReporte(id_caja, abmc.getTotalVentas(id_caja),abmc.getTotalOtros(id_caja));
                        if (Base.hasConnection()){
                            Base.close();
                        }
                    }
                    principal.dispose();
                    System.exit(0);
                    break;
                case "cajaParcial":
                    rc = new reporteControlador("transacciones.jasper");
                    if (!Base.hasConnection()){
                        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
                    }
                    int id_parcial = abmc.getLastCaja();
                    rc.mostrarReporte(id_parcial, abmc.getTotalVentas(id_parcial), abmc.getTotalOtros(id_parcial));
                    if (Base.hasConnection()){
                        Base.close();
                    }
                    break;
                case "imprimirClientes":
                    rc = new reporteControlador("listaClientes.jasper");
                    rc.mostrarLista();
                    break;
                case "imprimirProductos":
                    rc= new reporteControlador("listaProductos.jasper");
                    rc.mostrarLista();
                    break;
                case "VentanaProductos":
                    if (Quiniela.ventana == 0){
                        pc.getView().setVisible(true);
                        Quiniela.ventana = 1;
                    }
                    break;
                case "VentanaClientes":
                    if (Quiniela.ventana == 0){
                        clic.getView().setVisible(true);
                        Quiniela.ventana = 1;
                    }
                    break;
                case "cajasAnteriores":
                    if (Quiniela.ventana == 0){
                        lcc.getView().setVisible(true);
                        Quiniela.ventana = 1;
                    }
                    break;
                case "MenuUsuario":
                    if (Quiniela.ventana == 0){
                        admin.getView().setVisible(true);
                        Quiniela.ventana = 1;
                    }
                    break;
                case "Acerca De":
                    if (Quiniela.ventana == 0){
                        AcercaDeGUI ad = new AcercaDeGUI(principal,true);
                    }
            }
        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }
}
