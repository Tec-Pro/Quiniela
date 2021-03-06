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
    private final AdministradorGUI view;
    private final ABMUsuario abmU;
    private List<Usuario> listaUsuarios;
    private final AgregarUsuario agregarU;
    private final ModificarUsuario modificarU;
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
        getView().getButtonNuevo().addActionListener(this);
        getView().getButtonEliminar().addActionListener(this);
        getView().getButtonModificar().addActionListener(this);
        tablaUsuarios = getView().getTablaUsuariosDef();
        cargarUsuarios();
        
        //Ventana AgregarUsuario
        agregarU.getButtonAgregar().addActionListener(this);
        agregarU.getButtonSalir().addActionListener(this);
                
        //Ventana ModificarUsuario
        modificarU.getButtonConfirmar().addActionListener(this);
        modificarU.getButtonSalir().addActionListener(this);
        
    }

    private void cargarUsuarios() {
        abrirBase();
        tablaUsuarios.setRowCount(0);
        listaUsuarios = Usuario.where("visible = ?", 1);
        Iterator<Usuario> it = listaUsuarios.iterator();
        while(it.hasNext()){
            Usuario u = it.next();
            if (!u.get("admin").equals(1)){
                Object row[] = new Object[3];
                row[0] = u.get("id");
                row[1] = u.getString("nombre");
                row[2] = u.get("pass");
                tablaUsuarios.addRow(row);
            }
        }
        Base.close();
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
            case "Nuevo":
                agregarU.setVisible(true);
                break;                
            case "Modificar":
                idUsuarioM = (int) getView().getTablaUsuarios().getValueAt(getView().getTablaUsuarios().getSelectedRow(), 0); //Guarda el usuario seleccionado (modificar)
                modificarU.setVisible(true);
                break;
            case "Eliminar":
                idUsuarioE = (int) getView().getTablaUsuarios().getValueAt(getView().getTablaUsuarios().getSelectedRow(), 0); //Guarda el usuario seleccionado (eliminar)
                if (getView().getTablaUsuarios().getSelectedRow() >= 0){
                    int confirmarBorrar;
                    confirmarBorrar = JOptionPane.showConfirmDialog(null,"¿Esta seguro que quiere borrar este Usuario?","Confirmar",JOptionPane.YES_NO_OPTION);
                    if (confirmarBorrar == JOptionPane.YES_OPTION){
                        abrirBase();
                        abmU.bajaUsuario(idUsuarioE);
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }   
                }
                cargarUsuarios();
                break;
            case "Salir": //Salir de la ventana AgregarUsuario o ModificarUsuario
                agregarU.dispose();
                modificarU.dispose();
                break;
            case "Confirmar": //Ventana ModificarUsuario
                if (modificarU.getTextPass1().getPassword().length == 0 || modificarU.getTextPass2().getPassword().length == 0) {
                    JOptionPane.showInputDialog("Error: Contraseña vacía");
                }else{
                    String pass1 = new String(modificarU.getTextPass1().getPassword());
                    String pass2 = new String(modificarU.getTextPass2().getPassword());
                    if (!(pass1.equals(pass2))) {
                        JOptionPane.showInputDialog("Error: Contraseñas no coinciden");
                    }else{
                        abrirBase();
                        abmU.modificarUsuario(idUsuarioM,pass1,0);
                        modificarU.getTextPass1().setText("");
                        modificarU.getTextPass2().setText("");
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }
                }
                cargarUsuarios();//Actualiza la tabla
                break;
            case "Guardar"://Ventana AgregarUsuario
                if (agregarU.getTextUsuario().getText().trim().isEmpty() || agregarU.getTextPass1().getPassword().length == 0 || agregarU.getTextPass2().getPassword().length == 0){
                    JOptionPane.showInputDialog("Error: Usuario y/o contraseña vacía");
                }else{
                    String pass1 = new String(agregarU.getTextPass1().getPassword());
                    String pass2 = new String(agregarU.getTextPass2().getPassword());
                    if (!(pass1.equals(pass2))) {
                        JOptionPane.showInputDialog("Error: Contraseñas no coinciden");
                    }else{
                        abrirBase();
                        abmU.altaUsuario(agregarU.getTextUsuario().getText().trim().toString(),pass1);
                        agregarU.getTextPass1().setText("");
                        agregarU.getTextPass2().setText("");
                        agregarU.getTextUsuario().setText("");
                        if (Base.hasConnection()) {
                            Base.close();
                        }
                    }
                }
                cargarUsuarios();//Actualiza la tabla
                break;
        }
    }

    /**
     * @return the view
     */
    public AdministradorGUI getView() {
        return view;
    }
}
