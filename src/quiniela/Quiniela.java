/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quiniela;

import abm.ABMCaja;
import abm.ABMTransaccion;
import controlador.MainControlador;
import interfaz.CajaGUI;
import interfaz.MainGUI;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import javax.swing.*;
import models.Caja;
import models.Transaccion;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

/**
 *
 * @author joako
 */
public class Quiniela {
    public static int id_caja;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
        MainGUI mg = new MainGUI();
        MainControlador mc = new MainControlador(mg);
        mc.run();
        Base.close();
    }
}
