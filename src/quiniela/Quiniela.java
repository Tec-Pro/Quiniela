/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quiniela;


import abm.ABMTransaccion;
import controlador.MainControlador;
import controlador.reporteControlador;
import interfaz.MainGUI;
import java.math.BigDecimal;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author joako
 */
public class Quiniela {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JRException, ClassNotFoundException, SQLException {


            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
            MainGUI mg = new MainGUI();
            MainControlador mc = new MainControlador(mg);
            mc.run();
            Base.close();
        
      }
}
