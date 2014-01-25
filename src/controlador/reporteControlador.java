/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author joako
 */
public class reporteControlador {

    private JasperReport reporte;

    public reporteControlador(String jasper) throws JRException, ClassNotFoundException, SQLException {
        //cargo el reporte
        reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reporte/"+jasper));
    }

    //listado de clientes productos y proveedores.
    public void mostrarReporte() throws ClassNotFoundException, SQLException, JRException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/quiniela", "root", "root");
        Map parametros = new HashMap();
        parametros.clear();
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
        JasperViewer.viewReport(jasperPrint, false);
        connection.close();
    }

}
