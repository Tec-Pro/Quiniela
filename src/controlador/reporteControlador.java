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
import net.sf.jasperreports.engine.JREmptyDataSource;
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

    private final JasperReport reporte;

    public reporteControlador(String jasper) throws JRException, ClassNotFoundException, SQLException {
        //cargo el reporte
        reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reporte/"+jasper));
    }

    //listado de clientes productos y proveedores.
    public void mostrarReporte(int caja_id, Double totalVentas, Double totalOtros) throws ClassNotFoundException, SQLException, JRException {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/quiniela", "root", "root")) {
            Map parametros = new HashMap();
            parametros.clear();
            parametros.put("idCaja", caja_id);
            parametros.put("totalSinVentas", totalOtros);
            parametros.put("ventasTotales", totalVentas);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
            JasperViewer.viewReport(jasperPrint, false);
        }
    }
    
    public void mostrarReporte(Map parametros) throws ClassNotFoundException, SQLException, JRException {
        System.out.println(parametros);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new    JREmptyDataSource());
        JasperViewer.viewReport(jasperPrint, false);
    }
    
    public void mostrarLista() throws ClassNotFoundException, SQLException, JRException {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/quiniela", "root", "root")) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        }
    }
}
