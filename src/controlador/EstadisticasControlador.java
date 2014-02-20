/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import com.toedter.calendar.JDateChooser;
import interfaz.EstadisticasGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import models.Caja;
import models.Fecha;
import models.Producto;
import models.ProductosTransaccions;
import models.Transaccion;
import org.javalite.activejdbc.Base;

/**
 *
 * @author luciano
 */
public class EstadisticasControlador implements ActionListener {

    private final EstadisticasGUI view;
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
    private JButton calcularDep;
    private JButton calcularProm;
    private JButton calcularProm2;
    private JTextField campoDepo;
    private JTextField campoProm;
    private JTextField campoProm2;

    public EstadisticasControlador(EstadisticasGUI gui) {
        this.view = gui;
        iniciar();
    }

    private void iniciar() {
        tablaEstadisticas = view.getTablaEstDef();
        campoDepo = view.getCampoDep();
        calenDesde = view.getCalendarioDesde();
        calenHasta = view.getCalendarioHasta();
        calcular = view.getBotonBuscar();
        calcular.addActionListener(this);
        calcularDep = view.getBotoncalcularDep();
        calcularDep.addActionListener(this);
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

    public static int getDayOfTheWeek(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public void cargarEstadisticas(JTextField idProd, JTextField dia, java.util.Date desde, java.util.Date hasta) {
        if (desde != null && hasta != null) {
            abrirBase();
            tablaEstadisticas.setRowCount(0);
            if (idProd.getText().trim().length() == 0) {
                listaProd = Producto.findAll();
                for (Producto p : listaProd) {
                    cargarEstadisticasInd((Integer) p.getId(), dia, desde, hasta);
                }
            } else {
                cargarEstadisticasInd(Integer.parseInt(idProd.getText()), dia, desde, hasta);
            }
            if (Base.hasConnection()) {
                Base.close();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Error: Falta especificar fechas");
        }

    }

    public void cargarEstadisticasInd(int idProd, JTextField dia, java.util.Date desde, java.util.Date hasta) {
        abrirBase();
        Integer cantidad = 0;
        Double parcial = 0.0;
        Double ganancia = 0.0;
        Double perdida = 0.0;
        Double promVentas = 0.0;
        Double promVentas2 = 0.0;
        int contador = 0;
        Producto esteProducto = Producto.findById(idProd);
        cajas = Caja.findAll();
        listaProdTrans = ProductosTransaccions.findAll();
        for (Caja c : cajas) { //filtrar cajas que esten entre las fechas especificadas
            contador++;
            if (dia.getText().trim().isEmpty() || getDayOfTheWeek(c.getDate("fecha")) == transformarDia(dia.getText())) {
                if (c.getDate("fecha").compareTo(desde) >= 0 && c.getDate("fecha").compareTo(hasta) <= 0) {
                    listaTransaccion = Transaccion.where("caja_id=?", c.getId());
                    for (Transaccion t : listaTransaccion) { //filtrar transacciones que tengan que ver con el producto
                        for (ProductosTransaccions pt : listaProdTrans) {
                            if (pt.getInteger("transaccion_id").equals(t.getId()) && pt.getInteger("producto_id").equals(idProd)) {
                                parcial += pt.getInteger("cantidad") * pt.getDouble("precio") * pt.getDouble("comision") / 100;
                            }
                        }
                    }
                }

            }
        }
        ganancia = parcial;
        promVentas = cantidad.doubleValue() / contador;
        promVentas2 = ganancia / contador;
        String row[] = new String[7];
        row[0] = esteProducto.getId().toString();
        row[1] = esteProducto.getString("nombre");
        row[2] = cantidad.toString();
        ganancia = round(ganancia, 2);
        row[3] = ganancia.toString();
        perdida = round(perdida, 2);
        row[4] = perdida.toString();
        promVentas = round(promVentas, 2);
        row[5] = promVentas.toString();
        promVentas2 = round(promVentas2, 2);
        row[6] = promVentas2.toString();
        if (esteProducto.get("visible").equals(1)) {
            tablaEstadisticas.addRow(row);
        }
        if (Base.hasConnection()) {
            Base.close();
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        abrirBase();
        JButton b = (JButton) ae.getSource();
        if (b.equals(calcular)) {
            cargarEstadisticas(view.getCampoProd(), view.getCampoDia(), view.getCalendarioDesde().getDate(), view.getCalendarioHasta().getDate());
        }
        if (b.equals(calcularDep)) {
            calcularDeposito(view.getCampoProd());
        }
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
        }
    }

    private int transformarDia(String dia) {
        String fecha = dia.toLowerCase();
        if ("domingo".equals(fecha)) {
            return 1;
        }
        if ("lunes".equals(fecha)) {
            return 2;
        }
        if ("martes".equals(fecha)) {
            return 3;
        }
        if ("miercoles".equals(fecha)) {
            return 4;
        }
        if ("jueves".equals(fecha)) {
            return 5;
        }
        if ("viernes".equals(fecha)) {
            return 6;
        }
        if ("sabado".equals(fecha)) {
            return 7;
        }
        return -1;
    }

    private void calcularDeposito(JTextField idProd) {
        if (idProd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Error: Falta especificar codigo de producto");
        } else {
            abrirBase();
            listaProdTrans = ProductosTransaccions.findAll();
            ABMCaja abm = new ABMCaja();
            Producto esteProducto = Producto.findById(Integer.parseInt(idProd.getText()));
            Caja cajaActual = Caja.findById(abm.getLastCaja());
            Double deposito = 0.0;
            Double gananciaDelDia = 0.0;
            if (esteProducto.getInteger("hayStock") == 1) {
                while (!esDiaDeDeposito(Integer.parseInt(idProd.getText()), getDayOfTheWeek(cajaActual.getDate("fecha")))) {
                    int cantidad = 0;
                    listaTransaccion = Transaccion.where("caja_id=?", cajaActual.getId());
                    for (Transaccion t : listaTransaccion) { //filtrar transacciones que tengan que ver con el producto
                        for (ProductosTransaccions pt : listaProdTrans) {
                            if (pt.getInteger("transaccion_id").equals(t.getId()) && pt.getInteger("producto_id").equals(Integer.parseInt(idProd.getText()))) {
                                gananciaDelDia = pt.getInteger("cantidad") * pt.getDouble("precio") * pt.getDouble("comision") / 100;
                                deposito += pt.getInteger("cantidad") * pt.getDouble("precio") - gananciaDelDia;
                            }
                        }
                    }
                    //acumulo ganancias diarias de ventas del producto   
                    if ((int) cajaActual.getId() - 1 > 0) {
                        cajaActual = Caja.findById((int) cajaActual.getId() - 1);//le asigno la caja de ayer  
                    } else {
                        break;
                    }
                }
                int cantidad = 0;
                listaTransaccion = Transaccion.where("caja_id=?", cajaActual.getId());
                if (esDiaDeDeposito(Integer.parseInt(idProd.getText()), getDayOfTheWeek(cajaActual.getDate("fecha")))) {
                    for (Transaccion t : listaTransaccion) { //filtrar transacciones que tengan que ver con el producto
                        for (ProductosTransaccions pt : listaProdTrans) {
                            if (pt.getInteger("transaccion_id").equals(t.getId()) && pt.getInteger("producto_id").equals(Integer.parseInt(idProd.getText()))) {
                                gananciaDelDia = pt.getInteger("cantidad") * pt.getDouble("precio") * pt.getDouble("comision") / 100;
                                deposito += pt.getInteger("cantidad") * pt.getDouble("precio") - gananciaDelDia;
                            }
                        }
                    }
                }
            } else {
                int cantidad = 0;
                listaTransaccion = Transaccion.where("caja_id=?", cajaActual.getId());
                for (Transaccion t : listaTransaccion) { //filtrar transacciones que tengan que ver con el producto
                    for (ProductosTransaccions pt : listaProdTrans) {
                        if (pt.getInteger("transaccion_id").equals(t.getId()) && pt.getInteger("producto_id").equals(Integer.parseInt(idProd.getText()))) {
                            gananciaDelDia = pt.getInteger("cantidad") * pt.getDouble("precio") * pt.getDouble("comision") / 100;
                            deposito += pt.getInteger("cantidad") * pt.getDouble("precio") - gananciaDelDia;
                        }
                    }
                }
            }
            deposito = round(deposito, 2);
            campoDepo.setText(deposito.toString());
            if (Base.hasConnection()) {
                Base.close();
            }
        }
    }

    private boolean esDiaDeDeposito(int idProd, int dia) {
        List<Fecha> fechas = Fecha.where("producto_id=?", idProd);
        for (Fecha f : fechas) {
            if (transformarDia(f.getString("diaDeposito")) == dia) {
                return true;
            }
        }
        return false;

    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
