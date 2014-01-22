/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import models.Producto;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class ABMProducto {

    private String nombre;
    private Double precio;
    private int stock;
    private Double comision;
    private String diaSorteo;
    private int visible;

    public ABMProducto(String nombre, Double precio, int stock, Double comision, String diaSorteo, int visible) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.comision = comision;
        this.diaSorteo = diaSorteo;
        this.visible = visible;
    }

    public ABMProducto() {
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public Double getComision() {
        return comision;
    }

    public String getDiaSorteo() {
        return diaSorteo;
    }

    public int getVisible() {
        return visible;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public void setDiaSorteo(String diaSorteo) {
        this.diaSorteo = diaSorteo;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public boolean altaProducto(String nombre, Double precio, int stock, Double comision, String diaSorteo) {
        boolean result;
        Base.openTransaction();
        Producto prodViejo = Producto.first("nombre = ?", nombre);
        int id = prodViejo.getInteger("id");
        if (Producto.exists(id)) {
            prodViejo.set("precio", precio, "stock", stock, "comision", comision, "diaSorteo", diaSorteo, "visible", 1).saveIt();
            result = true;
        } else {
            Producto nuevoProd = Producto.create("nombre", nombre, "precio", precio, "stock", stock, "comision", comision, "diaSorteo", diaSorteo, "visible", 1);
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

    public boolean modificarProducto(int id, String nombre, Double precio, int stock, Double comision, String diaSorteo) {
        boolean result;
        Base.openTransaction();
        if (Producto.exists(id)) {
            Producto prodModif = Producto.findById(id);
            prodModif.set("nombre", nombre, "precio", precio, "stock", stock, "comision", comision, "diaSorteo", diaSorteo).saveIt();
            result = true;
            System.out.print("Producto modificado satisfactoriamente");
        } else {
            result = false;
            System.out.print("Producto no encontrado en Base de Datos");
        }
        Base.commitTransaction();
        return result;
    }

}
