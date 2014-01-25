/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import models.Caja;
import models.Cliente;
import models.Transaccion;
import models.Usuario;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

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
    private int usuario_id;
    private int cliente_id;

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
    public boolean altaTransaccion(String motivo, String tipo, BigDecimal monto, int visible, int caja, int cliente, int usuario) {
        Caja c = Caja.findById(caja);
        Cliente cl = Cliente.findById(cliente);
        //Usuario u = Usuario.findById(usuario);
        if (c != null) {
            if (cl != null){
            Base.openTransaction();
            if (monto.signum() == -1) {
                BigDecimal montoCaja = c.getBigDecimal("saldo");
                if (montoCaja.compareTo(monto.negate()) == -1) {
                    return false;
                }
            }
            Transaccion nuevo = Transaccion.create("motivo", motivo, "tipo", tipo, "monto", monto, "visible", visible);
            nuevo.saveIt();
            c.add(nuevo);
            cl.add(nuevo);
            //u.add(nuevo);
            ABMCaja abmc = new ABMCaja();
            abmc.modificarCaja(caja, monto);
            Base.commitTransaction();
            return true;
            }
        }
        return false;
    }

    public boolean altaTransaccion(String motivo, String tipo, BigDecimal monto, int visible, int caja,  int usuario) {
        Caja c = Caja.findById(caja);
        //Usuario u = Usuario.findById(usuario);
        if (c != null) {
            Base.openTransaction();
            if (monto.signum() == -1) {
                BigDecimal montoCaja = c.getBigDecimal("saldo");
                if (montoCaja.compareTo(monto.negate()) == -1) {
                    return false;
                }
            }
            Transaccion nuevo = Transaccion.create("motivo", motivo, "tipo", tipo, "monto", monto, "visible", visible);
            nuevo.saveIt();
            c.add(nuevo);
            //u.add(nuevo);
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

    /**
     * @return the usuario_id
     */
    public int getUsuario_id() {
        return usuario_id;
    }

    /**
     * @param usuario_id the usuario_id to set
     */
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    /**
     * @return the cliente_id
     */
    public int getCliente_id() {
        return cliente_id;
    }

    /**
     * @param cliente_id the cliente_id to set
     */
    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

}
