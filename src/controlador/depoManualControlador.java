/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMTransaccion;
import interfaz.DepoManual;
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
public class depoManualControlador implements ActionListener {

    private DepoManual depM;
    private ABMTransaccion abmTrans;
    private CajaControlador cc;

    depoManualControlador(DepoManual dm, ABMTransaccion abmt, CajaControlador cc) {
        this.depM = dm;
        this.abmTrans = abmt;
        this.cc = cc;
        iniciar();
    }

    private void iniciar() {
        depM.setVisible(true);
        depM.setTitle("Deposito Manual");
        depM.setLocationRelativeTo(null);
        depM.depoCancel.addActionListener(this);
        depM.depoOk.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String comando = ae.getActionCommand();
        switch (comando) {
            case "Depositar":
                if (!Base.hasConnection()) {
                    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
                }
                List<Caja> cajas = Caja.findAll();
                int id_caja = cajas.get(cajas.size() - 1).getInteger("id");
                if (depM.motivoDepoMan.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(depM, "Error: Falta especificar motivo");
                } else {
                    if (Double.valueOf(depM.montoDepoMan.getText()) > 0) {
                        String motivo = depM.motivoDepoMan.getText();
                        BigDecimal monto = BigDecimal.valueOf(Double.valueOf(depM.montoDepoMan.getText()));
                        abmTrans.altaTransaccion(motivo, "Dep. Man", monto, 1, id_caja, Quiniela.id_usuario);
                        cc.cargarTransacciones();
                        depM.dispose();
                    } else {
                        JOptionPane.showMessageDialog(depM, "El depósito no puede ser negativo");
                    }
                }
                if (Base.hasConnection()) {
                    Base.close();
                }
                break;
            case "Cancelar":
                depM.dispose();
                break;
        }
    }

}
