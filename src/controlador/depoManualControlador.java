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
import models.Transaccion;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;
/**
 *
 * @author joako
 */
public class depoManualControlador implements ActionListener {
    private DepoManual depM;
    private ABMTransaccion abmTrans;
    
    depoManualControlador(DepoManual dm, ABMTransaccion abmt){
        this.depM = dm;
        this.abmTrans = abmt;
        iniciar();
    }

    private void iniciar(){
        depM.setVisible(true);
        depM.setTitle("Deposito Manual");
        depM.setLocationRelativeTo(null);
        depM.depoCancel.addActionListener(this);
        depM.depoOk.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String comando = ae.getActionCommand();
        if (comando.equals("Depositar")){
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
            String motivo = depM.motivoDepoMan.getText();
            BigDecimal monto = BigDecimal.valueOf(Double.valueOf(depM.montoDepoMan.getText()));
            abmTrans.altaTransaccion(motivo, "Depósito Manual", monto, 1, Quiniela.id_caja);
            depM.dispose();
            Base.close();
        } else if (comando.equals("Cancelar")){
            depM.dispose();
        }
    }

    
    
}
