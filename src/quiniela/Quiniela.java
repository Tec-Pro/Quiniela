/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quiniela;


import controlador.UsuarioControlador;
import interfaz.UsuarioGUI;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
/**
 *
 * @author joako
 */
public class Quiniela {

    public static int id_caja;
    public static int id_usuario;
    public static int ventana;
    /**
     * @param args the command line arguments
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws JRException, ClassNotFoundException, SQLException {
            JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
       
             
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
            UsuarioGUI ug = new UsuarioGUI();
            UsuarioControlador uc = new UsuarioControlador(ug);
            Base.close();
        

      }
}
