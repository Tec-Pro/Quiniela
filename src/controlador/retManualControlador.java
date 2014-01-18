/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMTransaccion;
import interfaz.DepoManual;
import interfaz.RetManual;
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
public class retManualControlador implements ActionListener {
    private RetManual retM;
    private ABMTransaccion abmTrans;
    
    retManualControlador(RetManual rm, ABMTransaccion abmt){
        this.retM = rm;
        this.abmTrans = abmt;
        iniciar();
    }

    private void iniciar(){
        retM.setVisible(true);
        retM.setTitle("Retiro Manual");
        retM.setLocationRelativeTo(null);
        retM.retCancel.addActionListener(this);
        retM.retOk.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String comando = ae.getActionCommand();
        if (comando.equals("Retirar")){
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela","root", "root");
            String motivo = retM.motivoRepMan.getText();
            BigDecimal monto = BigDecimal.valueOf(Double.valueOf(retM.montoRepMan.getText()));
            System.out.println(monto.negate());
            abmTrans.altaTransaccion(motivo, "Retiro Manual", monto.negate(), 1, Quiniela.id_caja);
            retM.dispose();
            Base.close();
        } else if (comando.equals("Cancelar")){
            retM.dispose();
        }
    }

    
    
}
