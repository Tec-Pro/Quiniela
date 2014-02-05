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
    private JButton calcular;
    
    public EstadisticasControlador(EstadisticasGUI gui){
        this.view=gui;
        iniciar();
    }
    
    private void iniciar(){
        tablaEstadisticas=view.getTablaEstDef();
        calenDesde = view.getCalendarioDesde();
        calenHasta = view.getCalendarioHasta();
        calcular = view.getBotonBuscar();
        calcular.addActionListener(this);
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
        
    }

    public void calenHastaPropertyChange(PropertyChangeEvent e) {
        final Calendar c = (Calendar) e.getNewValue();
        dateHasta = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
        
    }
    
    public static int getDayOfTheWeek(Date d){
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(d);
	return cal.get(Calendar.DAY_OF_WEEK);		
}

    public void cargarEstadisticas(JTextField idProd, JTextField dia, java.util.Date desde, java.util.Date hasta){
        if (desde.toString().trim().length()!=0 && hasta.toString().trim().length()!=0) {
            abrirBase();
            tablaEstadisticas.setRowCount(0);
            if (idProd.getText().trim().length()==0){
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
        else {
            JOptionPane.showMessageDialog(view, "Error: Falta especificar motivo");
        }
        
    }
    
    public void cargarEstadisticasInd(int idProd, JTextField dia, java.util.Date desde, java.util.Date hasta) {
        abrirBase();
        Integer cantidad=0;
        Double ganancia=0.0;
        Double perdida=0.0;
        Producto esteProducto=Producto.findById(idProd);
        cajas = Caja.findAll();
        listaProdTrans = ProductosTransaccions.findAll();
        for (Caja c : cajas) { //filtrar cajas que esten entre las fechas especificadas
            if (dia.getText().trim().length()==0 || getDayOfTheWeek(c.getDate("fecha"))==Integer.parseInt(dia.getText()) ){
                if (c.getDate("fecha").compareTo(desde)>=0 && c.getDate("fecha").compareTo(hasta)<=0){
                    listaTransaccion=Transaccion.where("caja_id=?",c.getId());
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
        ganancia=cantidad * esteProducto.getDouble("precio") * esteProducto.getDouble("comision")/100;
        String row[] = new String[5];
        row[0]=esteProducto.getId().toString();
        row[1]=esteProducto.getString("nombre");
        row[2]=cantidad.toString();
        row[3]=ganancia.toString();
        row[4]=perdida.toString();
        tablaEstadisticas.addRow(row);
        if (Base.hasConnection())
            Base.close();

    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        abrirBase();
        JButton b = (JButton) ae.getSource();
        if (b.equals(calcular))
            cargarEstadisticas(view.getCampoProd(),view.getCampoDia(),view.getCalendarioDesde().getDate(),view.getCalendarioHasta().getDate());
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }


}
