/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import java.util.List;
import models.Cliente;
import org.javalite.activejdbc.Base;

/**
 *
 * @author max
 */
public class ABMCliente {
    private String nombre;
    private String apellido;
    private BigDecimal deber;
    private BigDecimal saldo;
    private BigDecimal haber;
    private int visible;
    private int id;

    /**
     * Getters 
     */
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public BigDecimal getDeber() {
        return deber;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public int getVisible() {
        return visible;
    }

    public int getId() {
        return id;
    }
    /**
     * End of Getters
     */
    
    
    /**
     * Setters
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDeber(BigDecimal deber) {
        this.deber = deber;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * End of Setters
     */
    
    public Cliente getCliente(String nombre, String apellido, int visible){
        return Cliente.first("nombre = ? and apellido = ? and visible = ?", nombre, apellido, visible);
    }
    
    public boolean findCliente(String nombre, String apellido,int visible){
        return (Cliente.first("nombre = ? and apellido = ? and visible = ?", nombre, apellido, visible)!= null);
    }
    /**
     * 
     * @param nombre
     * @param apellido
     * @param deber
     * @param saldo
     * @param haber
     * @param visible
     * @return true si el cliente se creo con exito, false si ya existía o introdujo datos erroneos
     *
     */
    public boolean altaCliente(String nombre, String apellido, BigDecimal deber, BigDecimal saldo, BigDecimal haber){
        if (findCliente(nombre,apellido,1)!=true){
            if (findCliente(nombre,apellido,0)==true){
                modificarCliente(nombre, apellido, deber, saldo, haber, 0);
            }else{
            Base.openTransaction();
            if (deber.signum()==-1){
                System.out.println("No se aceptan numero negativos");   
                return false;
            }
            if (saldo.signum()==-1){
                System.out.println("No se aceptan numero negativos");  
                return false;
            }
            if (haber.signum()==-1){
                System.out.println("No se aceptan numero negativos");   
                return false;
            }
            Cliente nuevo = Cliente.create("nombre",nombre,"apellido",apellido,"deber",deber,"saldo",saldo,"haber",haber,"visible",1);
            nuevo.saveIt();
            Base.commitTransaction();
            return true;
            }
        }            
        return false;
    }
    
    
     /**
     * 
     * @param nombre
     * @param apellido
     * @return true si el cliente se borro con exito
     *
     */
    public boolean bajaCliente(String nombre, String apellido){
        Cliente c;
        c = getCliente(nombre, apellido,1);
        if (c!=null){
            Base.openTransaction();
            c.set("visible",0);
            c.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
        
    }
     /**
     * 
     * @param nombre
     * @param apellido
     * @param deber
     * @param saldo
     * @param haber
     * @param visible
     * @return true si el se modifico el cliente con exito, false en caso contrario
     *
     */
    public boolean modificarCliente(String nombre, String apellido, BigDecimal deber, BigDecimal saldo, BigDecimal haber, int visible){
        if (findCliente(nombre,apellido,visible)!=true){
             Cliente c;
             c = getCliente(nombre, apellido,visible);
             Base.openTransaction();
             if (deber.signum()==-1){
                 System.out.println("No se aceptan numero negativos");   
                 return false;
             }
             if (saldo.signum()==-1){
                 System.out.println("No se aceptan numero negativos");  
                 return false;
             }
             if (haber.signum()==-1){
                 System.out.println("No se aceptan numero negativos");   
                 return false;
             }
             c.set("nombre",nombre);
             c.set("apellido",apellido);
             c.set("deber",deber);
             c.set("saldo",saldo);
             c.set("haber",haber);
             c.set("visible", 1);
             c.saveIt();
             Base.commitTransaction();
             return true;
        }
        return false;
    }
}
