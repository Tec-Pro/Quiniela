/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import models.Usuario;
import org.javalite.activejdbc.Base;

/**
 *
 * @author max
 */
public class ABMUsuario {

    private String user;
    private String password;
    private int visible;
    private int id;
    private int admin;
    
    public Usuario getUsuario(int id){
        return Usuario.first("id = ?",id);
    }
    
    /**
     * 
     * @param user
     * @param password
     * @return true si el usuario se creo con exito, false si ya existía o introdujo datos erroneos
     *
     */
    public boolean altaUsuario(String user, String password, int administrator){
        Base.openTransaction();
        Usuario nuevo = Usuario.create("nombre",user,"pass",password,"admin",administrator,"visible",1);
        nuevo.saveIt();
        Base.commitTransaction();
        return true;
    }
    
    
     /**
     * 
     * @param id
     * @return true si el usuario se borro con exito
     *
     */
    public boolean bajaUsuario(int id){
        Usuario u;
        u = getUsuario(id);
        if (u!=null){
            Base.openTransaction();
            u.set("visible",0);
            u.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
        
    }
     /**
     *
     * @param password
     * @param id
     * @return true si el se modifico el usuario con exito, false en caso contrario
     *
     */
    public boolean modificarUsuario(int id,String pass, int admin){
         Usuario u;
         u = getUsuario(id);
         if (u!=null){
             u.set("pass",pass);
             u.saveIt();
             Base.commitTransaction();
             return true;
        }
        return false;
    }
    
    public boolean verificarAdministrador(int id){
        Usuario u;
        u = getUsuario(id);
        if (u!=null){
            if (!u.get("admin").equals(0)){
                return true;
            }
        }
        return false;
    }
    
    public boolean verificarUsuario(String usuario, String password){
        Usuario u;
        u = Usuario.first("nombre = ?", usuario);
        if (u!=null){
            if (u.get("nombre").equals(usuario)){
                if (u.get("pass").equals(password)){
                    return true;
                }
            }
        }
        return false;
    }
}

