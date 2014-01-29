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



    public boolean altaProducto(String nombre, BigDecimal precio, BigDecimal comision, int hayStock) {
        boolean result;
        Base.openTransaction();
        Producto prodViejo = Producto.first("nombre = ?", nombre);
        if (prodViejo != null) {
            prodViejo.set("precio", precio, "comision", comision, "visible", 1,"hayStock",hayStock).saveIt();
            result = true;
        } else {
            Producto nuevoProd = Producto.create("nombre", nombre, "precio", precio, "comision", comision, "visible", 1,"hayStock",hayStock);
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
            System.out.print("Producto eliminado satisfactoriamente");
        } else {
            result = false;
            System.out.print("Producto no encontrado en Base de Datos");
        }
        Base.commitTransaction();
        return result;
    }

    public boolean modificarProducto(int id, String nombre, BigDecimal precio, BigDecimal comision,int hayStock) {
        boolean result;
        Base.openTransaction();
        if (Producto.exists(id)) {
            Producto prodModif = Producto.findById(id);
            prodModif.set("nombre", nombre, "precio", precio, "comision", comision,"hayStock",hayStock).saveIt();
            result = true;
            System.out.print("Producto modificado satisfactoriamente");
        } else {
            result = false;
            System.out.print("Producto no encontrado en Base de Datos");
        }
        Base.commitTransaction();
        return result;
    }

    public void altaStockFecha(int idProd, int stock, String diaSorteo){
        Producto prod = Producto.findById(idProd);
        Fecha fechaStock = Fecha.create("stock",stock,"diaSorteo",diaSorteo);
        fechaStock.saveIt();
        prod.add(fechaStock);
    }
    
    public boolean bajaStockFecha(int idProd, int stock, String diaSorteo){
        boolean result;
        Base.openTransaction();
        Fecha fecha = Fecha.first("stock = ? and diaSorteo= ? and producto_id= ?",stock,diaSorteo,idProd);
        if (fecha!=null){
            fecha.delete();
            result = true;
            System.out.print("Producto eliminado satisfactoriamente");
        } else {
            result = false;
            System.out.print("Producto no encontrado en Base de Datos");
        }
        System.out.println(result);
        Base.commitTransaction();
        return result;
    }
    
}
