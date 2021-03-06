/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMTransaccion;
import interfaz.RetManual;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import models.Caja;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author joako
 */
public class retManualControlador implements ActionListener {

    private final RetManual retM;
    private final ABMTransaccion abmTrans;
    private final CajaControlador cc;

    retManualControlador(RetManual rm, CajaControlador cc) {
        this.retM = rm;
        this.abmTrans = new ABMTransaccion();
        this.cc = cc;
        iniciar();
    }

    private void iniciar() {
        retM.setVisible(true);
        retM.setTitle("Retiro Manual");
        retM.setLocationRelativeTo(null);
        retM.retCancel.addActionListener(this);
        retM.retOk.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String comando = ae.getActionCommand();
        switch (comando) {
            case "Retirar":
                if (!Base.hasConnection()) {
                    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
                }
                List<Caja> cajas = Caja.findAll();
                int id_caja = cajas.get(cajas.size() - 1).getInteger("id");
                String motivo = retM.motivoRepMan.getText();
                BigDecimal monto = new BigDecimal((retM.montoRepMan.getText())).abs();
                if (retM.motivoRepMan.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(retM, "Error: Falta especificar motivo");
                } else {
                    if (Caja.findById(id_caja).getBigDecimal("saldo").subtract((new BigDecimal(retM.montoRepMan.getText()).abs())).compareTo(BigDecimal.ZERO) <0) {
                        JOptionPane.showMessageDialog(retM, "No hay fondos suficientes para realizar este retiro");
                    } else {
                       abmTrans.altaRetiro(motivo, "Ret. Man.", monto.negate(), 1, id_caja, Quiniela.id_usuario);
                        cc.cargarTransacciones();
                        retM.dispose();
                    }
                }
                
                if (Base.hasConnection()) {
                    Base.close();
                }
                break;
            case "Cancelar":
                retM.dispose();
                break;
        }
    }

}
