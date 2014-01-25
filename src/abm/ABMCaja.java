/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import models.Caja;
import org.javalite.activejdbc.Base;
import quiniela.Quiniela;

/**
 *
 * @author joako
 */
public class ABMCaja {
    private Timestamp fecha;
    private BigDecimal saldo;
    private List<Caja> cajas;
    /**
     * @return the fecha
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    /**
     * @param fecha de creación de la caja.
     * @return true si la caja no existía, false en caso contrario.
     */
    
    public boolean altaCaja(Date fecha){
        if (!findCaja(fecha)){ //Busca la caja por la fecha
            Base.openTransaction();
            Caja nuevo = Caja.create("fecha",fecha,"saldo",0,"visible",1); //Crea una nueva caja con saldo 0
            nuevo.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
    }
    
    
    /**
     * 
     * @param id
     * @return true si encuentra la caja y la elimina. 
     */
    public boolean bajaCaja(int id){
        Caja viejo = Caja.findById(id);
        if (viejo!=null){ //Busca la caja, si existe la borra.
            Base.openTransaction();
            viejo.set("visible", 0);
            viejo.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;    
    }
    
    /**
     * 
     * @param id
     * @param n
     * @return true si la modificación es exitosa. Sino falso.
     */
    
    public boolean modificarCaja(int id, BigDecimal n){
        Caja mod = Caja.findById(id); //Busca la caja, si existe verifica el signo de n
        if (mod != null){
            if (mod.getInteger("visible")==1){
                Base.openTransaction();
                if (n.signum() == 1){ 
                    mod.set("saldo",mod.getBigDecimal("saldo").add(n));
                } if (n.signum() ==-1){
                    mod.set("saldo",mod.getBigDecimal("saldo").add(n));
                } 
                mod.saveIt();
                Base.commitTransaction();
            return true;
            } else {
                return false;
            }
        } else {
            return false;
            }
        }
    
    /**
     * 
     * @param fecha
     * @return Caja correspondiente a la fecha pasada.
     */
    public Caja getCaja(Date fecha){
        return Caja.first("fecha = ?", fecha);
    }
    
    public int getLastCaja(){
        cajas = Caja.findAll();
        return cajas.get(cajas.size() - 1).getInteger("id");
    }

    /**
     * 
     * @param fecha
     * @return True si encuentra la caja.
     */
    public boolean findCaja(Date fecha){
        return (Caja.first("fecha = ?", fecha) != null);
    }
    
}
