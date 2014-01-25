/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMUsuario;
import interfaz.MainGUI;
import interfaz.UsuarioGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Usuario;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author max
 */
public class UsuarioControlador implements ActionListener{
    
    private UsuarioGUI view;
    private ABMUsuario abmU;
    
    public UsuarioControlador(UsuarioGUI usuarioGui){
        view = usuarioGui;
        abmU = new ABMUsuario();
        iniciar();
    }

    
    private void iniciar() {
        view.setVisible(true);
        view.getButtonIngresar().addActionListener(this);
        view.getButtonSalir().addActionListener(this);
        view.getTextStatus().setText("Buenos Días!");
        
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Ingresar":
                abrirBase();
                String user = view.getTextStatus().toString();
                String pass = view.getTextPass().toString();
                if (abmU.verificarUsuario(user, pass)){
                    Usuario u = Usuario.first("user = ?", user);
                    Quiniela.id_usuario = (int) u.get("id");
                    view.dispose();
                    MainGUI mg = new MainGUI();
                    MainControlador mc = new MainControlador(mg);
                    mc.run();
                }
                view.getTextStatus().setText("Usuario o contraseña equivocada. Intente nuevamente");
            case "Salir":
                view.dispose();
        }
    }

}
