/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;



import com.toedter.calendar.JDateChooser;
import interfaz.EstadisticasGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import models.Caja;
import models.Producto;
import models.ProductosTransaccions;
import models.Transaccion;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
/**
 *
 * @author luciano
 */
public class EstadisticasControlador implements ActionListener {
    
    private EstadisticasGUI view;
    private DefaultTableModel tablaEstadisticas;
    private List<Producto> listaProd;
    private List<Transaccion> listaTransaccion;
    private List<Caja> cajas;
    private List<ProductosTransaccions> listaProdTrans;
    private int id_caja;
    private JDateChooser calenDesde;
    private JDateChooser calenHasta;
    private String dateDesde;
    private String dateHasta;
    
    public EstadisticasControlador(EstadisticasGUI gui){
        this.view=gui;
        iniciar();
    }
    
    private void iniciar(){
        tablaEstadisticas=view.getTablaEstDef();
        calenDesde = view.getCalendarioDesde();
        calenHasta = view.getCalendarioHasta();
        cajas= new LinkedList<Caja>();
        listaTransaccion= new LinkedList<Transaccion>();
        listaProdTrans= new LinkedList<ProductosTransaccions>();
        listaProd= new LinkedList<Producto>();
        dateDesde = "0-0-0";
        dateHasta = "9999-0-0";
        
        calenDesde.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                calenDesdePropertyChange(e);
            }
        });
        calenHasta.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                calenHastaPropertyChange(e);
            }
        });
        
        

    }
    
    public void calenDesdePropertyChange(PropertyChangeEvent e) {
        final Calendar c = (Calendar) e.getNewValue();
        dateDesde = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
        cargarEstadisticas(view.getCampoProd(),view.getCampoDia(),view.getCalendarioDesde().getDate(),view.getCalendarioHasta().getDate());
    }

    public void calenHastaPropertyChange(PropertyChangeEvent e) {
        final Calendar c = (Calendar) e.getNewValue();
        dateHasta = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
        cargarEstadisticas(view.getCampoProd(),view.getCampoDia(),view.getCalendarioDesde().getDate(),view.getCalendarioHasta().getDate());
    }
    
    public static int getDayOfTheWeek(Date d){
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(d);
	return cal.get(Calendar.DAY_OF_WEEK);		
}

    public void cargarEstadisticas(JTextField idProd, JTextField dia, java.util.Date desde, java.util.Date hasta){
        abrirBase();
        tablaEstadisticas.setRowCount(0);
        if (idProd.getText().equals("")){
            listaProd=Producto.findAll();
            for (Producto p : listaProd){
                cargarEstadisticasInd((Integer)p.getId(),dia,desde,hasta);
            }
        }
        else{
            cargarEstadisticasInd(Integer.parseInt(idProd.getText()),dia,desde,hasta);
        }
        if (Base.hasConnection())
            Base.close();
        
    }
    
    public void cargarEstadisticasInd(int idProd, JTextField dia, java.util.Date desde, java.util.Date hasta) {
        abrirBase();
        Integer cantidad=0;
        Double ganancia=0.0;
        Double perdida=0.0;
        Producto esteProducto=Producto.findById(idProd);
        cajas = Caja.findAll();
        for (Caja c : cajas) { //filtrar cajas que esten entre las fechas especificadas
            if (getDayOfTheWeek(c.getDate("fecha"))==Integer.parseInt(dia.getText()) || dia.getText().equals("")){
                if (c.getDate("fecha").compareTo(desde)<=0 && c.getDate("fecha").compareTo(hasta)>=0){
                    listaTransaccion=Transaccion.where("caja_id=?", c.getId());
                    for (Transaccion t : listaTransaccion){ //filtrar transacciones que tengan que ver con el producto
                        for (ProductosTransaccions pt : listaProdTrans){
                            if (pt.getInteger("transaccion_id").equals(t.getId()) && pt.getInteger("producto_id").equals(idProd)){
                                cantidad+=pt.getInteger("cantidad");
                            }
                        }
                    }
                }
                
            }
        }
        ganancia=cantidad * esteProducto.getDouble("precio") * esteProducto.getDouble("comision");
        String row[] = new String[4];
        row[0]=esteProducto.getId().toString();
        row[1]=cantidad.toString();
        row[2]=ganancia.toString();
        row[3]=perdida.toString();
        tablaEstadisticas.addRow(row);
        if (Base.hasConnection())
            Base.close();

    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        abrirBase();
        JButton b = (JButton) ae.getSource();
        if (b.equals(view.getBotonBuscar()))
            cargarEstadisticas(view.getCampoProd(),view.getCampoDia(),view.getCalendarioDesde().getDate(),view.getCalendarioHasta().getDate());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }


}
