package com.aydsii.demo.exception;

//el producto no tiene stock disponible y no se puede agregar al carrito -> 409
public class ProductoSinStockException extends RuntimeException {
    public ProductoSinStockException(String nombreProducto) {
        super("El producto \"" + nombreProducto + "\" está sin stock y no se puede agregar al carrito.");
    }
}
