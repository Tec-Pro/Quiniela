/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import models.Caja;
import models.Transaccion;
import org.javalite.activejdbc.Base;
import org.joda.time.LocalDate;

/**
 *
 * @author joako
 */
public class ABMCaja {

    private List<Caja> cajas;

    /**
     * @param fecha de creación de la caja.
     * @return true si la caja no existía, false en caso contrario.
     */

    public boolean altaCaja(LocalDate fecha) {
        if (!findCaja(fecha)) { //Busca la caja por la fecha
            Base.openTransaction();
            Caja nuevo = Caja.create("fecha", fecha.toDate(), "saldo", 0, "visible", 1); //Crea una nueva caja con saldo 0
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
    public boolean bajaCaja(int id) {
        Caja viejo = Caja.findById(id);
        if (viejo != null) { //Busca la caja, si existe la borra.
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
    public boolean modificarCaja(int id, BigDecimal n) {
        Caja mod = Caja.findById(id); //Busca la caja, si existe verifica el signo de n
        if (mod != null) {
            if (mod.getInteger("visible") == 1) {
                Base.openTransaction();
                mod.set("saldo", mod.getBigDecimal("saldo").add(n));
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
    public Caja getCaja(Date fecha) {
        return Caja.first("fecha = ?", fecha);
    }

    public int getLastCaja() {
        cajas = Caja.findAll();
        return cajas.get(cajas.size() - 1).getInteger("id");
    }

    /**
     *
     * @param fecha
     * @return True si encuentra la caja.
     */
    public boolean findCaja(LocalDate fecha) {
        return (Caja.first("fecha = ?", fecha.toDate()) != null);
    }

    public Double getTotalVentas(int id) {
        Double totalVentas = 0.0;
        List<Transaccion> ventas = Transaccion.where("caja_id = ?", id);
        for (Transaccion t : ventas) {
            if (t.get("cliente_id") == null) {
                if (t.get("visible").equals(1)) {
                    if (t.get("tipo").equals("Venta")) {
                        totalVentas += t.getDouble("monto");
                    }
                }
            }
        }
        return totalVentas;
    }

    public Double getTotalOtros(int id) {
        Double totalOtros = 0.0;
        List<Transaccion> ventas = Transaccion.where("caja_id = ?", id);
        for (Transaccion t : ventas) {
            if (t.get("visible").equals(1)) {
                if (!t.get("tipo").equals("Venta")) {
                    totalOtros += t.getDouble("monto");
                }
            }
        }
        return totalOtros;
    }
}
