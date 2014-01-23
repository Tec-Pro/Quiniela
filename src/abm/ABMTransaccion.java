/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import models.Caja;
import models.Transaccion;
import org.javalite.activejdbc.Base;

/**
 *
 * @author joako
 */
public class ABMTransaccion {

    private String motivo;
    private String tipo;
    private BigDecimal monto;
    private int visible;
    private int caja_id;

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the visible
     */
    public int getVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(int visible) {
        this.visible = visible;
    }

    /**
     * @return the caja_id
     */
    public int getCaja_id() {
        return caja_id;
    }

    /**
     * @param caja_id the caja_id to set
     */
    public void setCaja_id(int caja_id) {
        this.caja_id = caja_id;
    }

    /**
     *
     * @param motivo
     * @param tipo
     * @param monto
     * @param visible
     * @param caja
     * @param usuario
     * @return true si la transacción fue creada con éxito.
     */
    public boolean altaTransaccion(String motivo, String tipo, BigDecimal monto, int visible, int caja, int usuario) {
        Caja c = Caja.findById(caja);
        if (c != null) {
            Base.openTransaction();
            if (monto.signum() == -1) {
                System.out.println("Signo" + monto.signum());
                BigDecimal montoCaja = c.getBigDecimal("saldo");
                System.out.println("Lo que hay en caja" + montoCaja);
                System.out.println("Comparacion" + montoCaja.compareTo(monto));
                if (montoCaja.compareTo(monto.negate()) == -1) {
                    System.out.println("Comparacion" + montoCaja.compareTo(monto));
                    System.out.println("If Compare" + montoCaja);
                    return false;
                }
            }
            Transaccion nuevo = Transaccion.create("motivo", motivo, "tipo", tipo, "monto", monto, "visible", visible);
            nuevo.saveIt();
            c.add(nuevo);
            ABMCaja abmc = new ABMCaja();
            abmc.modificarCaja(caja, monto);
            Base.commitTransaction();
            return true;
        }
        return false;
    }

    /**
     *
     * @param id
     * @return true si la transacción existe
     *
     */
    public boolean bajaTransaccion(int id) {
        Transaccion t = Transaccion.findById(id);
        if (t != null) {
            Base.openTransaction();
            t.set("visible", 0);
            t.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
    }

}
