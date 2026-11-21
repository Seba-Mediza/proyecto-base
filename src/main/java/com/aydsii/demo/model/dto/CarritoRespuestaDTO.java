package com.aydsii.demo.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

// Carrito completo del cliente, que se devuelve en el paso 6 de CU-03
@Schema(description = "Carrito del cliente con todos sus productos y el total")
public record CarritoRespuestaDTO(
        @Schema(description = "DNI del cliente dueño del carrito") String clienteDni,
        @Schema(description = "Productos del carrito") List<ItemCarritoDTO> items,
        @Schema(description = "Suma de los subtotales, en pesos") Double total) {
}
