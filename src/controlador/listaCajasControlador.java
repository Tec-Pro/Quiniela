/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMCaja;
import com.toedter.calendar.JDateChooser;
import interfaz.ListaCajas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Caja;
import models.Transaccion;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author joako
 */
public class listaCajasControlador implements ActionListener {

    private ABMCaja abmc;
    private final ListaCajas view;
    private final DefaultTableModel listaCajas;
    private JDateChooser calenDesde;
    private JDateChooser calenHasta;
    private String dateDesde;
    private String dateHasta;
    private String comando;
    private Double totalVentas;
    private Double totalOtros;
    private DateFormat df;
    private int id_caja;
    private final ActionListener reporteCaja;

    public listaCajasControlador(ListaCajas lc) {
        this.reporteCaja = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                abrirBase();
                try {
                    reporteControlador rc = new reporteControlador("transacciones.jasper");
                    rc.mostrarReporte(id_caja, abmc.getTotalVentas(id_caja), abmc.getTotalOtros(id_caja));
                } catch (JRException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(listaCajasControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (Base.hasConnection()) {
                    Base.close();
                }
            }
        };
        this.view = lc;
        this.listaCajas = view.getTablaCajas();
        iniciar();
    }

    private void iniciar() {
        calenDesde = getView().getFechaDesde();
        calenHasta = getView().getFechaHasta();
        dateDesde = "1900-01-01";
        dateHasta = "9999-01-01";
        id_caja = 0;
        abmc = new ABMCaja();

        calenDesde.addPropertyChangeListener("calendar", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                final Calendar c = (Calendar) pce.getNewValue();
                dateDesde = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
            }
        });
        calenHasta.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                final Calendar c = (Calendar) pce.getNewValue();
                dateHasta = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE);
            }
        });
        getView().getFiltrar().addActionListener(this);
        getView().getReporteCaja().addActionListener(reporteCaja);
        getView().getListaCajas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    JTable target = (JTable) e.getSource();
                    id_caja = (int) target.getValueAt(target.getSelectedRow(), 0);
                    getView().getMenuCaja().setVisible(true);
                    getView().getMenuCaja().show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void cargarCajas(java.util.Date desde, java.util.Date hasta) {
        if (desde != null && hasta != null) {
            abrirBase();
            listaCajas.setRowCount(0);
            List<Caja> lista = Caja.where("fecha between ? and ?", desde, hasta);
            for (Caja c : lista) {
                totalVentas = 0.0;
                totalOtros = 0.0;
                if (c.get("visible").equals(1)) {
                    List<Transaccion> transacciones = Transaccion.where("caja_id = ?", c.get("id"));
                    for (Transaccion t : transacciones) {
                        if (t.get("cliente_id") == null) {
                            if (t.get("visible").equals(1)) {
                                if (t.get("tipo").equals("Venta")) {
                                    totalVentas += t.getDouble("monto");
                                } else {
                                    totalOtros += t.getDouble("monto");
                                }
                            }
                        }
                    }
                    Object row[] = new Object[5];
                    row[0] = c.get("id");
                    row[1] = c.get("fecha");
                    row[2] = totalVentas;
                    row[3] = totalOtros;
                    row[4] = c.get("saldo");
                    listaCajas.addRow(row);
                }
            }
            if (Base.hasConnection()) {
                Base.close();
            }
        } else {
            JOptionPane.showMessageDialog(getView(), "Error: Falta especificar fechas");
        }

    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        comando = ae.getActionCommand();
        switch (comando) {
            case "Filtrar":
                cargarCajas(getView().getFechaDesde().getDate(), getView().getFechaHasta().getDate());
                break;
        }

    }

    /**
     * @return the view
     */
    public ListaCajas getView() {
        return view;
    }
}
