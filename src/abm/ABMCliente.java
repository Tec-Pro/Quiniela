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
    private BigDecimal deber;
    private BigDecimal saldo;
    private BigDecimal haber;


    /**
     * Getters 
     */


    public BigDecimal getDeber() {
        return deber;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getHaber() {
        return haber;
    }


    /**
     * End of Getters
     */
    
    
    /**
     * Setters
     */



    public void setDeber(BigDecimal deber) {
        this.deber = deber;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }


    
    /**
     * End of Setters
     */
    
    public Cliente getCliente(int id){
        return Cliente.first("id = ?",id);
    }
    /**
     * 
     * @param nombre
     * @param apellido
     * @param deber
     * @param saldo
     * @param haber
     * @return true si el cliente se creo con exito, false si ya existía o introdujo datos erroneos
     *
     */
    public boolean altaCliente(String nombre, String apellido){
        Base.openTransaction();
        Cliente nuevo = Cliente.create( "nombre", nombre, "apellido", apellido, "visible",1);
        nuevo.saveIt();
        Base.commitTransaction();
        return true;
    }
    
    
     /**
     * 
     * @param id
     * @return true si el cliente se borro con exito
     *
     */
    public boolean bajaCliente(int id){
        Cliente c;
        c = getCliente(id);
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
     * @param deber
     * @param saldo
     * @param haber
     * @param id
     * @return true si el se modifico el cliente con exito, false en caso contrario
     *
     */
    public boolean modificarCliente(int id, BigDecimal deber, BigDecimal haber){
         Cliente c;
         c = getCliente(id);
         if (c!=null){
             Base.openTransaction();
             if (deber.subtract(haber).signum()==-1){
                 c.set("deber",0);
                 c.set("haber",haber.subtract(deber));
             } else {
                 c.set("haber",0);
                 c.set("deber",deber.subtract(haber));
             }
             c.saveIt();
             Base.commitTransaction();
             return true;
        }
        return false;
    }
}
