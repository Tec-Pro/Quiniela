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
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import models.Usuario;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author max
 */
public class UsuarioControlador implements ActionListener {

    private final UsuarioGUI view;
    private final ABMUsuario abmU;

    public UsuarioControlador(UsuarioGUI usuarioGui) {
        view = usuarioGui;
        abmU = new ABMUsuario();
        iniciar();
    }

    private void iniciar() {
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        view.getButtonIngresar().addActionListener(this);
        view.getButtonSalir().addActionListener(this);
        view.getTextStatus().setText("Buenos Días!");
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "tecpro", "tecpro");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Ingresar":
                abrirBase();
                String user = view.getTextUser().getText();
                String pass = new String(view.getTextPass().getPassword());
                if (view.getTextUser().getText().trim().isEmpty() || view.getTextPass().getPassword().length == 0) {
                    view.getTextStatus().setText("Usuario o contraseña vacíos");
                } else {
                    if (abmU.verificarUsuario(user, pass)) {
                        Usuario u = Usuario.first("nombre = ?", user);
                        if (u.get("visible").equals(1)){
                            Quiniela.id_usuario = (int) u.get("id");
                            view.dispose();
                            MainGUI mg = new MainGUI();
                            MainControlador mc = new MainControlador(mg);
                            mc.run();
                        } else {
                            view.getTextStatus().setText("Usuario inexistente.");
                        }
                    } else {
                        view.getTextStatus().setText("Usuario o contraseña incorrectos");
                    }
                }
                if (Base.hasConnection()) {
                    Base.close();
                }
                break;
            case "Salir":
                view.dispose();
                break;
        }
    }
}
