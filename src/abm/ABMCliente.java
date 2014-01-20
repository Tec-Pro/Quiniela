/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
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
 
    public boolean altaCliente(String nombre, String apellido, BigDecimal deber, BigDecimal saldo, BigDecimal haber, int visible){
        Cliente c1 = Cliente.findById(nombre);
        Cliente c2 = Cliente.findById(apellido);
        if (c1 != null && c2 != null){
            if (c1 == c2){
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
                Cliente nuevo = Cliente.create("nombre",nombre,"apellido",apellido,"deber",deber,"saldo",saldo,"haber",haber,"visible",visible);
                nuevo.saveIt();
                Base.commitTransaction();
                return true;
            }
        }
        return false;
    }
    
    
     /**
     * 
     * @param id
     * @return true si el Cliente existe
     *
     */
    
    public boolean bajaCliente(int id){
        Cliente c = Cliente.findById(id);
        if (c!=null){
            Base.openTransaction();
            c.set("visible",0);
            c.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
        
    }
}
