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
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.Caja;
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
    private CajaControlador cc;

    retManualControlador(RetManual rm, ABMTransaccion abmt, CajaControlador cc) {
        this.retM = rm;
        this.abmTrans = abmt;
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
                    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
                }
                List<Caja> cajas = Caja.findAll();
                int id_caja = cajas.get(cajas.size()-1).getInteger("id");
                String motivo = retM.motivoRepMan.getText();
                BigDecimal monto = BigDecimal.valueOf(Double.valueOf(retM.montoRepMan.getText()));
                System.out.println(monto.negate());
                System.out.println(id_caja);
                if (abmTrans.altaTransaccion(motivo, "Retiro Manual", monto.negate(), 1, id_caja, 1)==false){
                    System.out.println("Acá entró");
                    JFrame error = new JFrame();
                    JPanel panel = new JPanel();
                    error.setTitle("Error");
                    JLabel mensaje = new JLabel();
                    mensaje.setText("No hay fondos suficientes para realizar este retiro");
                    panel.add(mensaje);
                    error.add(panel);
                    error.setSize(320, 240);
                    error.setLocationRelativeTo(null);
                    error.setVisible(true);
                }
                cc.cargarTransacciones();
                retM.dispose();
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
