package com.aydsii.demo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// Una línea del carrito tal como se le muestra al cliente
@Schema(description = "Producto del carrito con su cantidad y subtotal")
public record ItemCarritoDTO(
        @Schema(description = "Código del producto") Integer productoCodigo,
        @Schema(description = "Nombre del producto") String nombre,
        @Schema(description = "Presentación del producto, por ejemplo 'Pack 10 un.'") String descripcion,
        @Schema(description = "Cantidad de packs en el carrito") Integer cantidad,
        @Schema(description = "Precio de un pack en pesos") Double precioPack,
        @Schema(description = "Precio de un pack multiplicado por la cantidad") Double subtotal) {
}
