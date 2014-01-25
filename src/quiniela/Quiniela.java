/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quiniela;


import abm.ABMTransaccion;
import controlador.MainControlador;
import controlador.UsuarioControlador;
import controlador.reporteControlador;
import interfaz.MainGUI;
import interfaz.UsuarioGUI;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author joako
 */
public class Quiniela {

    public static int id_caja;
    public static int id_usuario;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JRException, ClassNotFoundException, SQLException {

       
             
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
            UsuarioGUI ug = new UsuarioGUI();
            UsuarioControlador uc = new UsuarioControlador(ug);
            Base.close();
        

      }
}
