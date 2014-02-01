/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import abm.ABMUsuario;
import interfaz.AdministradorGUI;
import interfaz.AgregarUsuario;
import interfaz.ModificarUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Usuario;
import org.javalite.activejdbc.Base;

/**
 *
 * @author max
 */
public class AdministradorControlador implements ActionListener {
    private DefaultTableModel tablaUsuarios;
    private AdministradorGUI view;
    private ABMUsuario abmU;
    private List<Usuario> listaUsuarios;
    private AgregarUsuario agregarU;
    private ModificarUsuario modificarU;
    private int idUsuarioE;
    private int idUsuarioM;
    
    public AdministradorControlador(AdministradorGUI adminGUI){
        view = adminGUI;
        abmU = new ABMUsuario();
        agregarU = new AgregarUsuario();
        agregarU.setVisible(false);
        modificarU = new ModificarUsuario();
        modificarU.setVisible(false);
        iniciar();
    }

    private void iniciar() {
        view.setVisible(true);
        view.getButtonNuevo().addActionListener(this);
        view.getButtonEliminar().addActionListener(this);
        view.getButtonModificar().addActionListener(this);
        tablaUsuarios = view.getTablaUsuariosDef();
        cargarUsuarios();
        
        //Ventana AgregarUsuario
        agregarU.getButtonAgregar().addActionListener(this);
        agregarU.getButtonSalir().addActionListener(this);
                
        //Ventana ModificarUsuario
        modificarU.getButtonConfirmar().addActionListener(this);
        modificarU.getButtonSalir().addActionListener(this);
        
    }

    private void cargarUsuarios() {
       if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/quiniela", "root", "root");
        }
        tablaUsuarios.setRowCount(0);
        listaUsuarios = Usuario.where("visible = ?", 1);
        Iterator<Usuario> it = listaUsuarios.iterator();
        while(it.hasNext()){
            Usuario u = it.next();
            Object row[] = new Object[3];
            row[0] = u.get("id");
            row[1] = u.getString("nombre");
            row[2] = u.get("pass");
            tablaUsuarios.addRow(row);
        }
        Base.close();
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
            case "Nuevo":
                agregarU.setVisible(true);
                break;                
            case "Modificar":
                idUsuarioM = (int) view.getTablaUsuarios().getValueAt(view.getTablaUsuarios().getSelectedRow(), 0); //Guarda el usuario seleccionado (modificar)
                modificarU.setVisible(true);
                break;
            case "Eliminar":
                idUsuarioE = (int) view.getTablaUsuarios().getValueAt(view.getTablaUsuarios().getSelectedRow(), 0); //Guarda el usuario seleccionado (eliminar)
                if (view.getTablaUsuarios().getSelectedRow() > 0){
                    int confirmarBorrar;
                    confirmarBorrar = JOptionPane.showConfirmDialog(null,"¿Esta seguro que quiere borrar este Usuario?","Confirmar",JOptionPane.YES_NO_OPTION);
                    if (confirmarBorrar == JOptionPane.YES_OPTION){
                        if (!Base.hasConnection()) {
                            abrirBase();
                        }
                        abmU.bajaUsuario(idUsuarioE);
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }   
                }
                cargarUsuarios();
                break;
            case "Salir": //Salir de la ventana AgregarUsuario o ModificarUsuario
                agregarU.setVisible(false);
                modificarU.setVisible(false);
                break;
            case "Confirmar": //Ventana ModificarUsuario
                if (modificarU.getTextPass1().getPassword().length == 0 || modificarU.getTextPass2().getPassword().length == 0) {
                    JOptionPane.showInputDialog("Error: Contraseña vacía");
                }else{
                    String pass1 = new String(modificarU.getTextPass1().getPassword());
                    String pass2 = new String(modificarU.getTextPass2().getPassword());
                    if (!(pass1 == pass2)) {
                        JOptionPane.showInputDialog("Error: Contraseñas no coinciden");
                    }else{
                        if (!Base.hasConnection()) {
                            abrirBase();
                        }
                        abmU.modificarUsuario(idUsuarioM,pass1,0);
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }
                }
                cargarUsuarios();//Actualiza la tabla
                break;
            case "Agregar"://Ventana AgregarUsuario
                if (agregarU.getTextUsuario() == null || agregarU.getTextPass1().getPassword().length == 0 || agregarU.getTextPass2().getPassword().length == 0){
                    JOptionPane.showInputDialog("Error: Usuario y/o contraseña vacía");
                }else{
                    String pass1 = new String(agregarU.getTextPass1().getPassword());
                    String pass2 = new String(agregarU.getTextPass2().getPassword());
                    if (!(pass1 == pass2)) {
                        JOptionPane.showInputDialog("Error: Contraseñas no coinciden");
                    }else{
                        if (!Base.hasConnection()) {
                            abrirBase();
                        }
                        abmU.altaUsuario(agregarU.getTextUsuario().toString(),pass1,0);
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }
                }
                cargarUsuarios();//Actualiza la tabla
                break;
        }
    }
}
