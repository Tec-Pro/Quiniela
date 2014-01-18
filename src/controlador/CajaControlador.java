/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.*;
import interfaz.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author joako
 */
public class CajaControlador implements ActionListener {
    private CajaGUI view;
    private ABMCaja abmc;
    private ABMTransaccion model;
    //JForm hijos
    
    DepoManual depoManual;
    RetManual retManual;
    
    public CajaControlador(CajaGUI caja, ABMTransaccion abmt){
        this.view = caja;
        this.model = abmt;
        this.abmc = new ABMCaja();
    }
    
    private void iniciar(){
        view.enableAll();
        view.getBotonContado().addActionListener(this);
        view.getBotonCuenta().addActionListener(this);
        view.getDepManual().addActionListener(this);
        view.getRetManual().addActionListener(this);
        view.getTotalField().addActionListener(this);
        view.getVentaOk().addActionListener(this);
        view.getTablaArticulos().addMouseListener(new MouseAdapter() {});
        view.getTablaCliente().addMouseListener(new MouseAdapter() {});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("DEPOSITO MANUAL")){
            depoManual = new DepoManual();
            depoManualControlador dmc = new depoManualControlador(depoManual, model);
        } else if (comando.equals("RETIRO MANUAL")){
            retManual = new RetManual();
            retManualControlador rmc = new retManualControlador(retManual, model);
        } else if (comando.equals("CONTADO")){
            view.disableCliente();
        } else if (comando.equals("CTA. CORRIENTE")){
            view.enableCliente();
        }
        
    }
   
}
