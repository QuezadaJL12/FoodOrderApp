package mx.edu.itson.potros.foodorderapp;

import java.util.List;

public class CarritoFirebase {

    private double subtotal;
    private double iva;
    private double total;
    private List<Product> productos;

    public CarritoFirebase() {
    }

    public CarritoFirebase(double subtotal, double iva, double total, List<Product> productos) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.productos = productos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Product> getProductos() {
        return productos;
    }

    public void setProductos(List<Product> productos) {
        this.productos = productos;
    }
}