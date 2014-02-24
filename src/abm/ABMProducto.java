/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import java.math.BigDecimal;
import models.Fecha;
import models.Producto;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class ABMProducto {



    public boolean altaProducto(String nombre, BigDecimal precio, BigDecimal comision, int hayStock, int stock) {
        boolean result;
        Base.openTransaction();
        Producto prodViejo = Producto.first("nombre = ?", nombre);
        if (prodViejo != null) {
            prodViejo.set("precio", precio, "comision", comision, "visible", 1,"hayStock",hayStock, "stock",stock).saveIt();
            result = true;
        } else {
            Producto nuevoProd = Producto.create("nombre", nombre, "precio", precio, "comision", comision, "visible", 1,"hayStock",hayStock, "stock",stock);
            result = nuevoProd.saveIt();
        }
        Base.commitTransaction();
        return result;
    }

    public boolean bajaProducto(int id) {
        boolean result;
        Base.openTransaction();
        if (Producto.exists(id)) {
            Producto.findById(id).set("visible", 0).saveIt();
            result = true;
        } else {
            result = false;
        }
        Base.commitTransaction();
        return result;
    }

    public boolean modificarProducto(int id, String nombre, BigDecimal precio, BigDecimal comision,int hayStock, int stock) {
        boolean result;
        Base.openTransaction();
        if (Producto.exists(id)) {
            Producto prodModif = Producto.findById(id);
            prodModif.set("nombre", nombre, "precio", precio, "comision", comision,"hayStock",hayStock, "stock",stock).saveIt();
            result = true;
        } else {
            result = false;
        }
        Base.commitTransaction();
        return result;
    }

    public void altaFecha(int idProd, String diaDepo){
        Producto prod = Producto.findById(idProd);
        Fecha fecha = Fecha.create("diaDeposito",diaDepo);
        fecha.saveIt();
        prod.add(fecha);
    }
    
    public boolean bajaFecha(int idProd,String diaDepo){
        boolean result;
        Base.openTransaction();
        Fecha fecha = Fecha.first("diaDeposito= ? and producto_id= ?",diaDepo,idProd);
        if (fecha!=null){
            fecha.delete();
            result = true;
        } else {
            result = false;
        }
        Base.commitTransaction();
        return result;
    }
    
}
